package com.example.phonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.phonebook.database.UserBaseHelper;
import com.example.phonebook.database.UserDBSchema;

import java.util.ArrayList;
import java.util.UUID;

public class Users {
    private ArrayList<User> userList;
    private SQLiteDatabase database;
    private Context context;

    public Users(Context context) {
        this.context = context.getApplicationContext();
        this.database = new UserBaseHelper(context).getWritableDatabase();
    }
    public void addUser(User user){
        ContentValues values = getContentValues(user);
        database.insert(UserDBSchema.UserTable.NAME, null, values);
    }
    private static ContentValues getContentValues(User user){
        ContentValues values = new ContentValues(); // сопоставляем характеристики юзера с колонками из ДБ
        values.put(UserDBSchema.Cols.UUID, user.getUuid().toString());
        values.put(UserDBSchema.Cols.USERNAME, user.getUserName());
        values.put(UserDBSchema.Cols.USERLASTNAME, user.getUserLastName());
        values.put(UserDBSchema.Cols.PHONE, user.getPhone());
        return values;
    }
    private UserCursorWrapper queryUsers(){
        Cursor cursor = database.query(UserDBSchema.UserTable.NAME, null, null ,null, null, null, null);
        return new UserCursorWrapper(cursor);
    }
    public ArrayList<User> getUserList(){
        this.userList = new ArrayList<User>();
        UserCursorWrapper cursorWrapper = queryUsers();
        try{
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                User user = cursorWrapper.getUser();
                userList.add(user);
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }
        return (userList);
    }
    public User getUserFromDb(UUID uuid){ // =?  Когда вы используете подготовленные операторы, базе данных не нужно каждый раз анализировать / компилировать запрос. Он использует шаблон и просто подставляет в него значения.
        Cursor cursor = database.query(UserDBSchema.UserTable.NAME, null, UserDBSchema.Cols.UUID+"=?", new String[]{uuid.toString()}, null, null, null);
        UserCursorWrapper cursorWrapper = new UserCursorWrapper(cursor);
        cursorWrapper.moveToFirst();
        return cursorWrapper.getUser();
    }
    public void removeUser(UUID uuid){
        String stringUuid = uuid.toString();
        database.delete(UserDBSchema.UserTable.NAME, UserDBSchema.Cols.UUID+"=?", new String[]{stringUuid}); // находим запись и удаляем из БД
    }
    public void updateUser(User user){
        ContentValues values = getContentValues(user);// позволяет связать строки в БД по какому то параметру, тут прост вызов для "раскладывания"
        database.update(UserDBSchema.UserTable.NAME, values, UserDBSchema.Cols.UUID+"=?", new String[]{user.getUuid().toString()});

    }
}
