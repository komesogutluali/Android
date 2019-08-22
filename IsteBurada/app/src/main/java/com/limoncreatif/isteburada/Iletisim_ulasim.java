package com.limoncreatif.isteburada;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.limoncreatif.isteburada.Adapter.MyAdapter;
import com.limoncreatif.isteburada.Models.TitleChild;
import com.limoncreatif.isteburada.Models.TitleCreator;
import com.limoncreatif.isteburada.Models.TitleParent;

import java.util.ArrayList;
import java.util.List;

public class Iletisim_ulasim extends AppCompatActivity {

    RecyclerView recyclerView;
    String santral[] = {"0.326 613 56 00 – 613 70 80 – 617 82 76","0.326 613 56 00","0.326 617 82 75  – 613 56 00","0.326 613 56 00 – 613 70 80","0.326 613 56 00 – 613 70 80",
            "0.326 613 56 00 – 613 70 80","0.326 613 56 00 – 613 70 80","0.326 613 56 00 –613 70 80","0.326 614 36 37 – 614 13 79","0.326 613 56 00 - 613 70 80","0.326 613 10 44",
            "0.326 614 16 93 – 614 16 96","0.326 613 56 00 - 613 70 80","0.326 613 56 00 – 613 70 80","0.326 764 83 33 – 764 82 41","0.326 618 92 95 – 618 93 96","0.326 618 29 31 – 618 29 32"};
    String fax[] = {"0.326 613 56 13","0.326 613 56 13","0.326 613 56 13","0.326 613 56 13","0.326 613 56 13",
            "0.326 613 56 13","0.326 613 56 13","0.326 613 56 13","0.326 614 11 15","0.326 613 56 13","0.326 613 10 44","0.326 614 18 77","0.326 641 65 16","0.326 641 65 16","0.326 764 84 44","0.326 618 22 97","0.326 618 29 30 – 618 29 22"};


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((MyAdapter)recyclerView.getAdapter()).onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iletisim_ulasim);
        
        recyclerView = (RecyclerView)findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MyAdapter adapter = new MyAdapter(this,initData());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        
        recyclerView.setAdapter(adapter);
    }

    private List<ParentObject> initData() {
        TitleCreator titleCreator = TitleCreator.get(this);
        List<TitleParent> titles = titleCreator.getAll();
        List<ParentObject> parentObjects = new ArrayList<>();
        int i = 0;
        for (TitleParent title:titles){
            List<Object> childList = new ArrayList<>();
            childList.add(new TitleChild("Telefon : " + santral[i],"Fax : "+ fax[i]));
            title.setChildObjectList(childList);

            parentObjects.add(title);
            i++;
        }
        return parentObjects;
    }
}
