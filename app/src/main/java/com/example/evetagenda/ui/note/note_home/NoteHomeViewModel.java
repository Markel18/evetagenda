package com.example.evetagenda.ui.note.note_home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NoteHomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NoteHomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}