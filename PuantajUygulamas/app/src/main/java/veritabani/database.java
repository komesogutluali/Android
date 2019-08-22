package veritabani;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {

    public static String dbname="Puantajdb.db";
    public static  int version=1;
    public database(Context c)
    {
        super(c,dbname,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql1="create table Yil(yilid integer primary key autoincrement not null,yil integer not null);";
     String sql="create table Puantaj(id integer primary key autoincrement not null,gun text not null,mesai real not null,gunluk text,yilid integer not null);";
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql1="drop table if exists  Yil";
        String sql="drop table if exists  Puantaj";
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
