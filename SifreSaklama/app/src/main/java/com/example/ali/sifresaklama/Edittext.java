package com.example.ali.sifresaklama;

import android.widget.EditText;

public class Edittext {
    public static  boolean text_kontrol_et(EditText[] edittext)
    {
        int sayac=0;
        for(int i=0;i<edittext.length;i++)
            if(edittext[i].getText()!=null&&edittext[i].getText().length()>0)
                sayac++;
        if(sayac==edittext.length)
            return  true;
        else
            return  false;
    }
    public static void clear(EditText[] text)
    {
        for(int i=0;i<text.length;i++)
            text[i].setText("");
    }
}
