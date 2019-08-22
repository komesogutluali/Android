package com.limoncreatif.isteburada;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Etkinlikler extends AppCompatActivity implements View.OnClickListener{
    public long  mLastClickTime=0;
    public Bitmap bimage;
    ProgressBar bar;
    TextView[] text;
    Document doc;
    Element el;
    String[] data,link;
    int[] text_id = {R.id.text1,R.id.text2,R.id.text3,R.id.text4,R.id.text5,R.id.text6,R.id.text7,R.id.text8,R.id.text9,R.id.text10};
    AlertDialog.Builder dialog;
    int secilen_i=0;
    Context c;
    int width;
    int height;
    private static final String APP_ID = "ca-app-pub-2352686457422315~8114173480";
    private AdView mAdView;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etkinlikler);
        text=new TextView[text_id.length];
        c=this;

        MobileAds.initialize(getApplicationContext(),APP_ID);

        mAdView = findViewById(R.id.reklam);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        for (int i = 0; i < text_id.length; i++) {
            text[i]=(TextView)findViewById(text_id[i]);
            text[i].setOnClickListener(this);
        }
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;
        bar = (ProgressBar) findViewById(R.id.bar);
        bar.setVisibility(View.GONE);
        progress = ProgressDialog.show(this, "Etkinlikler", "Yükleniyor....", true);
        progress.show();
        MainActivity.kontrolet(this);
    }
    @Override
    protected void onStart() {
        super.onStart();

        new Thread() {
            public void run() {
                try {
                    doc = Jsoup.connect("https://limoncreatif.com/isteburada/etkinlik.php").get();
                    el = doc.body();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            data = el.text().split("#");
                            link=new String[data.length];
                            for(int i=0;i<data.length;i++)
                            {
                                String[] veri = data[i].split("!");
                                link[i]=veri[0];
                                text[i].setText(veri[1]);
                            }
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            progress.dismiss();

                        }

                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    @Override
    public void onClick(View view) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
        MainActivity.kontrolet(this);
        bar.setVisibility(View.VISIBLE);
        int secilen_id = view.getId();
        for (int i = 0; i < text.length; i++)
            if (secilen_id == text[i].getId()) {
                secilen_i = i;
                break;
            }
        new Thread() {
            public void run() {
                InputStream in = null;
                try {

                    in = new URL(link[secilen_i]).openStream();
                    bimage =BitmapFactory.decodeStream(in);
                    bimage=Bitmap.createScaledBitmap(bimage, width,height, true);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            dialog = new AlertDialog.Builder(c);
                            ImageView secilen_img = new ImageView(c);
                            secilen_img.setImageBitmap(bimage);
                            secilen_img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                            dialog.setView(secilen_img);
                            dialog.setCancelable(false);
                            dialog.setPositiveButton("Kapat", null);
                            dialog.setTitle("Etkinlik");
                            AlertDialog alertDialog = dialog.create();
                            alertDialog.show();
                            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
                            bar.setVisibility(View.GONE);


                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
