package com.stkj.freeshare.models.resources.application;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stkj.freeshare.framework.entities.resources.FSApplication;
import com.stkj.freeshare.framework.repositories.resources.ApplicationRepository;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.livedata.FlexibleViewModel;

/**
 * @author GAM
 * @since 2018-05-06
 */
public final class ResourceApplicationViewModel extends FlexibleViewModel<List<FSApplication>,
        AbstractFlexibleItem, Integer> {
    private final Application APPLICATION;
    private final ApplicationRepository REPOSITORY;

    ResourceApplicationViewModel(final Application application) {
        APPLICATION = application;
        REPOSITORY = new ApplicationRepository(application);
    }

    @NonNull
    @Override
    protected final LiveData<List<FSApplication>> getSource(@NonNull final Integer id) {
        return REPOSITORY.obtain(id);
    }

    @Override
    protected final boolean isSourceValid(@Nullable final List<FSApplication> applications) {
        return null != applications && !applications.isEmpty();
    }

    @Override
    protected final List<AbstractFlexibleItem> map(@NonNull final List<FSApplication> applications) {
        final List<AbstractFlexibleItem> items = new ArrayList<>();
        items.add(new ResourceApplicationItemHead(APPLICATION, applications.size()));
        for (final FSApplication app : applications) {
            items.add(new ResourceApplicationItemIcon(app));
        }
        return items;
    }
}