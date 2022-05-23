package com.tut.lifestyle.utils;

import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class D2SManager {
    private static D2SManager INSTANCE;
    private PluginDevice pluginDevice;

    private D2SManager(){
        remoteListeners = new HashSet<>();
    }
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

    public void unregister(RemoteRepresentationListener receiver){
        // Register listeners for receiving the responses
        remoteListeners.remove(receiver);
    }

    public void getRemoteRepresentation(String attr){
        System.out.println("GET Resource attr: "+attr);
        for(RemoteRepresentationListener remoteListener : remoteListeners){
            String responseVal = pluginDevice.getData(attr);
            System.out.println("Response Val : "+responseVal);
            remoteListener.onRepresentationReceived(new OCFResponse(OCFConstants.RESULT_OK, attr, pluginDevice.getData(attr)));
        }
    }

    public void sendRemoteRepresentation(String attr, String value){
        System.out.println("POST Attr: "+attr+"\nValue: "+value);
        pluginDevice.setData(attr, value);
        for(RemoteRepresentationListener remoteListener : remoteListeners){
            String responseVal = pluginDevice.getData(attr);
            System.out.println("Response Val : "+responseVal);
            remoteListener.onRepresentationReceived(new OCFResponse(OCFConstants.RESULT_OK, attr, responseVal));
        }
    }
    public void sendRemoteRepresentation(String attr, List<String> values){
        System.out.println("POST Attr: "+attr+"\nValue: "+values);
    }
}
