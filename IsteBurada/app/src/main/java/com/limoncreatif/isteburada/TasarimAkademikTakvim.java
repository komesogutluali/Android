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

public class TasarimAkademikTakvim extends Fragment {

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
        String[] str_tarih = {"01-16 Ağustos", "13-17 Ağustos", "29 Ağustos", "03-07 Eylül", "03 Eylül", "05 Eylül", "07 Eylül", "11 Eylül", "16 Temmuz", "10-18 Eylül", "13-18 Eylül", "17-21 Eylül","24 Eylül"
        ,"24-25 Eylül","24 Eylül - 04 Ocak 2019","28 Eylül","01-31 Ekim","19-23 Kasım","05-18 Ocak","21-27 Ocak","02-24 Ocak"};
        String[] str_aciklamalar = {"Güz Dönemi Lisansüstü (On-line) Başvuru Tarihleri.",
                "Yatay Geçiş Başvuru Tarihleri.",
                "Yatay Geçiş Başvuru Sonuçları.",
                "Yatay Geçiş Kesin Kayıt Tarihleri.",
                "Yabancı Dil (İngilizce) Sınav Tarihleri.",
                "Yabancı Dil (İngilizce) Sınav Sonuçlarının İlan Tarihi.",
                "Bilim (Mülakat) Sınavı Tarihi.",
                "Bilim (Mülakat) Sınavı Sonuçlarının İlan Tarihi.",
                "Güz Yarıyılında Açılacak Derslerin Enstitüye Son Bildirim Tarihi ",
                "Özel Öğrenci Başvuruları",
                "Kesin Kayıtlar (Yeni Kazanan Öğrenciler)",
                "Güz Dönemi Kayıt Yenileme ve Ders Kayıtları",
                "Yeni Kazanan Öğrencilere Danışman Atamalarının Enstitüye Son Bildirim Tarihi",
                "Güz Dönemi Ders Ekle/Bırak Danışman Onayları",
                "Güz Dönemi",
                "Kesin Kayıt Yaptıran Öğrencilerin Ders Muafiyeti için Son Başvuru Tarihi",
                "Güz Dönemi Doktora Yeterlik Sınav Tarihleri",
                "Dönem Arası",
                "Güz Dönemi Yarıyıl Sonu Sınavları",
                "Güz Dönemi Bütünleme Sınavları",
                "Tezli Yüksek Lisans ve Doktora Tez Savunma Tarihleri",


        };
       AkademikListView.setAdapter(adp);
     //  Toast.makeText(getContext(),"Tarih :" + str_tarih.length +" Aciklamalar :" +str_aciklamalar.length,Toast.LENGTH_LONG ).show();

      for(int i=0;i<str_aciklamalar.length;i++)
         liste.add(new Takvim(str_aciklamalar[i],str_tarih[i]));


        return rootView;
    }
}