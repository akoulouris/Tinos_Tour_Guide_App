package com.example.sailinn.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.sailinn.model.ListItem;
import com.example.sailinn.repositories.ListRepository;

import java.util.List;

public class ListViewModel extends ViewModel {
    private MutableLiveData<List<ListItem>> mLists;
    private MutableLiveData<List<ListItem>> mListsBeach;
    private MutableLiveData<List<ListItem>> mListsLunch;
    private ListRepository mRepo;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void init(){
        if(mLists != null){
            return;
        }
        mRepo = ListRepository.getInstance();
        mLists = mRepo.getList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initBeach(){
        if(mLists != null){
            return;
        }
        mRepo = ListRepository.getInstance();
        mListsBeach = mRepo.getListBeach();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void initLunch(){
        if(mLists != null){
            return;
        }
        mRepo = ListRepository.getInstance();
        mListsLunch = mRepo.getListLunch();
    }
    public LiveData<List<ListItem>> getList(){
        return mLists;
    }
    public LiveData<List<ListItem>> getListBeach(){
        return mListsBeach;
    }
    public LiveData<List<ListItem>> getListLunch(){
        return mListsLunch;
    }
}
