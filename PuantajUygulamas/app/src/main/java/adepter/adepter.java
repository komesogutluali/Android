package adepter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ali.puantajuygulamas.MainActivity;
import com.example.ali.puantajuygulamas.R;

import java.util.List;

import puantaj.Puantaj;



public class adepter extends BaseAdapter  {
    public List<Puantaj> liste;
    LayoutInflater lyt;
    public adepter(Activity ac,List<Puantaj> liste)
    {
        this.liste=liste;
        lyt=(LayoutInflater)ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
      View nesne=lyt.inflate(R.layout.layout,null);
      int[] id={R.id.col1,R.id.col2,R.id.col3};
       final TextView[] text=new TextView[id.length];
      for(int a=0;a<id.length;a++)
          text[a]=(TextView)nesne.findViewById(id[a]);
         Puantaj p=liste.get(i);
         text[0].setText(p.gun);
         text[1].setText(p.mesai+"");
         text[2].setText(p.not);
         text[2].setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 MainActivity.notgoster(text[2].getText()+"");
             }
         });
        return nesne;
    }


    }

