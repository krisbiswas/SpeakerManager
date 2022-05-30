package com.tut.lifestyle.libs;

import com.tut.lifestyle.data.PluginDevice;

public interface SCDeviceListener {
    void onServiceConnected(PluginDevice pluginDevice);
    void onServiceDisconnected(PluginDevice pluginDevice);
}
