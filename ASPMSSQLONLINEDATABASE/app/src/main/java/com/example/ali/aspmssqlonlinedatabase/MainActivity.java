package com.example.ali.aspmssqlonlinedatabase;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.aspmssqlonlinedatabase.ADEPTER.adepter;
import com.example.ali.aspmssqlonlinedatabase.ADEPTER.kisi;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    EditText[] et_dialog;
    EditText[] et;
    int[] et_id={R.id.etad,R.id.etsoyad,R.id.etyas};
    Button btn_ekle;
    Context c;
    ListView lv;
    adepter adp;
    List<kisi> liste;
    List<kisi> liste_temp;
    List<Integer> liste_id;
    AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c=this;
        et=new EditText[et_id.length];
        for(int i=0;i<et.length;i++)
            et[i]=(EditText)findViewById(et_id[i]);
        btn_ekle=(Button)findViewById(R.id.btn_ekle);
        btn_ekle.setOnClickListener(this);
        lv=(ListView)findViewById(R.id.lv_kisi);
        liste=new ArrayList<kisi>();
        adp=new adepter(c,liste);
        lv.setAdapter(adp);
        lv.setOnItemClickListener(this);
        liste_id=new ArrayList<Integer>();
        liste_temp=new ArrayList<kisi>();
        Uri.Builder builder=new Uri.Builder();
        builder.appendQueryParameter("durum","0");
        post(builder);
    }
    public  void post(final Uri.Builder builder)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.aliroot.somee.com");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    String query = builder.build().getEncodedQuery();
                    OutputStream os = conn.getOutputStream();//bağlantının cıkısı
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(query);
                    writer.flush();
                    writer.close();
                    os.close();
                    StringBuilder builder=new StringBuilder();
                    String json="";
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((json = bufferedReader.readLine()) != null)
                        builder.append(json + "\n");
                    JSONArray jsonArray = new JSONArray(builder.toString().trim());
                    liste_id.clear();
                    liste_temp.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        liste_id.add(obj.getInt("id"));
                        kisi k=new kisi();
                        k.ad=obj.getString("ad");
                        k.soyad=obj.getString("soyad");
                        k.yas=obj.getInt("yas");
                        liste_temp.add(k);
                    }
                }
                catch (Exception ex)
                {

                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      liste.clear();
                      adp.notifyDataSetChanged();
                      for (int i=0;i<liste_temp.size();i++)
                          liste.add(liste_temp.get(i));
                    }
                });
            }
        }).start();
    }

    public boolean dogrula(EditText[] et)
    {
        int sayac=0;
        for(int i=0;i<et.length;i++)
            if(et[i].getText().length()>0)
                sayac++;
        if(et.length==sayac)
            return  true;
        else
            return  false;
    }
    @Override
    public void onClick(View view) {

        if (dogrula(et))
        {
            int yas=-1;
            try
            {
                yas=Integer.parseInt(et[2].getText()+"");
            }catch (Exception ex)
            {
                Toast.makeText(c,"Yaş Alanını uygun giriniz.",Toast.LENGTH_LONG).show();
                return;
            }
            Uri.Builder builder=new Uri.Builder();
            builder.appendQueryParameter("ad",et[0].getText()+"");
            builder.appendQueryParameter("soyad",et[1].getText()+"");
            builder.appendQueryParameter("yas",yas+"");
            builder.appendQueryParameter("durum","1");
            post(builder);
            for(int i=0;i<et.length;i++)
                et[i].setText("");
        }
    }
    int secilen_id;
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        dialog=new AlertDialog.Builder(c);
        LinearLayout linearLayout=new LinearLayout(c);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        secilen_id=liste_id.get(i);
        et_dialog=new EditText[3];
        String[] hint={"Ad","Soyad","Yaş"};
        for (int a=0;a<et_dialog.length;a++)
        {
            et_dialog[a]=new EditText(c);
            et_dialog[a].setHint(hint[a]);
            linearLayout.addView(et_dialog[a]);
        }
        kisi k=liste.get(i);
        et_dialog[0].setText(k.ad);
        et_dialog[1].setText(k.soyad);
        et_dialog[2].setText(k.yas+"");
        dialog.setView(linearLayout);
        dialog.setTitle("Güncelle/Sil");
        dialog.setPositiveButton("Sil", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                   Uri.Builder builder = new Uri.Builder();//post paremetreleri ayarlanıyor
                   builder.appendQueryParameter("id", secilen_id + "");
                   builder.appendQueryParameter("durum", "2");
                   post(builder);

            }
        });
        dialog.setNegativeButton("Güncelle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dogrula(et_dialog)) {
                    int yas=-1;
                    try
                    {
                        yas=Integer.parseInt(et_dialog[2].getText()+"");
                    }catch (Exception ex)
                    {
                        Toast.makeText(c,"Yaş Alanını uygun giriniz.",Toast.LENGTH_LONG).show();
                        return;
                    }
                    Uri.Builder builder=new Uri.Builder();
                    builder.appendQueryParameter("ad",et_dialog[0].getText()+"");
                    builder.appendQueryParameter("soyad",et_dialog[1].getText()+"");
                    builder.appendQueryParameter("yas",yas+"");
                    builder.appendQueryParameter("durum","3");
                    builder.appendQueryParameter("id",secilen_id+"");
                    post(builder);
                }
                else
                    Toast.makeText(c,"Alan Boş bırakılamaz!!",Toast.LENGTH_LONG).show();
            }
        });
        dialog.create().show();

    }
}
