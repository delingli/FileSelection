package com.stkj.freeshare.models.resources.files;

import java.io.Serializable;

public class ResourceFileNavEntiry implements Serializable {
    private String fileFloadName;
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public int hashCode() {
        return this.filePath.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ResourceFileNavEntiry){
            ResourceFileNavEntiry o= (ResourceFileNavEntiry) obj;
       return  this.filePath.equals(o.filePath);
        }
return   super.equals(obj);
    }

    public ResourceFileNavEntiry(String fileFloadName, String filePath) {
        this.fileFloadName = fileFloadName;
        this.filePath = filePath;
    }

    public String getFileFloadName() {
        return fileFloadName;
    }

    public void setFileFloadName(String fileFloadName) {
        this.fileFloadName = fileFloadName;
    }
}
