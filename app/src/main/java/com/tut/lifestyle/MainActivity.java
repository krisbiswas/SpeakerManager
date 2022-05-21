package com.tut.lifestyle;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ui.ai.AlexaSettingsFragment;
import com.tut.lifestyle.ui.ai.DeviceInfoFragment;
import com.tut.lifestyle.ui.ai.DeviceSettingFragment;
import com.tut.lifestyle.ui.ai.SpotifySettingsFragment;
import com.tut.lifestyle.utils.listeners.ConnectionListener;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

public class MainActivity extends AppCompatActivity implements ConnectionListener, DeviceUpdateListener {

    // Register receivers and listeners in Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolBar("Krishnandu Q990","Drawing Room");
    }

    private void setToolBar(String devName, String devLoc) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE|ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setTitle(devName);
        actionBar.setSubtitle(devLoc);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_48);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println("Toolbar Home button "+item);
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.device_settings:
                launchActivity(SettingsActivity.TAG, DeviceSettingFragment.TAG);break;
            case R.id.spotify_settings:
                launchActivity(SettingsActivity.TAG, SpotifySettingsFragment.TAG);break;
            case R.id.alexa_settings:
                launchActivity(SettingsActivity.TAG, AlexaSettingsFragment.TAG);break;
            case R.id.device_info:
                launchActivity(DeviceInfoActivity.TAG, DeviceInfoFragment.TAG);break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchActivity(String activityTag, String fragmentTag) {
        Intent intent;
        switch (activityTag){
            case SettingsActivity.TAG:
                intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("Fragment", fragmentTag);
                startActivity(intent);
                break;
            case FuntionsActivity.TAG:
                intent = new Intent(this, FuntionsActivity.class);
                intent.putExtra("Fragment", fragmentTag);
                startActivity(intent);
                break;
            case DeviceInfoActivity.TAG:
                intent = new Intent(this, DeviceInfoActivity.class);
                intent.putExtra("Fragment", fragmentTag);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
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