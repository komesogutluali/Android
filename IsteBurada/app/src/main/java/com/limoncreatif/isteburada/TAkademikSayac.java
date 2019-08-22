package com.limoncreatif.isteburada;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TAkademikSayac extends Fragment {

    private static final String APP_ID = "ca-app-pub-2352686457422315~8114173480";
    private AdView mAdView;
    TextView final_kalan,but_kalan,tatil_kalan,son_kalan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.fragment_akdemik_takvim_sayac,container,false);

        //Reklam Kodları Başlangıç
        MobileAds.initialize(getContext(),APP_ID);
        mAdView = rootView.findViewById(R.id.reklam);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //Reklam Kodları Bitiş
        //TextView Tanıyorum
        final_kalan = (TextView) rootView.findViewById(R.id.final_tarih);
        but_kalan=(TextView)rootView.findViewById(R.id.butler);
        tatil_kalan=(TextView)rootView.findViewById(R.id.tatil);
        son_kalan = (TextView) rootView.findViewById(R.id.okul_sonu);
        final_kalan.setText(tarih_hesapla("05.01.2019.08"));
        but_kalan.setText(tarih_hesapla("21.01.2019.08"));
        tatil_kalan.setText(tarih_hesapla("18.01.2019.08"));
        son_kalan.setText(tarih_hesapla("16.06.2019.09"));

        return rootView;
    }

    public String tarih_hesapla(String tarih2){//Hedef tarih
            Date hedef_tarih=new Date();
            SimpleDateFormat hedefformat=new SimpleDateFormat("dd.MM.yyyy.HH");
            String tarih1=hedefformat.format(hedef_tarih);
            Calendar cal = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.HH", Locale.US);
            try {
                cal.setTime(sdf.parse(tarih1));
                cal2.setTime(sdf.parse(tarih2));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date d1 = new Date(cal.getTimeInMillis());
            Date d2 = new Date(cal2.getTimeInMillis());

            long diff = d2.getTime()- d1.getTime() ;
            long h=diff / (1000*60*60);
            long d=h/24;
            long m=d/30;
            d=d%30;
            h-=(d*24+((m*30)*24));
            if(h<0||d<0||m<0)
            {
                m=0;
                d=0;
                h=0;
            }
            return (m+"  "+d+"  "+h);

    }
}

