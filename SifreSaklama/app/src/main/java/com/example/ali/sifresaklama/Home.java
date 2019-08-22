package com.example.ali.sifresaklama;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ali.sifresaklama.Adepter.Bilgiadp;
import com.example.ali.sifresaklama.DataBase.DBOPERATION;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    Context c;
    DBOPERATION db;
    Button btn;
   AlertDialog.Builder dialog;
    ListView lv;
    List<SifreBilgi> liste;
    List<Integer> liste_id;
    Bilgiadp adp;
    LinearLayout ll;
    EditText[] edittext;
    String[] tint={"Açıklama","Kullanıcı Adı","Şifre"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        c=this;
        db=new DBOPERATION(c);
        btn=(Button)findViewById(R.id.btn_ekle);
        btn.setOnClickListener(this);
        lv=(ListView)findViewById(R.id.bilgiler);
        liste=new ArrayList<SifreBilgi>();
        liste_id=new ArrayList<Integer>();
        adp=new Bilgiadp(c,liste);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(this);
        db.bilgi_getir(liste,liste_id,adp);
    }


    public void init_layout()
{
    edittext=new EditText[tint.length];
    ll=new LinearLayout(c);
    ll.setOrientation(LinearLayout.VERTICAL);
    for(int i=0;i<edittext.length;i++)
    {
       edittext[i]=new EditText(c);
       edittext[i].setHint(tint[i]);
       edittext[i].setMaxLines(1);
       edittext[i].setLines(1);
       edittext[i].setInputType(InputType.TYPE_CLASS_TEXT);
       edittext[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
       ll.addView(edittext[i]);
    }
}

    @Override
    public void onClick(View view) {
     dialog=new AlertDialog.Builder(c);
     dialog.setTitle("Sifre Ekleme");
     dialog.setCancelable(true);
     init_layout();
     dialog.setView(ll);
        dialog.setIcon(R.drawable.not);
        dialog.setNeutralButton("Ekle", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {
                 if (Edittext.text_kontrol_et(edittext)) {
                     db.bilgi_ekle(edittext[0].getText() + "", edittext[1].getText() + "", edittext[2].getText() + "");

                     db.bilgi_getir(liste, liste_id, adp);

                 }
        }
         });

     dialog.setNegativeButton("Geri", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {

         }
     });
    dialog.create().show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int secilen=i;
        final int id=liste_id.get(secilen);
        dialog=new AlertDialog.Builder(c);
        dialog.setTitle("Düzenle veya Sil");
        dialog.setCancelable(true);
        init_layout();
        SifreBilgi sb=liste.get(i);
        edittext[0].setText(sb.aciklamasi);
        edittext[1].setText(sb.kullanici_ad);
        edittext[2].setText(sb.sifre);
        dialog.setView(ll);
        dialog.setIcon(R.drawable.not);
        dialog.setNeutralButton("Düzenle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (Edittext.text_kontrol_et(edittext)) {
                  db.bilgi_guncelle(edittext[0].getText() + "", edittext[1].getText() + "", edittext[2].getText() + "",id);
                    db.bilgi_getir(liste, liste_id, adp);
                }
            }
        });

        dialog.setNegativeButton("SİL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 db.bilgi_sil(id);
                db.bilgi_getir(liste, liste_id, adp);
            }
        });
        dialog.create().show();
    }
}
