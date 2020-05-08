package com.example.mye_commerceapplication.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddProductsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddProductsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Add Product");
    }

    public LiveData<String> getText() {
        return mText;
    }
}