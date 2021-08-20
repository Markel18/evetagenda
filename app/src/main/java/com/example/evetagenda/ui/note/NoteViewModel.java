package com.example.evetagenda.ui.note;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NoteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NoteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is List fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}