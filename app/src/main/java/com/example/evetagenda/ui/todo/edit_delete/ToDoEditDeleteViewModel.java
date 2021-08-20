package com.example.evetagenda.ui.todo.edit_delete;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ToDoEditDeleteViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ToDoEditDeleteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This App Is Developed For Diploma Thesis Purposes");
    }

    public LiveData<String> getText() {
        return mText;
    }
}