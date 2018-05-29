package com.stkj.freeshare.models.resources.image;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * @author GAM
 * @since 2018-05-12
 */
public final class ResourceImageViewModelFactory implements ViewModelProvider.Factory {
    private final Application APPLICATION;

    public ResourceImageViewModelFactory(final Application application) {
        APPLICATION = application;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public final <T extends ViewModel> T create(final @NonNull Class<T> clz) {
        return (T) new ResourceImageViewModel(APPLICATION);
    }
}