package com.example.ali.sifresaklama;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.ali.sifresaklama.DataBase.DBOPERATION;

public class kullanicikayit extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText[] edittext;
    int[] editext_id={R.id.k_ad,R.id.k_sifre};
    Button btn;
    DBOPERATION db;
    Context c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanicikayit);
        c=this;
        edittext=new TextInputEditText[editext_id.length];
        for(int i=0;i<edittext.length;i++)
            edittext[i]=(TextInputEditText)findViewById(editext_id[i]);
           btn=(Button)findViewById(R.id.btn_kayit);
           btn.setOnClickListener(this);
           db=new DBOPERATION(c);
    }
    @Override
    public void onClick(View view) {
        if(Edittext.text_kontrol_et(edittext))
        {
            String sha_sifre=MainActivity.hash_get(edittext[1].getText()+"");
            if(sha_sifre.equalsIgnoreCase(sha_sifre)) {
                db.login_kaydet(edittext[0].getText() + "",sha_sifre);
                startActivity(new Intent(c, MainActivity.class));
                finish();
            }
            else
                Toast.makeText(c,"Kayıt yapılamadı.",Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(c,"Lütfen Alanları Boş Bırakmayınız.",Toast.LENGTH_LONG).show();

    }
}
