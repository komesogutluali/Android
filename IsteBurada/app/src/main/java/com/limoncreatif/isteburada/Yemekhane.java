package com.limoncreatif.isteburada;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class Yemekhane extends AppCompatActivity {
    Element el;
    TextView t_yemek,title_yemekgun,title_yemektarih;
    ProgressBar bar;
    String[] tr_tarih = {"Pazar","Pazartesi","Salı","Çarşamba","Perşembe","Cuma","Cumartesi"};
    Date now;
    Calendar myDate;
    int tarih_sayac = 0;
    String secilen_tarih = "";
    long oneDayLongValue = 1000 * 60 * 60 * 24;
    SimpleDateFormat myFormat ;
    Date bugunun_tarihi;
    int dow;
    private static final String APP_ID = "ca-app-pub-2352686457422315~8114173480";

    //Reklam İçin Eklenenler Başlangıç
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    private AdView mAdView;
    //Reklam İçin Eklenenler Bitti

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yemekhane);

        MainActivity.kontrolet(this);
        t_yemek=(TextView)findViewById(R.id.txt);
        title_yemekgun = (TextView) findViewById(R.id.title_gun);
        title_yemektarih = (TextView) findViewById(R.id.title_tarih);
        myDate = Calendar.getInstance(); // Bu kısım önemli
        now=new Date();
        //Bugünün Tarihine +1 Ekliyoruz ve Tarihi Yazdırıyoruz
        myFormat = new SimpleDateFormat("dd-MM-yyyy");
        bugunun_tarihi = new Date(now.getTime()+tarih_sayac*oneDayLongValue);
        dow = myDate.get (Calendar.DAY_OF_WEEK);
        //Tarih için tek bir fonksiyon kullanıyoruz.

        bar=(ProgressBar)findViewById(R.id.pbar);
        title_yemektarih.setText(myFormat.format(bugunun_tarihi));
        //Haftanın günler Pazardan başladığı için -1 alt kısma ekledik.
        title_yemekgun.setText(tr_tarih[tarih_sayac+(dow-1)]);
        //Yemek Listesi Fonksiyonunu Tekrar Çağırıyoruz
        secilen_tarih=title_yemektarih.getText().toString();
        yemek_listesi_getir(secilen_tarih);

        //Banner Display Reklam Başlangıç
        MobileAds.initialize(getApplicationContext(),APP_ID);

        mAdView = findViewById(R.id.reklam);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //Banner Display Reklam Bitiş

        //Tam Sayfa Reklam Başlangıç
        adRequest = new AdRequest.Builder().build();

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(Yemekhane.this);
        // Insert the Ad Unit ID
        interstitial.setAdUnitId(getString(R.string.admob_interstitial_id));

        interstitial.loadAd(adRequest);
        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                //  displayInterstitial() fonksiyonunu çağırıyoruz
                displayInterstitial();
            }
        });
        //Tam Sayfa Reklam Bitiş
    }
    public void yemek_listesi_getir(final String tarih){
        new Thread() {
            public void run() {

                Document doc;
                try {

                    //URL kısmına bugünün tarihini ekliyoruz.
                    doc = Jsoup.connect("https://limoncreatif.com/isteburada/yemek.php?tarih="+tarih).get();
                    el=doc.body();

                    runOnUiThread( new Runnable()
                    {
                        public void run()
                        {
                            String yemek="";
                            for (int i=0;i<el.text().length();i++)
                            {
                                if(el.text().charAt(i)!=')') {
                                    yemek += el.text().charAt(i);
                                    yemek.trim();
                                }
                                else
                                {
                                    yemek+=el.text().charAt(i);
                                    yemek+="\n"+"\n";
                                }

                            }
                            t_yemek.setText(yemek);
                            bar.setVisibility(View.GONE);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    //Yemekhane alt butonları için ortak Click fonksiyonu
    public void yemek_tarihleri (View view){
        switch (view.getId()){
            case R.id.ileri_trh:
                tarih_sayac++;
                tarih_dondur();
                break;
            case R.id.geri_trh:
                tarih_sayac--;
                tarih_dondur();
                break;
            case R.id.yemek_tarih_sec:
              tarih_sec();
                break;
        }

    }
    public void tarih_dondur(){
        bar.setVisibility(View.VISIBLE);
        Date secilen_date=null;
        try {
             secilen_date=myFormat.parse(secilen_tarih);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        bugunun_tarihi = new Date(secilen_date.getTime()+tarih_sayac*oneDayLongValue);
        title_yemektarih.setText(myFormat.format(bugunun_tarihi));
        //Haftanın günler Pazardan başladığı için -1 alt kısma ekledik.
        int tarihsayaci2=tarih_sayac+(dow-1);
        tarihsayaci2=tarihsayaci2%7;
        if(tarihsayaci2<0)
            tarihsayaci2 = 7 - (tarihsayaci2 * (-1));
        title_yemekgun.setText(tr_tarih[tarihsayaci2]);
        //Yemek Listesi Fonksiyonunu Tekrar Çağırıyoruz
        yemek_listesi_getir(title_yemektarih.getText().toString());
    }
    public String trh_cevir(int y,int a,int g)
    {  String gun="",ay="";
        if(a<10)
            ay="0"+a;
        else
            ay=a+"";
        if(g<10)
            gun="0"+g;
        else
            gun=g+"";
        return gun+"-"+ay+"-"+y;
    }
    public void tarih_sec()
    {
        int year = myDate.get(Calendar.YEAR);
        int month = myDate.get(Calendar.MONTH);
        int dayOfMonth = myDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        bar.setVisibility(View.VISIBLE);

                secilen_tarih =trh_cevir(year,(month+1),dayOfMonth);
                yemek_listesi_getir(secilen_tarih);
                title_yemektarih.setText(secilen_tarih);
                String secilen_name=daysNameOfWeek(secilen_tarih);
                int secilen_indis=0;
                for(int i=0;i<tr_tarih.length;i++)
                    if(secilen_name.equals(tr_tarih[i]))
                    {
                        secilen_indis=i;
                        break;
                    }
                   dow=secilen_indis+1;
                   tarih_sayac=0;
                   title_yemekgun.setText(tr_tarih[secilen_indis]);

            }
        },year,month,dayOfMonth);
        dpd.setTitle("Tarih Seçiniz");
        dpd.setButton(DatePickerDialog.BUTTON_POSITIVE,"AYARLA",dpd);
        dpd.setButton(DatePickerDialog.BUTTON_NEGATIVE,"IPTAL",dpd);
        dpd.show();
    }
    public String daysNameOfWeek(String inputDate){
        SimpleDateFormat simple=new SimpleDateFormat("dd-MM-yyyy");
        String daysName="";
        try {

            Date date = simple.parse(inputDate.trim());
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            daysName  = outFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return daysName;
    }

    //Reklamı Yüklemek İçin Kullanılan Fonksiyon Başlangıç
    public void displayInterstitial() {
// If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
    //Reklamı Yüklemek İçin Kullanılan Fonksiyon Bitiş

}