package com.yxm.project.spring_boot_test_demo.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("这里是主页viewModel");
    }

    public LiveData<String> getText() {
        return mText;
    }
}