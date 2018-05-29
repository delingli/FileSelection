package com.stkj.freeshare.framework.entities.resources;

import android.os.Parcel;
import android.support.annotation.Nullable;

import com.stkj.freeshare.framework.entities.Checkable;

/**
 * 显示层应用数据实体类
 *
 * @author GAM
 * @since 2018-05-05
 */
public final class FSApplication extends FSResource implements Checkable {
    @Nullable
    public final String pkg;// 包名
    @Nullable
    public final String path;// APK文件路径

    public boolean isChecked;

    public FSApplication(@Nullable String pkg, @Nullable String path) {
        this.pkg = pkg;
        this.path = path;
    }

    private FSApplication(Parcel in) {
        pkg = in.readString();
        path = in.readString();
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pkg);
        dest.writeString(path);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    public static final Creator<FSApplication> CREATOR = new Creator<FSApplication>() {
        @Override
        public FSApplication createFromParcel(Parcel in) {
            return new FSApplication(in);
        }

        @Override
        public FSApplication[] newArray(int size) {
            return new FSApplication[size];
        }
    };
}