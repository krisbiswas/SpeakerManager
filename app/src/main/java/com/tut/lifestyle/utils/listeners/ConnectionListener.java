package com.tut.lifestyle.utils.listeners;

public interface ConnectionListener {
    // Connection state change can be from either mobile or speaker end (or both)
    void onDeviceConnected();
    void onDeviceDisconnected();
}
