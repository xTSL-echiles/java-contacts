package com.example.phonebook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "userBase.db";
    public UserBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table  "+UserDBSchema.UserTable.NAME+"(" + "_id integer primary key autoincrement, " + UserDBSchema.Cols.UUID+", "
                + UserDBSchema.Cols.USERNAME+", "+UserDBSchema.Cols.USERLASTNAME+", "+ UserDBSchema.Cols.PHONE+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
