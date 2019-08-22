package com.example.ali.puantajuygulamas;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import adepter.adepter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import puantaj.Puantaj;
import veritabani.database;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    public Button[] btn;
    public ListView lv;
    public EditText[] et,gun_et;
    static ArrayList<Puantaj> liste;
    DatePickerDialog trh;
    public static adepter adp;
    static Calendar clr;
    static database vt;
    static SQLiteDatabase db;
    static TextView[] genel_text;
    int[] genel_text_id={R.id.topgun,R.id.topmesai};
    public int btnid[]={R.id.btngun,R.id.btnayrinti};
    public int etid[]={R.id.mesai,R.id.not};
    public static int yilid;
    private static  Context context;
    public static    Date date;
    static ArrayList<Integer> id;
    DatePicker dp;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dp=(DatePicker)findViewById(R.id.tarih);
        vt=new database(this);
        context=this;
        date=new Date();
        id=new ArrayList<Integer>();
        clr=Calendar.getInstance();
        btn=new Button[btnid.length];
        et=new EditText[etid.length];
        gun_et=new EditText[et.length];
        genel_text=new TextView[genel_text_id.length];
        for(int i=0;i<btnid.length;i++)
        {
            btn[i]=(Button)findViewById(btnid[i]);
            if(i<etid.length) {
                et[i] = (EditText) findViewById(etid[i]);
               genel_text[i]=(TextView)findViewById(genel_text_id[i]);
            }
            btn[i].setOnClickListener(this);
        }
        et[0].setText("0");
        liste=new ArrayList<Puantaj>();
        adp=new adepter(this,liste);
        lv=(ListView)findViewById(R.id.list);
        lv.setAdapter(adp);
        datagetir();
        geneltoplam(genel_text,liste);
        lv.setOnItemClickListener(this);
   }
    @Override
    public void onClick(View view) {
     switch (view.getId())
     {
         case R.id.btngun:ekle();break;
         case R.id.btnayrinti:ayrinti();break;
     }
    }
    public static void baglan()
    {
        try {
            vt=new database(context);
            db=vt.getWritableDatabase();
        }catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void ayrinti()
    {
       Intent frm=new Intent(this,Main2Activity.class);
       startActivity(frm);
    }
    public static boolean checkdata1()
    {
        try {
            baglan();
            Cursor c=db.rawQuery("select yilid from Yil where yil=?",new String[] {clr.get(Calendar.YEAR)+""});
            c.moveToPosition(-1);
            while(c.moveToNext())
            {
                yilid=c.getInt(0);
                c.close();
                db.close();
                return false;
            }
          c.close();
          db.close();
        }catch (Exception ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
        //önceden bu tarih eklenmmemis
        return  true;
    }
    public boolean checkdata2(String gun)
    {  //Aynı gün eklenmesini engelliyoruz
        try {
            baglan();
            Cursor c=db.query("Puantaj",new String[]{"id"},"yilid=? and gun like ?",new String[]{yilid+"",gun+"%"},null,null,null);
            c.moveToPosition(-1);
            while(c.moveToNext())
            {
                c.close();
                db.close();
                return false;
            }
            c.close();
            db.close();
        }catch (Exception ex)
        {
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

        return  true;
    }
    public String trh_cevir(int y,int a,int g)
    {  String tarih=y+"";
        if(a<10)
            tarih+="0"+a;
        else
            tarih+=a;
        if(g<10)
            tarih+="0"+g;
        else
            tarih+=g;
 return tarih;
    }
public void ekle()
{
  if(et[0].getText().length()>0) {
    try { //Yıl ve ay ekliyoruz
        double mesai=Double.parseDouble(et[0].getText()+"");
        String tarih=trh_cevir(dp.getYear(),dp.getMonth()+1,dp.getDayOfMonth());
        if (checkdata1()) { //yil ve gün eklenecek
       baglan();
       ContentValues par1=new ContentValues();
       par1.put("yil",clr.get(Calendar.YEAR));
       db.insertOrThrow("Yil",null,par1);
       db.close();
       checkdata1();
            baglan();
            ContentValues par=new ContentValues();
            par.put("gun",tarih);
            par.put("mesai",mesai);
            par.put("yilid",yilid);
            par.put("gunluk",et[1].getText()+"");
            long tran=  db.insertOrThrow("Puantaj",null,par);
            db.close();
            datagetir();
            et[1].setText("");
            et[0].setText("0");
        } else {
            //normal sadece gün ekleniyor
            if(checkdata2(tarih)) {
                baglan();
                ContentValues par = new ContentValues();
                par.put("gun", tarih);
                par.put("mesai", mesai);
                par.put("yilid", yilid);
                par.put("gunluk", et[1].getText() + "");
                long tran = db.insertOrThrow("Puantaj", null, par);
                db.close();
                datagetir();
                et[1].setText("");
                et[0].setText("0");
            }
            else
                Toast.makeText(this,"Aynı Tarih Eklenemez!!!", Toast.LENGTH_LONG).show();
        }
    } catch (Exception ex) {
        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
    }

}

}

public static void datagetir()
{
    try {
        DateFormat frmt=new SimpleDateFormat("yyyyMM");
        String tarih=frmt.format(date);
        tarih+="%";
        checkdata1();
        baglan();
        Cursor c=db.query("Puantaj",new String[]{"gun","mesai","gunluk","id"},"gun like ? and yilid=?",new String[]{tarih,yilid+""},null,null,"gun ASC");
        liste.clear();
        id.clear();
        adp.notifyDataSetChanged();
        c.moveToPosition(-1);
        while(c.moveToNext())
        {
             String tarih1=c.getString(0);
             String tarih2=tarih1.substring(0,4)+"/";
             tarih2+=tarih1.substring(4,6)+"/";
             tarih2+=tarih1.substring(6,8);
           liste.add(new Puantaj(tarih2,c.getDouble(1),c.getString(2)));
            id.add(c.getInt(3));
        }
        c.close();
        db.close();
        geneltoplam(genel_text,liste);
}catch (Exception ex)
    {
        Toast.makeText(context, ex.getMessage(), Toast.LENGTH_LONG).show();
    }
}
public void guncelle(String gun,String  mesai,String not,int secilen_id)
{    if(gun.length()>0&&mesai.length()>0)//gün ve mesai' yi kontrol ediyoruz
{     double gmesai=Double.parseDouble(mesai);
    try {
        if(checkdata2(gun)) {
            baglan();
            ContentValues par = new ContentValues();
            par.put("gun", gun);
            par.put("mesai", gmesai);
            par.put("gunluk", not);
            db.update("Puantaj", par, "id=?", new String[]{"" + secilen_id});
            db.close();
            datagetir();
        }
        else
        {
            Toast.makeText(this,"Aynı Tarih Güncellenemez ve not veya mesai güncellendi", Toast.LENGTH_LONG).show();
            baglan();
            ContentValues par = new ContentValues();
            par.put("mesai", gmesai);
            par.put("gunluk", not);
            db.update("Puantaj", par, "id=?", new String[]{"" + secilen_id});
            db.close();
            datagetir();
        }


    }catch (Exception ex)
    {
        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
    }
}
else
    Toast.makeText(this, "Lütfen Değerleri Doğru Giriniz!!!", Toast.LENGTH_LONG).show();
}
//tıklanan notu dialogta gösteriyoruz
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
public void sil(int sil_indis)
{
    try {
        baglan();
        db.delete("Puantaj","id=?",new String[]{id.get(sil_indis)+""});
        db.close();
        datagetir();
    }catch (Exception ex)
    {
        Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
    }
}
//toplam gün ve mesai
public static void geneltoplam(TextView[] genel,ArrayList<Puantaj> liste)
{
    String[] ay=Main2Activity.aylar;
    SimpleDateFormat sdf=new SimpleDateFormat("MM");
    final int tarih=Integer.parseInt(sdf.format(date));
    genel[0].setText(ay[tarih-1]+" ay'ı Toplam="+liste.size()+" gün");
    double mesaitop=0.0;
    for(int i=0;i<liste.size();i++)
    {
        Puantaj p=liste.get(i);
        mesaitop+=p.mesai;
    }
   genel[1].setText("Mesai Toplam="+mesaitop+" saat");
}
String secilen_tarih;
   int secilen_indis;
    String[] dt;
   @Override
   //Listview in tıklanma olayı
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
      Puantaj k=liste.get(i);
    final   String[] dt={k.gun.substring(0,4),k.gun.substring(5,7),k.gun.substring(8,10)};
      secilen_tarih=dt[0]+dt[1]+dt[2];
       secilen_indis=i;
       Puantaj p=liste.get(i);
       double mesai=p.mesai;
       String not=p.not;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        Button btn_tarih=new Button(this);
        btn_tarih.setText("Gün Seçiniz");
        btn_tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trh=new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                       secilen_tarih=trh_cevir(i,(i1+1),i2);

                    }
                },Integer.parseInt(dt[0]),Integer.parseInt(dt[1])-1,Integer.parseInt(dt[2]));
                trh.setTitle("Tarih Seciniz");
                trh.setButton(DatePickerDialog.BUTTON_POSITIVE,"Ayarla",trh);
                trh.setButton(DatePickerDialog.BUTTON_NEGATIVE,"Iptal",trh);
                trh.show();
            }
        });
        layout.addView(btn_tarih);
        for(int a=0;a<gun_et.length;a++)
        {
            gun_et[a]=new EditText(this);
           layout.addView(gun_et[a]);
        }
        gun_et[0].setText(mesai+"");
        gun_et[1].setText(not);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Düzenle veya Sil");
        dialog.setCancelable(true);
        dialog.setView(layout);
        dialog.setPositiveButton("Düzenle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                   if(secilen_tarih.length()>0)
                   {
                       guncelle(secilen_tarih,gun_et[0].getText()+"",gun_et[1].getText()+"",id.get(secilen_indis));
                   }
                   else
                       Toast.makeText(getApplicationContext(), "Lütfen Tarihi Seçiniz", Toast.LENGTH_LONG).show();
            }
        });
        dialog.setNegativeButton("Sil", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                     sil(secilen_indis);
            }
        });
        dialog.create().show();
    }
}
