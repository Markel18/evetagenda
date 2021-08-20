package com.example.evetagenda.ui.prescription.prescription_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PrescriptionDetailsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PrescriptionDetailsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This App Is Developed For Diploma Thesis Purposes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}