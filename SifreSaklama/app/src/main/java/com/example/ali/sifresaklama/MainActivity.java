package com.example.ali.sifresaklama;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;
import com.example.ali.sifresaklama.DataBase.DBOPERATION;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button[] btn;
    int[] btn_id={R.id.btn_giris,R.id.btn_sifre_unuttum};
    int[] edittext_id={R.id.k_ad,R.id.k_sifre};
    TextInputEditText[] edittext;
    CheckBox beni_hatirla;
    Context c;
    DBOPERATION db;
    static   SharedPreferences sp;
    static   SharedPreferences.Editor spe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c=this;
        db=new DBOPERATION(c);
        if(db.login_ilk())
            startActivity(new Intent(c,kullanicikayit.class));
        beni_hatirla=(CheckBox)findViewById(R.id.beni_hatirla);
        btn=new Button[btn_id.length];
        edittext=new TextInputEditText[edittext_id.length];
        for (int i=0;i<btn_id.length;i++)
        {
            btn[i]=(Button)findViewById(btn_id[i]);
            btn[i].setOnClickListener(this);
            edittext[i]=(TextInputEditText)findViewById(edittext_id[i]);
        }
        sp=this.getSharedPreferences("sharedPref",Context.MODE_PRIVATE);
        spe=sp.edit();
        if(sp.getBoolean("checked",false))
        {
            edittext[0].setText(sp.getString("kul_Ad",""));
            edittext[1].setText(sp.getString("sifre",""));
            beni_hatirla.setChecked(sp.getBoolean("checked",false));
        }
    }
public static  String hash_get(String sifre)
{
    try {

        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte[] hashInBytes = md.digest(sifre.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        return "";
    }

}
    public void giris()
    {
        if(Edittext.text_kontrol_et(edittext)) {
            String sha_sifre =hash_get(edittext[1].getText()+"");
            if(sha_sifre.equalsIgnoreCase(sha_sifre)) {
            if (beni_hatirla.isChecked()) {
                spe.putBoolean("checked", true);
                spe.putString("kul_Ad", edittext[0].getText() + "");
                spe.putString("sifre", edittext[1].getText() + "");
                spe.commit();
            } else
                spe.clear();

            if (db.login_giris(edittext[0].getText() + "", sha_sifre)) {
                startActivity(new Intent(c, Home.class));
                finishAffinity();
            } else
                Toast.makeText(c, "Kullanıcı Adı veya Şifre Yanlış.", Toast.LENGTH_LONG).show();
        }
        }
        else
            Toast.makeText(c,"Lütfen Alanları Boş Bırakmayınız.",Toast.LENGTH_LONG).show();
    }
    public  void  sifre_unuttum()
    {
      startActivity(new Intent(c,sifre_dogrula.class));
    }
    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.btn_giris:giris();break;
            case  R.id.btn_sifre_unuttum:sifre_unuttum();break;
        }

    }
}
