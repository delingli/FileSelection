package com.stkj.freeshare.framework.entities.resources;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;

import java.io.File;

@SuppressLint("ParcelCreator")
public class FileImpl extends FSResource {
    //文件名
    private String fileName;
    //文件路徑
    private String filePath;
    //是否选中
    private boolean isSelect;
    //是否是文件夾类型 默认
    private boolean isDirectory;
    private String modifyTime;
    /**
     * 文件图标
     */
    private int fileType;

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    @Override
    public int hashCode() {
        return this.getFilePath().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof FileImpl){
            FileImpl it= (FileImpl) obj;
            return it.getFilePath().equals(this.getFilePath());

        }
        return super.equals(obj);
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    private boolean isNull=false;

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    private String size;
    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }



    public FileImpl(boolean isSelect) {
        this.isSelect = isSelect;
    }


    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
//    public void setDir(String dir) {
//        this.dir = dir;
//        int lastIndex = dir.lastIndexOf("/");
//        this.name = dir.substring(lastIndex + 1);
//    }




    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
