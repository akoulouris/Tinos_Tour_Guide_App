package com.example.sailinn.repositories;

import android.arch.lifecycle.MutableLiveData;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.sailinn.R;
import com.example.sailinn.model.ListItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListRepository {
    private static ListRepository instance;
    private List<ListItem> dataSet = new ArrayList<>();

    public static ListRepository getInstance() {

        if (instance == null) {
            instance = new ListRepository();
        }
        return instance;
    }

    // Pretend to get data from a webservice or online source
    @RequiresApi(api = Build.VERSION_CODES.N)
    public MutableLiveData<List<ListItem>> getList(){
        dataSet.clear();
        setListBeaches();
        MutableLiveData<List<ListItem>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

   @RequiresApi(api = Build.VERSION_CODES.N)
   public MutableLiveData<List<ListItem>> getListBeach(){
        dataSet.clear();
        setListBeaches();
        MutableLiveData<List<ListItem>> data = new MutableLiveData<>();
       data.setValue(dataSet.stream().filter(article -> article.getCategory().contains("Beach")).collect(Collectors.toList()));
       return data;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public MutableLiveData<List<ListItem>> getListLunch(){
        dataSet.clear();
        setListBeaches();
        MutableLiveData<List<ListItem>> data = new MutableLiveData<>();
        data.setValue(dataSet.stream().filter(article -> article.getCategory().contains("Lunch")).collect(Collectors.toList()));
        return data;
    }
    private void setListBeaches(){
        dataSet.add(
                new ListItem( R.drawable.bars,"Kolimvithra","0","0" ,"Beach")
        );
        dataSet.add(
                new ListItem(R.drawable.beaches,"Pachia Ammos","1","0" ,"Beach")
        );
        dataSet.add(
                new ListItem(R.drawable.carousel1,"Rochari","2","0" ,"Beach")
        );
        dataSet.add(
                new ListItem(R.drawable.carousel1,"Livada","3","0" ,"Beach")
        );
        dataSet.add(
                new ListItem(R.drawable.carousel1,"Agios Romanos","4","0" ,"Beach")
        );
        dataSet.add(
                new ListItem(R.drawable.carousel1,"Kalyvia","5","0" ,"Lunch")
        );
    }


}
