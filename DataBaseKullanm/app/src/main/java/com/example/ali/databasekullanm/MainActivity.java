package com.example.ali.databasekullanm;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adepter.myadepter;
import kisi.Kisi;
import veritabani.vt;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ListView lv;
    EditText[] et,et_gun;
    int[] id={R.id.ad,R.id.soyad,R.id.yas};
    myadepter adp;
    List<Kisi> liste;
    ArrayList<Integer> idliste;
    Button btn;
    vt vtys;
    SQLiteDatabase db;
    AlertDialog.Builder dialog;
    LinearLayout layout;
    int secilenid;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        et=new EditText[id.length];
        vtys=new vt(this);
        lv=(ListView)findViewById(R.id.list);
        liste=new ArrayList<Kisi>();
        adp=new myadepter(this,liste);
        lv.setAdapter(adp);
        for(int i=0;i<id.length;i++)
            et[i]=(EditText)findViewById(id[i]);
        btn=(Button)findViewById(R.id.btnekle);
        btn.setOnClickListener(this);
        idliste=new ArrayList<Integer>();
        datagetir();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                secilenid=i;
                layout=new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);
                dialog=new AlertDialog.Builder(context);
                dialog.setTitle("Düzenle veya Sil");
                dialog.setCancelable(true);
                et_gun=new EditText[et.length];
                for(int a=0;a<id.length;a++) {
                    et_gun[a] = new EditText(context);
                    layout.addView(et_gun[a]);
                }
                Kisi k=liste.get(secilenid);
                et_gun[0].setText(k.ad);
                et_gun[1].setText(k.soyad);
                et_gun[2].setText(k.yas+"");
                dialog.setView(layout);
                dialog.setPositiveButton("Düzenle", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if(kontrol(et_gun))
                          {   int id=idliste.get(secilenid);
                              try {

                                  baglan();
                                  int yas=Integer.parseInt(et_gun[2].getText()+"");
                                  ContentValues par=new ContentValues();
                                  par.put("ad",et_gun[0].getText()+"");
                                  par.put("soyad",et_gun[1].getText()+"");
                                  par.put("yas",yas);
                                  db.update("Kisi",par,"id=?",new String[]{id+""});
                                  db.close();
                                  datagetir();
                              }catch (Exception ex)
                              {
                                  Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
                              }

                          }
                    }
                });
                dialog.setNegativeButton("Sil", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int id=idliste.get(secilenid);
                    try {
                        baglan();
                        int yas=Integer.parseInt(et_gun[2].getText()+"");
                        db.delete("Kisi","id=?",new String[]{id+""});
                        db.close();
                        datagetir();
                    }catch (Exception ex) {
                        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
                dialog.create().show();
                }

        });
    }
public void baglan()
{
    try {
        db=vtys.getWritableDatabase();
    }
    catch (Exception ex)
    {
        Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
    }

}
public boolean kontrol(EditText[] jtext)
{
    int sayac=0;
 for(int i=0;i<jtext.length;i++)
     if(jtext[i].getText().length()>0)
         sayac++;
    if(sayac==jtext.length)
        return  true;
 return false;
}
    @Override
    public void onClick(View view) {
        try {

            baglan();
            int yas=Integer.parseInt(et[2].getText()+"");
            if(kontrol(et))
            {
                ContentValues par=new ContentValues();
                par.put("ad",et[0].getText()+"");
                par.put("soyad",et[1].getText()+"");
                par.put("yas",yas);
              db.insertOrThrow("Kisi",null,par);
              db.close();
             datagetir();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
 public void datagetir(){
        try {
            baglan();
            Cursor c=db.query("Kisi",new String[]{"id","ad","soyad","yas"},null,null,null,null,null);
            liste.clear();
            idliste.clear();
            adp.notifyDataSetChanged();//liste değisiklik oldupu için adepteri güncelliyoruz
            c.moveToPosition(-1);//işaretcinin(Cursor) pozisyonu ayalıyoruz tablonun ilk satırından baslaması için
            while(c.moveToNext())//ileri diyoruz boylece Cursor tablonun ilk satırına geciyor
            {
                Kisi k=new Kisi();
                k.ad=c.getString(1);
                k.soyad=c.getString(2);
                k.yas=c.getInt(3);
                liste.add(k);//liste Kisi tipinde item aldığı için Kisi sınıfından nesne turetip degiskenlerine atıp nesneyi attık
                idliste.add(c.getInt(0));
            }
            db.close();
            c.close();
        }
        catch (Exception ex)
        {
           Toast.makeText(getApplicationContext(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }

 }
}
