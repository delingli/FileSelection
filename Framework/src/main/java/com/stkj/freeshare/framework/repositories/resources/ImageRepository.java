package com.stkj.freeshare.framework.repositories.resources;

import android.app.Application;
import android.text.TextUtils;
import android.util.SparseArray;

import com.stkj.freeshare.framework.entities.resources.FSAlbum;
import com.stkj.freeshare.framework.processors.resources.ResourceProcessor;
import com.stkj.yunos.onekey.data.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GAM
 * @since 2018-05-15
 */
public final class ImageRepository extends AbstractResourceRepository<List<FSAlbum>, Image> {
    public ImageRepository(Application application) {
        super(application, ResourceProcessor.PROCESS_IMAGE);
    }

    @Override
    final List<FSAlbum> covert(final SparseArray<Image> source) throws InterruptedException {
        Map<String, FSAlbum> albums = new HashMap<>();
        for (int i = 0; i < source.size(); i++) {
            if (Thread.interrupted())
                throw new InterruptedException("Thread of convert sparse to list was interrupted!");
            Image image = source.valueAt(i);
            if (null == image || TextUtils.isEmpty(image.path)) continue;
            File file = new File(image.path);
            if (!file.exists()) continue;
            FSAlbum album = albums.get(file.getParent());
            if (null == album) {
                album = new FSAlbum(file.getParent());
                albums.put(file.getParent(), album);
            }
            List<FSAlbum.FSImage> images = album.images;
            if (null == images) {
                images = new ArrayList<>();
                album.images = images;
            }
            images.add(new FSAlbum.FSImage(image.path, file.length()));
        }
        List<FSAlbum> result = new ArrayList<>();
        for (FSAlbum album : albums.values()) {
            if (null == album.images) continue;
            for (FSAlbum.FSImage image : album.images) {
                album.size += image.size;
            }
            result.add(album);
        }
        return result;
    }
}