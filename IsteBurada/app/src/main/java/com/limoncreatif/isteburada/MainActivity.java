package com.limoncreatif.isteburada;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity  {

ProgressBar prog_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);



    }
public static void  kontrolet(final Context c)
{
    if(!agkontrol.baglimi(c)) {
        final   AlertDialog.Builder dialog=new AlertDialog.Builder(c);
        dialog.setTitle("Uyarı");
        dialog.setCancelable(false);
        dialog.setMessage("İnternet bağlantınızı kontrol ediniz!!!");
        dialog.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        dialog.create().show();
    }
}
 public  long mLastClickTime=0;
    public void menu_butonlar(View view){
        if (SystemClock.elapsedRealtime() - mLastClickTime < 3000) {
            return;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

            switch (view.getId()) {
                case R.id.obs_cardview:
                    Intent obs_intent = new Intent(this, ObsWebview.class);
                    startActivity(obs_intent);

                    break;
                case R.id.akademik_cardview:
                    Intent akademik_intent = new Intent(this, AkademikTakvim.class);
                    startActivity(akademik_intent);
                    break;
                case R.id.yemekhane_cardview:
                    kontrolet(this);
                    Intent yemekhane_intent = new Intent(this, Yemekhane.class);
                    startActivity(yemekhane_intent);
                    break;
                case R.id.duyurucardview:
                    kontrolet(this);
                    Intent duyuru_intent = new Intent(this, Duyurular.class);
                    startActivity(duyuru_intent);
                    break;
                case R.id.etkinlikcardview:
                    Intent etkinlik_intent = new Intent(this, Etkinlikler.class);
                    startActivity(etkinlik_intent);
                    break;
                case R.id.iletisim_ulasim:
                    Intent iletisim_ulasim = new Intent(this,Iletisim_ulasim.class);
                    startActivity(iletisim_ulasim);
                    break;
                case R.id.lim_hakkimizda:
                    Intent limon = new Intent(this,LimonHakkinda.class);
                    startActivity(limon);
                    break;
                case R.id.iste_hakkinda:
                    Intent iste_hak = new Intent(this,OnBoardScreen.class);
                    startActivity(iste_hak);
                    break;
                case R.id.habercardview:
                    kontrolet(this);
                      startActivity(new Intent(this,habermerkezi.class));
                    break;
                case R.id.iste_arma:
                    //Logo'nun click fonksiyonu
                    browser();
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Çok Yakında...", Toast.LENGTH_LONG).show();
                    break;
            }


    }
        //Ana sayfada okul logosuna tıklandığında WebBrowserı açabilmek adına aşağıdaki kodu kullanıyoruz.
    public void browser(){
        AlertDialog.Builder uyari = new AlertDialog.Builder(this);
        uyari.setTitle("Emin Misin ?");
        uyari.setMessage("Okulumuzun internet sitesini açmak istiyor musun ?");
        uyari.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://iste.edu.tr"));
                startActivity(browserIntent);
            }
        });

        uyari.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"İptal Edildi",Toast.LENGTH_LONG).show();
            }
        });
        uyari.create().show();
    }

}
