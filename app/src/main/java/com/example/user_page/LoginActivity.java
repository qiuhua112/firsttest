package com.example.user_page;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private UserDBHelper dbHelper;

    @Override
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        dbHelper = new UserDBHelper(this);

        // 初始化手机号、用户名、密码输入框
        final EditText phoneEditText = findViewById(R.id.et_phone);
        final EditText usernameEditText = findViewById(R.id.et_username);
        final EditText passwordEditText = findViewById(R.id.et_password);

        // 初始化登录按钮
        Button loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = phoneEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (validateLoginInput(phone, password)) {
                    // 模拟登录成功，获取用户名
                    String username = getUsernameByPhone(phone);
                    // 跳转到个人信息界面
                    Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("phone", phone); // 传递手机号
                    startActivity(intent);
                    finish();
                }
            }
        });

        // 初始化“注册账号”TextView
        TextView registerTextView = findViewById(R.id.tv_register);
        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册页面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    // 验证登录输入
    private boolean validateLoginInput(String phone, String password) {
        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 检查手机号和密码是否匹配
        if (checkLogin(phone, password)) {
            return true;
        }
        Toast.makeText(this, "手机号或密码错误", Toast.LENGTH_SHORT).show();
        return false;
    }

    // 检查登录信息
    private boolean checkLogin(String phone, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + UserDBHelper.TABLE_NAME + " WHERE " +
                UserDBHelper.COLUMN_PHONE + " = '" + phone + "' AND " +
                UserDBHelper.COLUMN_PASSWORD + " = '" + password + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean isLogin = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isLogin;
    }

    // 根据手机号获取用户名
    @SuppressLint("Range")
    private String getUsernameByPhone(String phone) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT " + UserDBHelper.COLUMN_USERNAME + " FROM " + UserDBHelper.TABLE_NAME + " WHERE " +
                UserDBHelper.COLUMN_PHONE + " = '" + phone + "';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String username = "";
        if (cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndex(UserDBHelper.COLUMN_USERNAME));
        }
        cursor.close();
        db.close();
        return username;
    }
}