package com.example.evetagenda.ui.customer.edit_delete;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CustomerEditDeleteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CustomerEditDeleteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This App Is Developed For Diploma Thesis Purposes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}