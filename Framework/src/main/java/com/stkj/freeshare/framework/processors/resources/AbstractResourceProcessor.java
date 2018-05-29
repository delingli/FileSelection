package com.stkj.freeshare.framework.processors.resources;

import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.aigestudio.avatar.utils.LogUtil;
import com.stkj.yunos.onekey.data.IManager;
import com.stkj.yunos.onekey.data.Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象的资源处理器类
 *
 * @param <Resource> 具体的资源类型，该泛型类型由子类提供
 * @author GAM
 * @since 2018-05-05
 */
abstract class AbstractResourceProcessor<Resource> implements IResourceProcessor<Resource>,
        Listener {
    static final String TAG = "DB_RES_PRS";

    @NonNull
    private final IManager<Resource> MANAGER;

    AbstractResourceProcessor(@NonNull IManager<Resource> manager) {
        MANAGER = manager;
    }

    @Override
    public final boolean hasData() {
        return MANAGER.hasData();
    }

    @NonNull
    @Override
    public final SparseArray<Resource> fetchResource() throws InterruptedException {
        List<Resource> tmp = new ArrayList<>();
        MANAGER.readPhone(tmp, this);
        return listToSparse(tmp);
    }

    // TODO 列表数据转为稀疏数组 该方法有可优化空间
    private SparseArray<Resource> listToSparse(List<Resource> sources) throws InterruptedException {
        SparseArray<Resource> result = new SparseArray<>();
        for (Resource model : sources) {
            if (Thread.interrupted())
                throw new InterruptedException("Thread of convert list to sparse was interrupted!");
            result.put(model.hashCode(), model);
        }
        return result;
    }

    @Override
    public final void onStart() {
        LogUtil.d(TAG, "开始读取" + MANAGER.getName() + "的数据");
    }

    @Override
    public final void onProgress(final int current, final int total) {
        LogUtil.i(TAG, "读取" + MANAGER.getName() + "数据进度为：" + current + "/" + total);
    }

    @Override
    public final void onFinished() {
        LogUtil.d(TAG, "读取" + MANAGER.getName() + "数据完毕");
    }

    @Override
    public final void onException(final Exception e) {
        LogUtil.e(TAG, "读取" + MANAGER.getName() + "数据时出现异常：" + e);
    }
}