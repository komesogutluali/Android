package com.limoncreatif.isteburada;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class OnBoardScreen extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.limoncreatif.isteburada", Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("oneboard",1).apply();

        setPrevText("Geri"); // Previous button text
        setNextText("İleri"); // Next button text
        setFinishText("Bitti !"); // Finish button text
        setCancelText("Bitir"); // Cancel button text


        addFragment(
                new Step.Builder()
                        .setTitle("Hoş Geldin!")
                        .setContent("İste Burada uygulaması ile okulumuzun web sitesini senin için olabildiğince kolaylaştırmak istedik.Hazırsan hemen neler yaptık sana anlatalım. :)")
                        .setBackgroundColor(Color.parseColor("#FF0957"))
                        .setDrawable(R.drawable.logo_onboard)
                        .setSummary(getString(R.string.continue_and_learn))
                        .build());


        addFragment(
                new Step.Builder()
                        .setTitle(getString(R.string.permission_title))
                        .setContent(getString(R.string.permission_detail))
                        .setBackgroundColor(Color.parseColor("#FF0957"))
                        .setDrawable(R.drawable.yemekhane_onboard)
                        .setSummary(getString(R.string.continue_and_learn))
                        .build());
        addFragment(
                new Step.Builder()
                        .setTitle(getString(R.string.automatic_data))
                        .setContent(getString(R.string.gm_finds_photos))
                        .setBackgroundColor(Color.parseColor("#29536E"))
                        .setDrawable(R.drawable.duyurular_onboard)
                        .setSummary(getString(R.string.continue_and_learn))
                        .build());
        addFragment(
                new Step.Builder()
                        .setTitle(getString(R.string.choose_the_song))
                        .setContent(getString(R.string.swap_to_the_tab))
                        .setBackgroundColor(Color.parseColor("#ff3c00"))
                        .setDrawable(R.drawable.akademik_onboard)
                        .setSummary(getString(R.string.continue_and_learn))
                        .build());
        addFragment(
                new Step.Builder()
                        .setTitle(getString(R.string.edit_data))
                        .setContent(getString(R.string.update_easily))
                        .setBackgroundColor(Color.parseColor("#022382"))
                        .setDrawable(R.drawable.hepsibirarada_oneboard)
                        .setSummary(getString(R.string.continue_and_result))
                        .build());
        addFragment(
                new Step.Builder()
                        .setTitle(getString(R.string.result_awesome))
                        .setContent(getString(R.string.after_updating))
                        .setBackgroundColor(Color.parseColor("#ffa800"))
                        .setDrawable(R.drawable.iste_onboard)
                        .setSummary(getString(R.string.thank_you))
                        .build());
    }

    @Override
    public void finishTutorial() {
        Intent main = new Intent(this,MainActivity.class);
        startActivity(main);
        finish();
    }


}