package com.limoncreatif.isteburada;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ObsWebview extends AppCompatActivity {
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obs_webview);
        MainActivity.kontrolet(this);
        webview = (WebView) findViewById(R.id.obs_webview);
        webview.getSettings().setJavaScriptEnabled(true);

        webview.loadUrl("http://obs.iste.edu.tr");

        final ProgressDialog progress = ProgressDialog.show(this, "İste Öğrenci Otomasyonu", "Yükleniyor....", true);
        progress.show();
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.dismiss();
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webview.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Bir hata oluştu, lütfen daha sonra tekrar deneyin.", Toast.LENGTH_LONG).show();
                progress.dismiss();
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                return super.shouldOverrideKeyEvent(view, event);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
