package com.example.ali.aspmssqlonlinedatabase.ADEPTER;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ali.aspmssqlonlinedatabase.R;

import java.util.List;

public class adepter extends BaseAdapter {

    List<kisi> liste;
    LayoutInflater layout;
    Context c;
    public adepter(Context c, List<kisi> liste){
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
        View v=layout.inflate(R.layout.colum,null);
        TextView t_ad=v.findViewById(R.id.col1);
        TextView t_soyad=v.findViewById(R.id.col2);
        TextView t_yas=v.findViewById(R.id.col3);
        kisi k=liste.get(i);
        t_ad.setText(k.ad);
        t_soyad.setText(k.soyad);
        t_yas.setText(k.yas+"");
        return v;
    }

}
