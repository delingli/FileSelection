package com.stkj.freeshare.framework.processors.resources;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.stkj.freeshare.framework.entities.resources.FileImpl;
import com.stkj.freeshare.framework.utils.FileSizeUtil;
import com.stkj.freeshare.framework.utils.FileUtils;
import com.stkj.yunos.onekey.data.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor implements IResourceProcessor<FileImpl>{
    private String currentPath;
     private Application application;

    public String getCurrentPath() {
        return currentPath;
    }

    public FileProcessor(Application application) {
        this.application = application;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    @Override
    public boolean hasData() {
        return false;
    }

    @NonNull
    @Override
    public SparseArray<FileImpl> fetchResource() throws InterruptedException {
        return null;
    }

    public SparseArray<FileImpl> searchFiles(String path){
         setCurrentPath(path);
        //查找文件
        List<File> fileListByDirPath = FileUtils.getFileListByDirPath(path);
        SparseArray<FileImpl> result = new SparseArray<>();
        if(fileListByDirPath==null||fileListByDirPath.size()==0)
            return  result;
        for (File file : fileListByDirPath) {
            if (Thread.interrupted())
                try {
                    throw new InterruptedException("Thread of convert list to sparse was interrupted!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            FileImpl fileimpl=new FileImpl(false);
            if(file.isDirectory()){
                fileimpl.setDirectory(true);
            }else{
                //文件特殊处理文件图标
                fileimpl.setDirectory(false);
                fileimpl.setModifyTime(FileUtils.getModifiedTime(file));
                fileimpl.setSize(FileSizeUtil.getAutoFileOrFilesSize(file.getAbsolutePath()));
                fileimpl.setFileType(FileUtils.getFileType(file.getAbsolutePath()));
            }
            fileimpl.setFileName(file.getName());
            fileimpl.setFilePath(file.getAbsolutePath());
            result.put(file.hashCode(),fileimpl);
        }
        return result;

    }

 /*   @NonNull
    @Override
    public SparseArray<FileImpl> fetchResource() throws InterruptedException {
        List<FileImpl> tmp = new ArrayList<>();
        //查找文件
        List<File> fileListByDirPath = FileUtils.getFileListByDirPath(rootPath);
        SparseArray<FileImpl> result = new SparseArray<>();
        if(fileListByDirPath==null||fileListByDirPath.size()==0)
            return  result;
        for (File file : fileListByDirPath) {
            if (Thread.interrupted())
                throw new InterruptedException("Thread of convert list to sparse was interrupted!");
            if(!FileUtils.isFile(file))
                continue;
            FileImpl fileimpl=new FileImpl(file,false);
            if(file.isDirectory()){
                fileimpl.setDirectory(true);
            }else{
                fileimpl.setDirectory(false);
            }
            fileimpl.setFileName(file.getName());
            fileimpl.setFilePath(file.getAbsolutePath());
            fileimpl.setSize("0");
            result.put(file.hashCode(),fileimpl);
        }
        return result;
    }*/


}
