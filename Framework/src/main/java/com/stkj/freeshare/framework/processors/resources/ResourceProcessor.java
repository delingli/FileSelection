package com.stkj.freeshare.framework.processors.resources;

import android.app.Application;

/**
 * @author GAM
 * @since 2018-05-06
 */
public final class ResourceProcessor {
    public static final String PROCESS_APPLICATION = "application";
    public static final String PROCESS_IMAGE = "image";
    public static final String PROCESS_ALLFILE = "allfile";
    public static IResourceProcessor get(final Application application, final String name) {
        switch (name) {
            case PROCESS_APPLICATION:
                return getApplicationProcessor(application);
            case PROCESS_IMAGE:
                return getImageProcessor(application);
            case PROCESS_ALLFILE:
                return getAllFileProcessor(application);
        }
        throw new UnsupportedOperationException("Unknown processor named " + name);
    }

    private static volatile ApplicationProcessor INSTANCE_PROCESSOR_APPLICATION;

    private static ApplicationProcessor getApplicationProcessor(final Application application) {
        if (null == INSTANCE_PROCESSOR_APPLICATION) synchronized (ApplicationProcessor.class) {
            if (null == INSTANCE_PROCESSOR_APPLICATION)
                INSTANCE_PROCESSOR_APPLICATION = new ApplicationProcessor(application);
        }
        return INSTANCE_PROCESSOR_APPLICATION;
    }

    private static volatile ImageProcessor INSTANCE_PROCESSOR_IMAGE;

    private static ImageProcessor getImageProcessor(final Application application) {
        if (null == INSTANCE_PROCESSOR_IMAGE) synchronized (ImageProcessor.class) {
            if (null == INSTANCE_PROCESSOR_IMAGE)
                INSTANCE_PROCESSOR_IMAGE = new ImageProcessor(application);
        }
        return INSTANCE_PROCESSOR_IMAGE;
    }

    private static volatile FileProcessor INSTANCE_PROCESSOR_ALLFILE;

    private static FileProcessor getAllFileProcessor(final Application application) {
        if (null == INSTANCE_PROCESSOR_ALLFILE) synchronized (FileProcessor.class) {
            if (null == INSTANCE_PROCESSOR_ALLFILE)
                INSTANCE_PROCESSOR_ALLFILE = new FileProcessor(application);
        }
        return INSTANCE_PROCESSOR_ALLFILE;
    }
}