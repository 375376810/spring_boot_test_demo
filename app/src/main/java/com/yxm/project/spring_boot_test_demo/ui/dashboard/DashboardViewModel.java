package com.yxm.project.spring_boot_test_demo.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("这里是黑板viewModel");
    }

    public LiveData<String> getText() {
        return mText;
    }
}