package com.example.user_page;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        // 获取传递过来的用户名和手机号
        String username = getIntent().getStringExtra("username");
        String phone = getIntent().getStringExtra("phone");

        // 设置用户名和手机号
        TextView usernameTextView = findViewById(R.id.tv_edit_username);
        TextView phoneTextView = findViewById(R.id.tv_edit_phone);

        usernameTextView.setText(username);
        phoneTextView.setText(phone);
    }
}