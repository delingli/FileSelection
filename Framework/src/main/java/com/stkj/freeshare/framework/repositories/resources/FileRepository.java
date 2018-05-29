package com.stkj.freeshare.framework.repositories.resources;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;


import com.stkj.freeshare.framework.entities.resources.FileImpl;
import com.stkj.freeshare.framework.processors.resources.FileProcessor;
import com.stkj.freeshare.framework.processors.resources.ResourceProcessor;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileRepository extends AbstractResourceRepository<List<FileImpl>, FileImpl> {
private String path= Environment.getExternalStorageDirectory().getAbsolutePath();//默认值
    private MutableLiveData<List<FileImpl>> liveData;


    public FileRepository(Application application) {
        super(application, ResourceProcessor.PROCESS_ALLFILE);
        liveData = new MutableLiveData<>();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void FeatchFileByPath(String path){
        this.path=path;
        searchFile();
    }
    public  MutableLiveData<List<FileImpl>> searchFile(){

        EXECUTOR.execute(new Runnable() {
            @Override
            public final void run() {
                try {
                    if(PROCESSOR instanceof FileProcessor){
                        FileProcessor  processor= (FileProcessor) PROCESSOR;
                        List<FileImpl> covert = covert(processor.searchFiles(path));
                        Log.d("FileRepository",covert.toString()+"::"+covert.size());
                        if(null==covert||covert.size()==0){
                            FileImpl file=      new FileImpl(false);
                            file.setNull(true);
                            covert.add(file);
                            liveData.postValue(covert);
                        }else{
                            liveData.postValue(covert(processor.searchFiles(path)));
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return liveData;

    }



    @Override
    List<FileImpl> covert(SparseArray<FileImpl> source) throws InterruptedException {
        List<FileImpl> result = new ArrayList<>();
        for (int i = 0; i < source.size(); i++) {
            if (Thread.interrupted())
                throw new InterruptedException("Thread of convert sparse to list was interrupted!");
            FileImpl fileimpl = source.valueAt(i);
            if (null == fileimpl) continue;
            result.add(fileimpl);
        }
        return result;
    }
}
