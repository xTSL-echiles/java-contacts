package com.example.phonebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView; // то, что помогает сделать скрол вниз, контейнеры для информации постоянно сменяющие друг-друга не создающие новые
    ArrayList<User> userList = new ArrayList<>(); // лист юзеров
    UserAdapter userAdapter; //создаем объект для адаптера
    Button addUserBtn; // кнопка добавить юзера
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        addUserBtn = findViewById(R.id.addUserBtn);
        addUserBtn.setOnClickListener(new View.OnClickListener() { // создаем линейную сетку для нашей телефонной книги
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserFormActivity.class);
                startActivity(intent);
            }
        });
    }
    private void recyclerViewInit(){
        Users users = new Users(MainActivity.this);
        userList = users.getUserList();
        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter); // передаем на вход список элементов для адаптера
    }

    @Override
    public void onResume(){
        super.onResume();
        recyclerViewInit();
    }

    private  class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{ //user holder - генерирует элементы списка
        TextView itemTextView;
        User user;
        public UserHolder(LayoutInflater inflater, ViewGroup viewGroup) {  // в конструктор 2 элемента,
            super(inflater.inflate(R.layout.single_item, viewGroup, false));  // раздуваем макет, что бы он отрисовывался на экране каждый раз
            // itemView - текущий layout single_item
            itemTextView = itemView.findViewById(R.id.itemTextView); // обращаемся к тексту в single_item
            itemView.setOnClickListener(this); // активность на клик, именно наша (this) активность в текущем UserHolder'e
        }

        public void bind(String userString, User user){
            itemTextView.setText(userString);
            this.user = user;
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }
    private class UserAdapter extends RecyclerView.Adapter<UserHolder>{ // адаптер, помещает элементы в наш recycler view, внутри передаем user holder - генерирует элементы списка
        ArrayList<User> users;// создаем лист с юзерами, которые потом будем получать
        public UserAdapter(ArrayList<User> users) {
            this.users = users;
        }
        @Override
        public UserHolder onCreateViewHolder(ViewGroup viewGroup, int i) { // передаем элементы для отображения и счетчик, что бы знать какой элеент отобразить
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);  //добавлям в "контейнер" информацию для отображения
            return new UserHolder(inflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(UserHolder userHolder, int position) {  // тут записываем в макет материалы из юзер листа
            User user = users.get(position);
            String userString = user.getUserName()+"\n"+user.getUserLastName();
            userHolder.bind(userString, user); // вызываем метод bind - печатаем в "контейнере" текст, получаемычй строкой выше
        }

        @Override
        public int getItemCount() {
            return users.size(); // возвращает кол-во элементов в списке
        }
    }
}
