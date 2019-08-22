package com.example.ali.sifresaklama.Adepter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.text.method.TransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.sifresaklama.R;
import com.example.ali.sifresaklama.SifreBilgi;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class Bilgiadp extends BaseAdapter implements View.OnClickListener {

    List<SifreBilgi> liste;
    LayoutInflater layout;
    Context c;
    public Bilgiadp(Context c, List<SifreBilgi> liste){
        this.c=c;
        this.liste=liste;
        layout=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int i) {
        return liste.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=layout.inflate(R.layout.bilgisablonu,null);
        TextView t_aciklama=v.findViewById(R.id.text_aciklama);
        TextView t_kullanici=v.findViewById(R.id.text_kullanici);
        TextInputEditText t_sifre=v.findViewById(R.id.text_sifre);
        t_sifre.setKeyListener(null);
        SifreBilgi sb=liste.get(i);
        t_aciklama.setText(sb.aciklamasi);
        t_kullanici.setText(sb.kullanici_ad);
        t_sifre.setText(sb.sifre);
        ImageView btn_kullanici=v.findViewById(R.id.btn_kullanici);
        ImageView  btn_sifre=v.findViewById(R.id.btn_sifre);
        btn_kullanici.setTag(sb.kullanici_ad);
        btn_sifre.setTag(sb.sifre);
        btn_kullanici.setOnClickListener(this);
        btn_sifre.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {

        ClipboardManager  myClipboard = (ClipboardManager)c.getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", view.getTag()+"");
        myClipboard.setPrimaryClip(myClip);
        Toast.makeText(c,myClipboard.getPrimaryClip().getItemAt(0).getText()+" Panoya Kopyalandı.",Toast.LENGTH_LONG).show();

    }
}
