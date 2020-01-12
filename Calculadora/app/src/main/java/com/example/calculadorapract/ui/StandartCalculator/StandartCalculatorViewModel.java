package com.example.calculadorapract.ui.StandartCalculator;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StandartCalculatorViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StandartCalculatorViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}