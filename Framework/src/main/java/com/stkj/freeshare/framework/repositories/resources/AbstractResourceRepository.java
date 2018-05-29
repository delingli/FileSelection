package com.stkj.freeshare.framework.repositories.resources;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.aigestudio.avatar.utils.LogUtil;
import com.stkj.freeshare.framework.processors.resources.IResourceProcessor;
import com.stkj.freeshare.framework.processors.resources.ResourceProcessor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @param <ViewModel>
 * @param <DataModel>
 * @author GAM
 * @since 2018-05-06
 */
abstract class AbstractResourceRepository<ViewModel, DataModel> {
    private static final int COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE = Math.max(2, Math.min(COUNT - 1, 4));
    private static final int MAXIMUM = COUNT * 2 + 1;
    private static final int SECONDS = 30;
    private static final BlockingQueue<Runnable> QUEUE = new LinkedBlockingQueue<>(128);
    private static final ThreadFactory FACTORY = new ThreadFactory() {
        private final AtomicInteger COUNT = new AtomicInteger(1);

        @Override
        public final Thread newThread(@NonNull final Runnable r) {
            Thread t = new Thread(r, "RESOURCE_REPOSITORY #" + COUNT.getAndIncrement());
            t.setPriority(Thread.NORM_PRIORITY + 1);
            return t;
        }
    };
    private static final RejectedExecutionHandler HANDLER = new RejectedExecutionHandler() {
        @Override
        public final void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
            LogUtil.e("DB_RES_REPO", "==========执行任务时被线程池拒绝==========");
            final boolean isShutdown = executor.isShutdown();
            final boolean isTerminated = executor.isTerminated();
            final boolean isTerminating = executor.isTerminating();
            LogUtil.e("DB_RES_REPO", "线程池是否关闭：" + isShutdown +
                    ", 线程池是否已经终止：" + isTerminated + ", 线程池是否正在终止：" + isTerminating);
            final int active = executor.getActiveCount();
            final long task = executor.getTaskCount();
            LogUtil.e("DB_RES_REPO", "线程池正在执行的任务数量为：" + active +
                    ", 线程池尚未执行的任务数量为：" + task);
        }
    };
    protected static  Executor EXECUTOR;

    static {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORE, MAXIMUM, SECONDS,
                TimeUnit.SECONDS, QUEUE, FACTORY, HANDLER);
        executor.allowCoreThreadTimeOut(true);
        EXECUTOR = executor;
    }

    protected final IResourceProcessor<DataModel> PROCESSOR;

    @SuppressWarnings("unchecked")
    AbstractResourceRepository(final Application application, final String processor) {
        PROCESSOR = ResourceProcessor.get(application, processor);
    }

    @SuppressWarnings("unused")
    public final MutableLiveData<ViewModel> obtain(final int hashcode) {
        final MutableLiveData<ViewModel> liveData = new MutableLiveData<>();
        EXECUTOR.execute(new Runnable() {
            @Override
            public final void run() {
                try {
                    liveData.postValue(covert(PROCESSOR.fetchResource()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return liveData;
    }

    /**
     * 将后端存储数据转换为前端显示数据
     *
     * @param source 后端存储数据，在现有的逻辑中特制从资源处理器中所获取到的逻辑，目前为止从资源处理中所获取的数
     *               据均为{@link SparseArray}稀疏数组类型
     * @return 前端显示数据，具体数据类型根据子类实现决定
     * @throws InterruptedException 线程中断的必检异常，如果该方法不处于子线程中该异常捕获处理可忽略
     */
    abstract ViewModel covert(SparseArray<DataModel> source) throws InterruptedException;
}