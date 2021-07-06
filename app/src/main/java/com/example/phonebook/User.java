package com.example.phonebook;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable { // класс для юзера, гетеры-сетеры, все как обычно
    private String userName;
    private String userLastName;
    private UUID uuid;
    private String phone;
    public User(){
        this.uuid = UUID.randomUUID(); // конструктор на ID
    }
    public User(UUID uuid){
        this.uuid = uuid;
    } // перегрузка конструктора для изменения UUID

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UUID getUuid() {
        return uuid;
    }
}