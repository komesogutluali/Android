package com.example.ali.sifresaklama.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.ali.sifresaklama.Adepter.Bilgiadp;
import com.example.ali.sifresaklama.SifreBilgi;
import java.util.List;

public class DBOPERATION {
private  Veritabani vt;
private SQLiteDatabase db;
private  Context c;
public  DBOPERATION(Context c)
{
    vt=new Veritabani(c);
    this.c=c;
}
private void baglan()
{
    try {
       if(vt!=null)
           db=vt.getWritableDatabase();

    }catch (Exception ex)
    {
        Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
    }

}
    private void kapat()
    {
        try {
            if(vt!=null)
                db.close();
        }catch (Exception ex)
        {
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    public void login_kaydet(String kullaniciad,String sifre)
    {
        try {
            baglan();
            db.beginTransaction();
            ContentValues par=new ContentValues();
            par.put("KULLANICIAD",kullaniciad);
            par.put("SIFRE",sifre);
            db.insertOrThrow("LOGIN",null,par);
            db.setTransactionSuccessful();
            db.endTransaction();
            kapat();
        }catch (Exception ex)
        {
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    public boolean login_giris(String kullaniciad,String sifre)
    {
        try {
            baglan();
            db.beginTransaction();
            Cursor c=db.query("LOGIN",new String[]{"ID"},"KULLANICIAD=? and SIFRE=?",new String[]{""+kullaniciad,""+sifre},null,null,null);
            if(c.moveToFirst())
            {
                c.close();
                db.setTransactionSuccessful();
                db.endTransaction();
                kapat();
                return  true;
            }
            c.close();
            db.setTransactionSuccessful();
            db.endTransaction();
            kapat();
        }catch (Exception ex)
        {
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }
        return false;
    }
    public boolean login_ilk()
    {
        try {
            baglan();
            db.beginTransaction();
            Cursor c=db.query("LOGIN",new String[]{"ID"},null,null,null,null,null);
            if(c.moveToFirst())
            {
                c.close();
                db.setTransactionSuccessful();
                db.endTransaction();
                kapat();
                return  false;
            }
            c.close();
            db.setTransactionSuccessful();
            db.endTransaction();
            kapat();
        }catch (Exception ex)
        {
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
            return true;
        }
        return true;
    }
    public  boolean login_sifre_guncelle(String sifre,int id)
    {
        try
        {

            baglan();
            db.beginTransaction();
            ContentValues par=new ContentValues();
            par.put("SIFRE",sifre);
            db.update("LOGIN",par,"ID=?",new String[]{id+""});
            db.setTransactionSuccessful();
            db.endTransaction();
            kapat();
            return true;
        }catch (Exception ex)
        {
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
            return false;
        }

    }
    public int login_sifre_check(String sifre)
    {
        try {
            baglan();
            db.beginTransaction();
            Cursor c=db.query("BILGILER",new String[]{"ID"},"SIFRE=?",new String[]{sifre},null,null,null);
            if(c.moveToFirst())
            {
                c.close();
                Cursor c1=db.rawQuery("select ID from LOGIN",null);
                c1.moveToFirst();
                int id=c1.getInt(0);
                c1.close();
                db.setTransactionSuccessful();
                db.endTransaction();
                kapat();
                return  id;
            }
            c=db.rawQuery("select ID from BILGILER",null);
            if(c.getCount()==0)
            {
                c.close();
                Cursor c1=db.rawQuery("select ID from LOGIN",null);
                c1.moveToFirst();
                int id=c1.getInt(0);
                c1.close();
                db.setTransactionSuccessful();
                db.endTransaction();
                kapat();
                return  id;
            }
            c.close();
            db.setTransactionSuccessful();
            db.endTransaction();
            kapat();

        }catch (Exception ex)
        {
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
            return -1;
        }
        return  -1;
    }

    public boolean bilgi_ekle(String aciklama,String kullaniciad,String sifre)
{
    try {
        baglan();
        db.beginTransaction();
        ContentValues par=new ContentValues();
        par.put("ACIKLAMA",aciklama);
        par.put("KULLANICIAD",kullaniciad);
        par.put("SIFRE",sifre);
        db.insertOrThrow("BILGILER",null,par);
        db.setTransactionSuccessful();
        db.endTransaction();
        kapat();
        return true;
    }catch (Exception ex)
    {
        Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
       return false;
    }
}
    public void bilgi_sil(int id)
    {
        try {
            baglan();
            db.beginTransaction();
            db.delete("BILGILER","ID=?",new String[]{""+id});
            db.setTransactionSuccessful();
            db.endTransaction();
            kapat();
        }catch (Exception ex)
        {
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    public void bilgi_guncelle(String aciklama,String kullaniciad,String sifre,int id)
    {
        try {
            baglan();
            db.beginTransaction();
            ContentValues par=new ContentValues();
            par.put("ACIKLAMA",aciklama);
            par.put("KULLANICIAD",kullaniciad);
            par.put("SIFRE",sifre);
            db.update("BILGILER",par,"ID=?",new String[]{""+id});
            db.setTransactionSuccessful();
            db.endTransaction();
            kapat();
        }catch (Exception ex)
        {
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void bilgi_getir(List<SifreBilgi> liste, List<Integer> liste_id, Bilgiadp adp)
    {
        try {
            baglan();
            db.beginTransaction();
            Cursor c=db.query("BILGILER",new String[]{"ID","ACIKLAMA","KULLANICIAD","SIFRE"},null,null,null,null,"ID desc");
            liste.clear();
            liste_id.clear();
            adp.notifyDataSetChanged();
            c.moveToPosition(-1);
            while (c.moveToNext())
            {
                SifreBilgi sb=new SifreBilgi();
                liste_id.add(c.getInt(0));
                sb.aciklamasi=c.getString(1);
                sb.kullanici_ad=c.getString(2);
                sb.sifre=c.getString(3);
                liste.add(sb);
            }
            c.close();
            db.setTransactionSuccessful();
            db.endTransaction();
            kapat();
        }catch (Exception ex)
        {
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
}
