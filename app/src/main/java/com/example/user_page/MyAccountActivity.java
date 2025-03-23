package com.example.user_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MyAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        // 初始化登录按钮
        Button loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到登录界面
                Intent intent = new Intent(MyAccountActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // 初始化注册按钮
        Button registerButton = findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册界面
                Intent intent = new Intent(MyAccountActivity.this, RegisterActivity.class);
                intent.putExtra("isRegister", true);
                startActivity(intent);
            }
        });
    }
}
