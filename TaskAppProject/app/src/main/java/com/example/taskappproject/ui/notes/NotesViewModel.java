package com.example.taskappproject.ui.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}