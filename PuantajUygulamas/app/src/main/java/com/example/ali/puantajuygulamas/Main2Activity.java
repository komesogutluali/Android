package com.example.ali.puantajuygulamas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import adepter.adepter1;
import puantaj.Puantaj;
import veritabani.*;
import java.io.DataOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    public ListView lv;
    public Button btn,btn_sil;
    public Spinner[] sp;
   public int[] sp_id={R.id.syil,R.id.say};
   public ArrayList<String> list[];
    public ArrayList<Integer> id,lvlis_id;
   ArrayList<Puantaj> lvlist;
   public ArrayAdapter<String >[] adp;
   adepter1 myadp;
   database vt;
   SQLiteDatabase db;
    private static Context context;
    RadioButton[] rbtn;
    TextView[] genel_text;
    int[] genel_text_id={R.id.topgun,R.id.topmesai};
   public static String[] aylar={"Ocak","Şubat","Mart","Nisan","Mayıs","Haziran","Temmuz","Ağustos","Eylül","Ekim","Kasım","Aralık"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        context=this;
        list=new ArrayList[sp_id.length];
        lvlis_id=new ArrayList<Integer>();
       vt=new database(this);
        sp=new Spinner[sp_id.length];
        adp=new ArrayAdapter[list.length];
       id=new ArrayList<Integer>();
        genel_text=new TextView[genel_text_id.length];
        for(int i=0;i<sp.length;i++)
       {
           sp[i]=(Spinner)findViewById(sp_id[i]);
           list[i]=new ArrayList<String>();
           adp[i]=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list[i]);
           sp[i].setAdapter(adp[i]);
           genel_text[i]=(TextView)findViewById(genel_text_id[i]);
       }
    lvlist=new ArrayList<Puantaj>();
       myadp=new adepter.adepter1(this,lvlist);
       lv=(ListView)findViewById(R.id.liste);
       lv.setAdapter(myadp);
       spdoldur();
       for(int i=0;i<aylar.length;i++)
           adp[1].add(aylar[i]);
       Date date=new Date();
       SimpleDateFormat sdf=new SimpleDateFormat("MM");
       final int tarih=Integer.parseInt(sdf.format(date));
        sp[1].setSelection(tarih-1);
        btn_sil=(Button)findViewById(R.id.btnsil);
        btn_sil.setEnabled(false);
        btn_sil.setOnClickListener(this);
        btn=(Button)findViewById(R.id.btnara);
        btn.setOnClickListener(this);

    }
    public static void notgoster(String not)
    {
        if(not.length()>0) {
            LinearLayout layout = new LinearLayout(context);
            TextView tgunluk = new TextView(context);
            tgunluk.setText(not);
            layout.addView(tgunluk);
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("NOT");
            dialog.setCancelable(true);
            dialog.setView(layout);
            dialog.create().show();
        }
        else
            Toast.makeText(context,"Bu güne ait herhangi bir not yok!!",Toast.LENGTH_LONG).show();

    }
public void baglan()
{
    try
    {
   db=vt.getWritableDatabase();
    }
    catch (Exception ex)
    {
        Toast.makeText(this,ex.getMessage(), Toast.LENGTH_LONG).show();
    }
}
public void spdoldur()
{
    try
    {
       list[0].clear();
       adp[0].notifyDataSetChanged();
       id.clear();
        baglan();
        Cursor c=db.query("Yil",new String[]{"yilid","yil"},null,null,null,null,null);
        c.moveToPosition(-1);
        while(c.moveToNext())
        {
            adp[0].add(c.getString(1));
            id.add(c.getInt(0));
        }
        c.close();
        db.close();
    }
    catch (Exception ex)
    {
        Toast.makeText(this,ex.getMessage(), Toast.LENGTH_LONG).show();
    }
}

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnara:ara();break;
            case R.id.btnsil:hepsinisil();break;
        }
    }
    public void ara()
    {
        int secilen_yil=sp[0].getSelectedItemPosition();
        int secilen_ay=sp[1].getSelectedItemPosition();
        if(secilen_ay>-1&&secilen_yil>-1)
            try
            {
                String tarih_query=(secilen_ay+1)+"";
                if(tarih_query.length()<2)
                    tarih_query="0"+tarih_query;
                tarih_query=adp[0].getItem(secilen_yil)+tarih_query+"%";
                lvlist.clear();
                lvlis_id.clear();
                myadp.notifyDataSetChanged();
                baglan();
                Cursor c=db.query("Puantaj",new String[]{"gun","mesai","gunluk","id"},"yilid=? and gun like ?",new String[]{id.get(secilen_yil)+"",tarih_query},null,null,"gun ASC");
                c.moveToPosition(-1);
                while(c.moveToNext())
                {   String tarih1=c.getString(0);
                    String tarih3=tarih1.substring(0,4)+"/";
                    tarih3+=tarih1.substring(4,6)+"/";
                    tarih3+=tarih1.substring(6,8);
                    lvlist.add(new Puantaj(tarih3,c.getDouble(1),c.getString(2)));
                    lvlis_id.add(c.getInt(3));
                }
                c.close();
                db.close();
                if(lvlist.size()!=0)
                    btn_sil.setEnabled(true);
                else
                    btn_sil.setEnabled(false);
                MainActivity.geneltoplam(genel_text,lvlist);
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(),ex.getMessage(), Toast.LENGTH_LONG).show();
            }
    }
    public void hepsinisil()
    {   final AlertDialog.Builder dialog=new AlertDialog.Builder(this);
         dialog.setTitle("Seçim");
         dialog.setCancelable(false);
        LinearLayout layout=new LinearLayout(this);
       layout.setOrientation(LinearLayout.VERTICAL);
        final RadioGroup grup=new RadioGroup(this);
        final RadioButton[] rbtn={new RadioButton(this),new RadioButton(this)};
        rbtn[0].setText(list[0].get(sp[0].getSelectedItemPosition()));
        rbtn[1].setText(list[1].get(sp[1].getSelectedItemPosition()));
        grup.addView(rbtn[0]);
        grup.addView(rbtn[1]);
        layout.addView(grup);
        dialog.setView(layout);
        dialog.setPositiveButton("Hepsini Sil", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int secilen_yil=sp[0].getSelectedItemPosition();
                int secilen_ay=sp[1].getSelectedItemPosition();
                if(secilen_ay>-1&&secilen_yil>-1) {
                    try {
                         int secilen_id=grup.getCheckedRadioButtonId();
                        if (secilen_id==rbtn[0].getId())//Yıl'a göre hepsini silme
                        {
                            baglan();
                            db.delete("Puantaj","yilid=?",new String[]{id.get(secilen_yil)+""});
                            db.close();
                            MainActivity.datagetir();
                            spdoldur();
                            ara();
                        }
                        else if(secilen_id==rbtn[1].getId())//Secili yıl'ın bu hesini silme
                        {
                            baglan();
                            for(int a=0;a<lvlis_id.size();a++)
                                db.delete("Puantaj","id=? and yilid=?",new String[]{lvlis_id.get(a)+"",id.get(secilen_yil)+""});
                            db.close();
                            MainActivity.datagetir();
                            ara();
                        }
                        else
                            Toast.makeText(getApplicationContext(),"Hiçbir secim yapılmadı!!",Toast.LENGTH_SHORT).show();
                    } catch (Exception ex) {

                    }
                }
            }
        });
        dialog.setNegativeButton("Vazgeç", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    dialog.create().show();
    }
}
