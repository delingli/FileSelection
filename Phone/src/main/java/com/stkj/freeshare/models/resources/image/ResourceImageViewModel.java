package com.stkj.freeshare.models.resources.image;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stkj.freeshare.framework.entities.resources.FSAlbum;
import com.stkj.freeshare.framework.repositories.resources.ImageRepository;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.livedata.FlexibleViewModel;

/**
 * @author GAM
 * @since 2018-05-15
 */
public final class ResourceImageViewModel extends FlexibleViewModel<List<FSAlbum>,
        AbstractFlexibleItem, Integer> {
    private final Application APPLICATION;
    private final ImageRepository REPOSITORY;

    ResourceImageViewModel(final Application application) {
        APPLICATION = application;
        REPOSITORY = new ImageRepository(application);
    }

    @NonNull
    @Override
    protected final LiveData<List<FSAlbum>> getSource(@NonNull final Integer id) {
        return REPOSITORY.obtain(id);
    }

    @Override
    protected final boolean isSourceValid(@Nullable final List<FSAlbum> albums) {
        return null != albums && !albums.isEmpty();
    }

    @Override
    protected final List<AbstractFlexibleItem> map(@NonNull final List<FSAlbum> albums) {
        final List<AbstractFlexibleItem> items = new ArrayList<>();
        for (FSAlbum album : albums) {
            ResourceImageItemHeader header = new ResourceImageItemHeader(album);
            for (FSAlbum.FSImage image : album.images) {
                ResourceImageItemThumb thumb =
                        new ResourceImageItemThumb(header, APPLICATION, image);
                header.addSubItem(thumb);
            }
            items.add(header);
        }
        return items;
    }
}