package com.stkj.freeshare.framework.processors.resources;

import android.app.Application;

import com.stkj.yunos.onekey.data.App;
import com.stkj.yunos.onekey.data.Manager;

/**
 * 具体的处理应用资源的处理器
 *
 * @author GAM
 * @since 2018-05-06
 */
final class ApplicationProcessor extends AbstractResourceProcessor<App> {
    @SuppressWarnings("unchecked")
    ApplicationProcessor(Application application) {
        super(Manager.getInstance().app(application));
    }
}