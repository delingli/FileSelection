package com.stkj.freeshare.framework.entities.resources;

import com.stkj.freeshare.framework.entities.Checkable;

import java.util.List;

public final class FSAlbum implements Checkable {
    public List<FSImage> images;
    public String path;
    public int count;
    public long size;
    public boolean isChecked;

    public FSAlbum(String path) {
        this.path = path;
    }

    public static final class FSImage implements Checkable {
        public String path;
        public long size;
        public boolean isChecked;

        public FSImage(String path, long size) {
            this.path = path;
            this.size = size;
        }
    }
}