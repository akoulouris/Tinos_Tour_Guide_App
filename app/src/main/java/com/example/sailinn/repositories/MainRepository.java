package com.example.sailinn.repositories;
import android.arch.lifecycle.MutableLiveData;

import com.example.sailinn.R;
import com.example.sailinn.model.MainMenu;
import java.util.ArrayList;
import java.util.List;
/**
 * Singleton pattern
 */
public class MainRepository {
    private static MainRepository instance;
    private ArrayList<MainMenu> dataSet = new ArrayList<>();

    public static MainRepository getInstance() {
        if (instance == null) {
            instance = new MainRepository();
        }
        return instance;
    }

    // Pretend to get data from a webservice or online source
    public MutableLiveData<List<MainMenu>> getMainMenu(){
        dataSet.clear();
        setMainMenu();
        MutableLiveData<List<MainMenu>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setMainMenu(){
        dataSet.add(
                new MainMenu("Beaches",
                        R.drawable.beach)
        );
        dataSet.add(
                new MainMenu("Lunch",
                        R.drawable.lunch)
        );
        dataSet.add(
                new MainMenu("Drinks",
                        R.drawable.drinks)
        );
        dataSet.add(
                new MainMenu("Coffee",
                        R.drawable.coffee)
        );
        dataSet.add(
                new MainMenu("Bars",
                        R.drawable.drinkblack)
        );
        dataSet.add(
                new MainMenu("Bars",
                        R.drawable.drinkblack)
        );

    }
}
