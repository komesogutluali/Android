package adepter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ali.databasekullanm.R;

import java.util.List;

import kisi.Kisi;

public class myadepter extends BaseAdapter {
    List<Kisi> liste;
    LayoutInflater layout;
    public myadepter(Activity a,List<Kisi> liste){
        this.liste=liste;
        layout=(LayoutInflater)a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        TextView t1=(TextView)v.findViewById(R.id.ad);
        TextView t2=(TextView)v.findViewById(R.id.soyad);
        TextView t3=(TextView)v.findViewById(R.id.yas);
        Kisi k=liste.get(i);
       t1.setText(k.ad);
       t2.setText(k.soyad);
       t3.setText(""+k.yas);
        return v;
    }
}
