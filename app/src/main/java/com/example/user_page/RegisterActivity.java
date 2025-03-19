package com.example.user_page;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private UserDBHelper dbHelper;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        dbHelper = new UserDBHelper(this);

        // 初始化手机号、用户名、密码输入框
        final EditText phoneEditText = findViewById(R.id.et_phone);
        final EditText usernameEditText = findViewById(R.id.et_username);
        final EditText passwordEditText = findViewById(R.id.et_password);

        // 初始化注册按钮
        Button registerButton = findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEditText.getText().toString();
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (validateRegisterInput(phone, username, password)) {
                    if (registerUser(phone, username, password)) {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                        // 跳转回个人账户页面
                        Intent intent = new Intent(RegisterActivity.this, MyAccountActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // 验证注册输入
    private boolean validateRegisterInput(String phone, String username, String password) {
        if (phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 检查手机号是否已注册
        if (isPhoneRegistered(phone)) {
            Toast.makeText(this, "手机号已注册", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // 注册用户
    private boolean registerUser(String phone, String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String insertQuery = "INSERT INTO " + UserDBHelper.TABLE_NAME + " (" +
                UserDBHelper.COLUMN_PHONE + ", " +
                UserDBHelper.COLUMN_USERNAME + ", " +
                UserDBHelper.COLUMN_PASSWORD + ") VALUES ('" +
                phone + "', '" + username + "', '" + password + "');";
        try {
            db.execSQL(insertQuery);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }

    // 检查手机号是否已注册
    private boolean isPhoneRegistered(String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + UserDBHelper.TABLE_NAME + " WHERE " +
                UserDBHelper.COLUMN_PHONE + " = '" + phone + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean isRegistered = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isRegistered;
    }
}