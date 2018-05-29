package com.stkj.freeshare.framework.repositories.resources;

import android.app.Application;
import android.util.SparseArray;

import com.stkj.freeshare.framework.entities.resources.FSApplication;
import com.stkj.freeshare.framework.processors.resources.ResourceProcessor;
import com.stkj.yunos.onekey.data.App;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GAM
 * @since 2018-05-06
 */
public final class ApplicationRepository extends AbstractResourceRepository<List<FSApplication>, App> {
    public ApplicationRepository(Application application) {
        super(application, ResourceProcessor.PROCESS_APPLICATION);
    }

    @Override
    final List<FSApplication> covert(final SparseArray<App> source) throws InterruptedException {
        List<FSApplication> result = new ArrayList<>();
        for (int i = 0; i < source.size(); i++) {
            if (Thread.interrupted())
                throw new InterruptedException("Thread of convert sparse to list was interrupted!");
            App app = source.valueAt(i);
            if (null == app) continue;
            result.add(new FSApplication(app.packageName, app.sourceDir));
        }
        return result;
    }
}