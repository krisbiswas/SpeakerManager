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
            addLaunchDetails(launchMainIntent);
            startActivity(launchMainIntent);
        });
        setDummyPluginDevice();
    }

    private void setDummyPluginDevice() {
        PluginDevice device = new PluginDevice();
        device.setDeviceName("Krish Q950");
        device.setDeviceLoc("Living Room");
        device.setNwStatus(70);
        device.setSoundFrom(new Pair<>("Galaxy M21", "WiFi"));
        device.setSoundMode("Music");
        device.setEqualizerState("Metal");
        device.setWooferSetting(10);
//        device.setAutoEq(false);
        device.setVoiceAssistant(false);
        device.setSpaceFit(true);
        device.setChannelSetting(new Pair<>(60,80));
        setPluginDevice(device);
    }

    private void setPluginDevice(PluginDevice pluginDevice) {
        D2SManager.getInstance().setPluginDevice(pluginDevice);
    }

    private void addLaunchDetails(Intent intent) {
        EditText et = findViewById(R.id.et_device_name);
        intent.putExtra("device name", (et != null)?et.getText() : null);
        et = findViewById(R.id.et_device_location);
        intent.putExtra("device loc", et.getText());
        et = findViewById(R.id.et_cloud_id);
        intent.putExtra("cloud id", et.getText());
        et = findViewById(R.id.et_nw_strength);
        intent.putExtra("signal", et.getText());
        et = findViewById(R.id.et_fw_version);
        intent.putExtra("fw ver", et.getText());
    }
}