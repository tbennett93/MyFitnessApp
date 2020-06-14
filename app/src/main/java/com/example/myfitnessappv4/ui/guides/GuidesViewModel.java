package com.example.myfitnessappv4.ui.guides;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GuidesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GuidesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is guides fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}