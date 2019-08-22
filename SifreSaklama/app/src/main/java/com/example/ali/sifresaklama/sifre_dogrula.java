package com.example.ali.sifresaklama;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.sifresaklama.DataBase.DBOPERATION;

public class sifre_dogrula extends AppCompatActivity implements View.OnClickListener{
     Context c;
    EditText et_sifre;
    Button btn_dogrula;
    TextView text;
    DBOPERATION db;
    int id=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_dogrula);
        c=this;
        db=new DBOPERATION(c);
        et_sifre=(EditText)findViewById(R.id.sifre_dogrula);
        btn_dogrula=(Button)findViewById(R.id.btn_dogrula);
        btn_dogrula.setOnClickListener(this);
        text=(TextView)findViewById(R.id.text_not );
        id=db.login_sifre_check("");
        if(id!=-1)
        {
            btn_dogrula.setText("GÜNCELLE");
            et_sifre.setText("");
            et_sifre.setHint("Yeni Şifre Giriniz");
            text.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if(et_sifre.getText().length()>0) {
            if (btn_dogrula.getText().toString().equalsIgnoreCase("Doğrula")) {
                id=db.login_sifre_check(et_sifre.getText()+"");
                if(id!=-1)
                {
                    btn_dogrula.setText("GÜNCELLE");
                    et_sifre.setText("");
                    et_sifre.setHint("Yeni Şifre Giriniz");
                    text.setVisibility(View.GONE);
                  return;
                }
                else
                    Toast.makeText(c,"Malesef Girdiğiniz Şifre Doğrulanmadı.",Toast.LENGTH_LONG).show();
            }
            if (btn_dogrula.getText().toString().equalsIgnoreCase( "GÜNCELLE")) {
              String sha_sifre=MainActivity.hash_get(et_sifre.getText()+"");
                if(sha_sifre!=""&&db.login_sifre_guncelle(sha_sifre,id))
                {
                    MainActivity.spe.clear().commit();
                    Toast.makeText(c,"Şifreniz Başarı ile Güncellendi.",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(c,MainActivity.class));
                    finish();
                }
            }
        }
        else
            Toast.makeText(c,"Lütfen Alanı Boş Bırakmayınız.",Toast.LENGTH_LONG).show();
    }
}
