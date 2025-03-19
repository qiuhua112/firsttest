package com.example.user_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        // 获取传递过来的用户名
        String username = getIntent().getStringExtra("username");

        // 设置用户名
        TextView usernameTextView = findViewById(R.id.tv_username);
        usernameTextView.setText(username);

        // 初始化修改个人信息按钮
        Button switchprofileButton = findViewById(R.id.switch_button);
        Button editProfileButton = findViewById(R.id.btn_edit_profile);
        switchprofileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到编辑个人信息界面
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                // 根据手机号获取用户名和手机号
                String phone = getIntent().getStringExtra("phone");
                intent.putExtra("username", username);
                intent.putExtra("phone", phone);
                startActivity(intent);
            }
        });
    }
}