package com.limoncreatif.isteburada;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class takvimadepter  extends BaseAdapter {

    List<Takvim> liste;
    LayoutInflater layout;
    public takvimadepter(Activity a, List<Takvim> liste)
    {
        this.liste=liste;
        layout=(LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return liste.size();
    }

    @Override
    public Object getItem(int position) {
        return liste.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=layout.inflate(R.layout.colum,null);
        TextView t1=(TextView)v.findViewById(R.id.col1);
        TextView t2=(TextView)v.findViewById(R.id.col2);
        Takvim t=liste.get(position);//adepteree atılan takvim tipinde veri tipini aldık
        t1.setText(t.aciklama);//aciklama kısmını ilk textviewe atıyoruz
        t2.setText(t.tarih);//tarih kısmını ikinci textviewe atıyoruz
        return v;
    }
}
