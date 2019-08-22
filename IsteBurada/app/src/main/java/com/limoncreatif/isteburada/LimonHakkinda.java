package com.limoncreatif.isteburada;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import static com.limoncreatif.isteburada.MainActivity.kontrolet;

public class LimonHakkinda extends AppCompatActivity {
    Intent browserIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_limon_hakkinda);

    }

    public void alt_butonlar(View view) {
        kontrolet(this);
        switch (view.getId()) {
            case R.id.facebook:
                Toast.makeText(getApplicationContext(), "Çok Yakında...", Toast.LENGTH_LONG).show();
                break;
            case R.id.twitter:
                Toast.makeText(getApplicationContext(), "Çok Yakında...", Toast.LENGTH_LONG).show();
                break;
            case R.id.limon:
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://limoncreatif.com"));
                startActivity(browserIntent);
                break;
            case R.id.instagram:
                Toast.makeText(getApplicationContext(), "Çok Yakında...", Toast.LENGTH_LONG).show();
                break;
            default:
                Toast.makeText(getApplicationContext(), "Çok Yakında...", Toast.LENGTH_LONG).show();
                break;

        }

    }
}