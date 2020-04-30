package com.example.sailinn.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.example.sailinn.model.MainMenu;
import com.example.sailinn.repositories.MainRepository;

import java.util.List;

public class MainViewModel extends ViewModel {
    private MutableLiveData<List<MainMenu>> mManMenu;
    private MainRepository mRepo;

    public void init(){
        if(mManMenu != null){
            return;
        }
        mRepo = MainRepository.getInstance();
        mManMenu = mRepo.getMainMenu();
    }

    public LiveData<List<MainMenu>> getMainMenu(){
        return mManMenu;
    }

}
