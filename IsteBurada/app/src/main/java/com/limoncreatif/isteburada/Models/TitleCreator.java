package com.limoncreatif.isteburada.Models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class TitleCreator {
    static TitleCreator _titleCreator;
    List<TitleParent> _titleParents;

    public TitleCreator(Context context) {

        String basliklar[] = {"İskenderun Teknik Üniversitesi (İSTE)","Rektörlük","Öğrenci İşleri Daire Başkanlığı","Bilgi İşlem Daire Başkanlığı","İdari ve Mali İşler Daire Başkanlığı","Strateji Geliştirme Daire Başkanlığı",
        "Sağlık Kültür ve Spor Daire Başkanlığı","Personel Daire Başkanlığı","Mustafa Yazıcı Devlet Konservatuvarı","Mühendislik ve Doğa Bilimleri Fakültesi","Barbaros Hayrettin Gemi İnşaatı ve Denizcilik Fak.",
        "Deniz Bilimleri ve Teknolojisi Fakültesi","Sivil Havacılık Yüksekokulu","Turizm İşletmeciliği ve Otelcilik Yüksekokulu","Dörtyol Meslek Yüksekokulu","Denizcilik Meslek Yüksekokulu","İskenderun Meslek Yüksekokulu"};

        _titleParents = new ArrayList<>();
        for (int i=0;i<17;i++){
            TitleParent title = new TitleParent(String.format(basliklar[i]));
            _titleParents.add(title);
        }

    }

    public static TitleCreator get(Context context){
        if(_titleCreator == null)
            _titleCreator = new TitleCreator(context);
        return _titleCreator;
    }

    public List<TitleParent> getAll(){
        return _titleParents;
    }
}
