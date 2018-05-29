package com.stkj.freeshare.framework.processors.resources;

import android.app.Application;

import com.stkj.yunos.onekey.data.Image;
import com.stkj.yunos.onekey.data.Manager;

/**
 * @author GAM
 * @since 2018-05-15
 */
public final class ImageProcessor extends AbstractResourceProcessor<Image> {
    @SuppressWarnings("unchecked")
    ImageProcessor(Application application) {
        super(Manager.getInstance().image(application));
    }
}