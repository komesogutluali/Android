package com.example.ali.sifresaklama.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class Veritabani extends SQLiteOpenHelper {

   static String db_name="SifreSaklama.db";
   static  int version=1;
    public Veritabani(Context c)
    {
        super(c,db_name,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table LOGIN( ID integer primary key autoincrement not null,KULLANICIAD text not null,SIFRE text not null)";
        String sql1="create table BILGILER(ID integer primary key autoincrement not null,ACIKLAMA text ,KULLANICIAD text,SIFRE text)";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql1="drop table  if exists LOGIN";
        String sql2="drop table  if exists BILGILER";
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        onCreate(sqLiteDatabase);
    }

}
