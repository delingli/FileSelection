package com.stkj.freeshare.models.resources.files;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;

import com.stkj.freeshare.models.resources.application.ResourceApplicationViewModel;

public class ResourceFilesViewModelFractory implements ViewModelProvider.Factory {
    private final Application APPLICATION;

    public ResourceFilesViewModelFractory(Application APPLICATION) {
        this.APPLICATION = APPLICATION;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
     return    (T) new ResourceFliesViewModel(APPLICATION);
    }
}
