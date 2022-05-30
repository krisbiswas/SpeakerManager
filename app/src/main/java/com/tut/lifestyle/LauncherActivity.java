package com.tut.lifestyle;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.utils.D2SManager;

public class LauncherActivity extends AppCompatActivity {
    private boolean isWifiConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        findViewById(R.id.btn_plugin_launch).setOnClickListener((view)->{
            Intent launchMainIntent = new Intent(this, MainActivity.class);
            //            createAIPluginDevice(launchMainIntent);
            createPluginDevice(launchMainIntent);
            addLaunchDetails(launchMainIntent);
            startActivity(launchMainIntent);
        });
    }

    private void createAIPluginDevice(Intent intent) {
        intent.putExtra("VID","f65002SdDDH53");
        PluginDevice device = new PluginDevice();
        device.setCloudConnected(true);
        device.setDeviceName("Krisb Q900");
        device.setDeviceLoc("Living Room");
        device.setSoundFrom(new Pair<>("TV","HDMI"));
        device.setSoundMode("Standard");
        device.setEqualizerState("Tone");
//        device.setWooferSetting(4);
        device.setSpaceFit(true);
        D2SManager.getInstance().setPluginDevice(device);
    }

    private void createPluginDevice(Intent intent) {
        intent.putExtra("VID","6350faswds3cds");
        PluginDevice device = new PluginDevice();
        device.setCloudConnected(true);
        device.setDeviceName("Kris Old Q900");
        device.setDeviceLoc("Bed Room");
        device.setSoundFrom(new Pair<>("Galaxy","WiFi"));
        device.setEqualizerState("Tone");
        device.setWooferSetting(4);
        D2SManager.getInstance().setPluginDevice(device);
    }

    private void addLaunchDetails(Intent intent) {
        intent.putExtra("DeviceID","6sdad6654dasd435ads3cds");
        intent.putExtra("CloudID","45681354");
        intent.putExtra("device name", ((EditText)findViewById(R.id.et_device_name)).getText());
        intent.putExtra("device loc", ((EditText)findViewById(R.id.et_device_name)).getText());
        intent.putExtra("cloud id", ((EditText)findViewById(R.id.et_device_name)).getText());
        intent.putExtra("signal", ((EditText)findViewById(R.id.et_device_name)).getText());
        intent.putExtra("fw ver", ((EditText)findViewById(R.id.et_device_name)).getText());
    }
}