package com.stkj.freeshare.framework.entities.resources;

import android.os.Parcelable;

/**
 * @author GAM
 * @since 2018-05-05
 */
public abstract class FSResource implements Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }
}