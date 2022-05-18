package com.tut.lifestyle.utils.listeners;

import com.tut.lifestyle.data.PluginDevice;

public interface ConnectionListener {
    // Connection state change can be from either mobile or speaker end (or both)
    void onDeviceConnected(PluginDevice pluginDevice);
    void onDeviceDisconnected(PluginDevice pluginDevice);
}
