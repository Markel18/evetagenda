package com.example.evetagenda.ui.medicine.medicine_home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MedicineHomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MedicineHomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}