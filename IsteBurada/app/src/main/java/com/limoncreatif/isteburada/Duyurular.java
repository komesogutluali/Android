package com.limoncreatif.isteburada;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
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

public class Duyurular extends AppCompatActivity {
    public ProgressBar progressBar ;
    private static final String APP_ID = "ca-app-pub-2352686457422315~8114173480";
    WebView v;
   //Reklam İçin Eklenenler Başlangıç
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    private AdView mAdView;
    //Reklam İçin Eklenenler Bitti
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyurular);

        progress = ProgressDialog.show(this, "Duyurular", "Yükleniyor....", true);
        progress.show();
        MainActivity.kontrolet(this);
        //Banner Display Reklam Başlangıç
        MobileAds.initialize(getApplicationContext(),APP_ID);

        mAdView = findViewById(R.id.reklam);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //Banner Display Reklam Bitiş

    //Tam Sayfa Reklam Başlangıç
        adRequest = new AdRequest.Builder().build();

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(Duyurular.this);
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

        v=(WebView)findViewById(R.id.web);
        v.getSettings().setJavaScriptEnabled(true);
        v.loadUrl("http://limoncreatif.com/isteburada/bot.php");
        v.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
               if(haveStoragePermission()) {
                   int boyut = url.length() - 1;
                   String dosya_uzanti = "." + url.charAt(boyut - 2) + url.charAt(boyut - 1) + url.charAt(boyut);
                   DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                   request.allowScanningByMediaScanner();
                   request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                   Log.d("uzantı", dosya_uzanti);
                   Log.d("url", url);
                   request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Dosya" + dosya_uzanti);
                   DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                   dm.enqueue(request);
                   Toast.makeText(getApplicationContext(), "İndirme başladı", Toast.LENGTH_LONG).show();
               }


               }
        });
        v.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageFinished(WebView view, String url) {

                super.onPageFinished(view, url);
                progress.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                v.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Sayfa yüklenemedi Hata oluştu internet bağlantınızı kontrol ediniz", Toast.LENGTH_LONG).show();
            }
        });

    }
    public  boolean haveStoragePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission error","You have permission");
                return true;
            } else {

                Log.e("Permission error","You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //you dont need to worry about these stuff below api level 23
            Log.e("Permission error","You already have the permission");
            return true;
        }
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
