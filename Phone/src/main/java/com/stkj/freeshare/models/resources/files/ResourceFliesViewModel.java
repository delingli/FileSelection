package com.stkj.freeshare.models.resources.files;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.stkj.freeshare.R;
import com.stkj.freeshare.framework.entities.resources.FileImpl;
import com.stkj.freeshare.framework.repositories.resources.FileRepository;
import com.stkj.freeshare.framework.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.livedata.FlexibleViewModel;

public class ResourceFliesViewModel extends FlexibleViewModel<List<FileImpl>,
        AbstractFlexibleItem, Integer> {
    private final Application APPLICATION;
    private final FileRepository REPOSITORY;
    private String filePath;

    public ResourceFliesViewModel(Application application) {
        String mPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        APPLICATION = application;
        REPOSITORY = new FileRepository(application);
    }

    public void FeatchFileByPath(String path) {
        REPOSITORY.FeatchFileByPath(path);
    }

    @NonNull
    @Override
    protected LiveData<List<FileImpl>> getSource(@NonNull Integer integer) {
        return REPOSITORY.searchFile();
    }


    @Override
    protected boolean isSourceValid(@Nullable List<FileImpl> files) {
        return null != files && !files.isEmpty();
    }

    /**
     * 转换成是适配器需要的
     *
     * @param files
     * @return
     */
    @Override
    protected List<AbstractFlexibleItem> map(@NonNull List<FileImpl> files) {
        final List<AbstractFlexibleItem> items = new ArrayList<>();
        for (FileImpl f : files) {
            items.add(new ResourceFilesItem(f));
        }
        return items;
    }
}



