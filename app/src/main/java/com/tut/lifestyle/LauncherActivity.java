package com.tut.lifestyle;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {
    private boolean isWifiConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        findViewById(R.id.btn_plugin_launch).setOnClickListener((view)->{
            Intent launchMainIntent = new Intent(this, MainActivity.class);
            addLaunchDeatils(launchMainIntent);
            startActivity(launchMainIntent);
        });
    }

    private void addLaunchDeatils(Intent intent) {
        intent.putExtra("device name", ((EditText)findViewById(R.id.et_device_name)).getText());
        intent.putExtra("device loc", ((EditText)findViewById(R.id.et_device_name)).getText());
        intent.putExtra("cloud id", ((EditText)findViewById(R.id.et_device_name)).getText());
        intent.putExtra("signal", ((EditText)findViewById(R.id.et_device_name)).getText());
        intent.putExtra("fw ver", ((EditText)findViewById(R.id.et_device_name)).getText());
    }
}