package com.tut.lifestyle.utils;

import com.tut.lifestyle.utils.listeners.ConnectionListener;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

import java.util.ArrayList;
import java.util.List;

public class AppUtils {
    private static AppUtils INSTANCE;
    public static AppUtils getInstance(){
        if(INSTANCE == null){
            INSTANCE = new AppUtils();
        }
        return INSTANCE;
    }

    private AppUtils(){
        connectionListeners = new ArrayList<>();
        deviceListeners = new ArrayList<>();
    }

    private List<ConnectionListener> connectionListeners;
    private List<DeviceUpdateListener> deviceListeners;

}
