package com.example.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserFormActivity extends AppCompatActivity {
    Button insertUserButton;
    EditText editTextName;
    EditText editTextLastName;
    EditText editTextPhone;
    boolean i = false; // костыльным флагам привет
    User user;
   // Users users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        user = (User) getIntent().getSerializableExtra("user");
        editTextName = findViewById(R.id.editTextTextName);
        editTextLastName = findViewById(R.id.editTextTextLastName); // связываем переменные и формы в xml
        editTextPhone = findViewById(R.id.editTextPhone);
        insertUserButton = findViewById(R.id.insertUserBtn);
        if(user != null) // если юзер есть - редактируем, если нет - создаем нового
        {
            insertUserButton.setText("Сохранить изменения");
            editTextName.setText(user.getUserName());
            editTextLastName.setText(user.getUserLastName());
            editTextPhone.setText(user.getPhone());
        }else{
            i = true;
           user = new User();
            insertUserButton.setText("Добавить контакт");
        }
        insertUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setUserName(editTextName.getText().toString());
                user.setUserLastName(editTextLastName.getText().toString()); // пишем что есть\нет в формах
                user.setPhone(editTextPhone.getText().toString());
                Users users = new Users(UserFormActivity.this);
                if(!i){
                    users.updateUser(user);// редактируем юзера
                }
                else users.addUser(user); // добавляем если не было
                onBackPressed(); // вернуться на экран назад
            }
        });
    }
}