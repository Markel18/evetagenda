package com.example.evetagenda.ui.note.edit_delete;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NoteEditDeleteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NoteEditDeleteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This App Is Developed For Diploma Thesis Purposes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}