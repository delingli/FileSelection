package com.stkj.freeshare.framework.processors.resources;

import android.support.annotation.NonNull;
import android.util.SparseArray;

/**
 * @author GAM
 * @since 2018-05-06
 */
public interface IResourceProcessor<Resource> {
    boolean hasData();

    @NonNull
    SparseArray<Resource> fetchResource() throws InterruptedException;
}