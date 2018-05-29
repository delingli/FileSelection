package com.stkj.freeshare.models.resources.application;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * @author GAM
 * @since 2018-05-06
 */
public final class ResourceApplicationViewModelFactory implements ViewModelProvider.Factory {
    private final Application APPLICATION;

    public ResourceApplicationViewModelFactory(final Application application) {
        APPLICATION = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public final <T extends ViewModel> T create(final @NonNull Class<T> clz) {
        return (T) new ResourceApplicationViewModel(APPLICATION);
    }
}