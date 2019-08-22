package com.limoncreatif.isteburada;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class fragment_bahar_takvim extends Fragment {

    ListView AkademikListView;
    Takvim takvim;
    takvimadepter adp;
    List<Takvim> liste;
    private static final String APP_ID = "ca-app-pub-2352686457422315~8114173480";
    private AdView mAdView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_akdemik_takvim, container, false);


        MobileAds.initialize(getContext(),APP_ID);

        mAdView = rootView.findViewById(R.id.reklam);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        AkademikListView = (ListView) rootView.findViewById(R.id.akd_listview);
        liste=new ArrayList<Takvim>();
        adp=new takvimadepter(getActivity(),liste);
        String[] str_tarih = {"04-18 Ocak", "22 Ocak", "23 Ocak", "24 Ocak", "24 Ocak", "25 Ocak", "25 Ocak - 01 Şubat", "28 Ocak - 01 Şubat", "01-28 Şubat", "04-08 Şubat", "11-12 Şubat", "08 Şubat","11-12 Şubat","11 Şubat - 17 Mayıs","11 Şubat",
        "18-31 Mayıs","31 Mayıs","10-16 Haziran","20 Mayıs - 10 Haziran","01-15 Temmuz"};
        String[] str_aciklamalar = {"Bahar Dönemi Lisansüstü (On-line) ve Yatay Geçiş Başvuru Tarihleri ",
                "Yabancı Dil (İngilizce) Sınav Tarihleri",
                "Yabancı Dil (İngilizce) Sınav Sonuçlarının İlan Tarihi",
                "Bahar Yarıyılında Açılacak Derslerin Enstitüye Son Bildirim Tarihi",
                "Bilim (Mülakat) Sınavı Tarihi",
                "Bilim (Mülakat) Sınavı Sonuçlarının İlan Tarihi",
                "Özel Öğrenci Başvuruları",
                "Kesin Kayıtlar (Yeni Kazanan Öğrenciler)",
                "Bahar Dönemi Doktora Yeterlilik Sınav Tarihleri",
                "Bahar Dönemi Kayıt Yenileme ve Ders Kayıtları",
                "Yeni Kazanan Öğrencilere Danışman Atamalarının Enstitüye Son Bildirim Tarihi",
                "Bahar Dönemi Ders Ekle/Bırak Danışman Onayları",
                "Bahar Dönemi",
                "Kesin Kayıt Yaptıran Öğrencilerin Ders Muafiyeti için Son Başvuru Tarihi",
                "Bahar Dönemi Yarıyıl Sonu Sınavları",
                "Kep Atma Töreni",
                "Bahar Dönemi Bütünleme Sınavları",
                "Tezli Yüksek Lisans ve Doktora Tez Savunma Tarihleri",
                "2019-2020 Güz Dönemi Lisansüstü (On-line) ve Yatay Geçiş Başvuru Tarihleri"};
        AkademikListView.setAdapter(adp);
        //  Toast.makeText(getContext(),"Tarih :" + str_tarih.length +" Aciklamalar :" +str_aciklamalar.length,Toast.LENGTH_LONG ).show();


        for(int i=0;i<str_aciklamalar.length;i++)
            liste.add(new Takvim(str_aciklamalar[i],str_tarih[i]));


        return rootView;
    }
}