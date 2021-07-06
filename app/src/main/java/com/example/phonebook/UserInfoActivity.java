package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    TextView textViewInfoName;
    TextView textViewInfoLastName;
    TextView textViewInfoPhone;
    Button removeUserInfo;
    Button editUserInfo;
    Users users;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        user = (User) getIntent().getSerializableExtra("user");
        users = new Users(UserInfoActivity.this);
        removeUserInfo = findViewById(R.id.removeUserInfo);
        editUserInfo = findViewById(R.id.editUserInfo);
        editUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this,UserFormActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        removeUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.removeUser(user.getUuid());
                onBackPressed();
            }
        });
    }
    public void onResume(){
        super.onResume();
        user = users.getUserFromDb(user.getUuid());
        textViewInfoName = findViewById(R.id.textViewInfoName);
        textViewInfoLastName = findViewById(R.id.textViewInfoLastName);
        textViewInfoPhone = findViewById(R.id.textViewInfoPhone);
        textViewInfoName.setText(user.getUserName());
        textViewInfoLastName.setText(user.getUserLastName());
        textViewInfoPhone.setText(user.getPhone());
    }
}