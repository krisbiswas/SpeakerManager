package com.tut.lifestyle.utils;

public class DeviceInfo {
    private static DeviceInfo INSTANCE;
    private DeviceInfo(){}
    public static DeviceInfo getInstance() {
        if(INSTANCE == null){
            INSTANCE = new DeviceInfo();
        }
        return INSTANCE;
    }

    public boolean isAISoundbar = false;
}
