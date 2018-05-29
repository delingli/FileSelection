package com.stkj.freeshare.models.resources.files;

import java.io.Serializable;

public class DevicesStorageItem implements Serializable {
    private String storageDeviceName;
    private String TatalStorageDeviceSize;
    private String AvailableStorageDeviceSize;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public DevicesStorageItem() {
    }


    public String getStorageDeviceName() {
        return storageDeviceName;
    }

    public void setStorageDeviceName(String storageDeviceName) {
        this.storageDeviceName = storageDeviceName;
    }

    public String getTatalStorageDeviceSize() {
        return TatalStorageDeviceSize;
    }

    public void setTatalStorageDeviceSize(String tatalStorageDeviceSize) {
        TatalStorageDeviceSize = tatalStorageDeviceSize;
    }

    public String getAvailableStorageDeviceSize() {
        return AvailableStorageDeviceSize;
    }

    public void setAvailableStorageDeviceSize(String availableStorageDeviceSize) {
        AvailableStorageDeviceSize = availableStorageDeviceSize;
    }
}
