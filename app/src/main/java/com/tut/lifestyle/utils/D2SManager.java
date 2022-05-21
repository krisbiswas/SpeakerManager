package com.tut.lifestyle.utils;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;

import java.util.List;
import java.util.Set;

public class D2SManager {
    private static D2SManager INSTANCE;
    private PluginDevice pluginDevice;

    private D2SManager(){}
    public static D2SManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new D2SManager();
        }
        return INSTANCE;
    }

    Set<RemoteRepresentationListener> remoteListeners;

    public void setPluginDevice(PluginDevice pluginDevice) {
        this.pluginDevice = pluginDevice;
    }

    public PluginDevice getPluginDevice() {
        return pluginDevice;
    }

    public void register(RemoteRepresentationListener receiver){
        // Register listeners for receiving the responses
        remoteListeners.add(receiver);
    }

    public void getRemoteRepresentation(String uri){

    }

    public void sendRemoteRepresentation(String attr, String value){

    }
    public void sendRemoteRepresentation(String attr, List<String> values){

    }
}
