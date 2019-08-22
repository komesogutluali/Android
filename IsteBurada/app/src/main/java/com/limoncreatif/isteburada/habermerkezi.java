package com.limoncreatif.isteburada;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class habermerkezi extends AppCompatActivity {
    ProgressDialog progress;
    WebView web;
    private static final String APP_ID = "ca-app-pub-2352686457422315~8114173480";
    //Reklam İçin Eklenenler Başlangıç
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    private AdView mAdView;
    //Reklam İçin Eklenenler Bitti
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habermerkezi);

        //Banner Display Reklam Başlangıç
        MobileAds.initialize(getApplicationContext(),APP_ID);

        mAdView = findViewById(R.id.reklam);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //Banner Display Reklam Bitiş

        //Tam Sayfa Reklam Başlangıç
        adRequest = new AdRequest.Builder().build();

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(habermerkezi.this);
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
        //Tam Sayfa Reklam Bitişy.kontrolet(this);



        progress = ProgressDialog.show(this, "Haber Merkezi", "Yükleniyor....", true);
        progress.show();
        MainActivity.kontrolet(this);
        web=(WebView)findViewById(R.id.habermerkezi_web);
        web.getSettings().setJavaScriptEnabled(true);

        web.loadUrl("http://limoncreatif.com/isteburada/haberbot.php");
        web.setWebViewClient(new WebViewClient() {




            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
              progress.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                web.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Sayfa yüklenemedi Hata oluştu internet bağlantınızı kontrol ediniz", Toast.LENGTH_LONG).show();
            }
        });
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
