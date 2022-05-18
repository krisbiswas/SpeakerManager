package com.tut.lifestyle;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.utils.listeners.ConnectionListener;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

public class MainActivity extends AppCompatActivity implements ConnectionListener, DeviceUpdateListener {

    // Register receivers and listeners in Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onDeviceConnected(PluginDevice pluginDevice) {
        // Called when phone or soundbar comes back online from offline state
    }

    @Override
    public void onDeviceDisconnected(PluginDevice pluginDevice) {
        // Called when phone or soundbar goes to offline state
    }

    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {

    }
}