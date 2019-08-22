package veritabani;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class vt extends SQLiteOpenHelper{
     static final String dbname="Kisiler.db";
     static  int dbversiyon=1;
    public vt(Context c)
    {
        super(c,dbname,null,dbversiyon);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql="create table Kisi(id integer primary key autoincrement,ad text not null,soyad text not null,yas integer);";
       sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
     String sql="drop table  if exists Kisi";
     sqLiteDatabase.execSQL(sql);
     onCreate(sqLiteDatabase);
    }
}
