package com.tut.lifestyle;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ui.ai.DeviceInfoFragment;
import com.tut.lifestyle.ui.ai.setting.AlexaSettingsFragment;
import com.tut.lifestyle.ui.ai.setting.DeviceSettingFragment;
import com.tut.lifestyle.ui.ai.setting.SpotifySettingsFragment;
import com.tut.lifestyle.ui.common.OfflineFragment;
import com.tut.lifestyle.utils.AppUtils;
import com.tut.lifestyle.utils.D2SManager;
import com.tut.lifestyle.utils.listeners.ConnectionListener;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

public class MainActivity extends AppCompatActivity implements ConnectionListener, DeviceUpdateListener {
    public static final String TAG = "MainActivity";
    // Register receivers and listeners in Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolBar();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        registerReceiver(AppUtils.getInstance().getWifiConnectionReceiver(), intentFilter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get device state and required attributes
        AppUtils.getInstance().addConnectionListener(this);
        AppUtils.getInstance().addDeviceListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppUtils.getInstance().removeConnctionListener(this);
        AppUtils.getInstance().removeDeviceListener(this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(AppUtils.getInstance().getWifiConnectionReceiver());
        super.onDestroy();
    }

    @Override
    public void onDeviceConnected() {
        // Called when phone or soundbar comes back online from offline state
        Fragment offlineFragment = getSupportFragmentManager().findFragmentByTag(OfflineFragment.TAG);
        System.out.println(TAG+" onDeviceConnected offlineFragment" + offlineFragment);
        if(offlineFragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(offlineFragment)
                    .commitNow();
        }
    }

    @Override
    public void onDeviceDisconnected() {
        // Called when phone or soundbar goes to offline state
        Fragment offlineFragment = getSupportFragmentManager().findFragmentByTag(OfflineFragment.TAG);
        System.out.println(TAG+" onDeviceDisconnected offlineFragment" + offlineFragment);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.dashboard_fragment_container, OfflineFragment.class, null, OfflineFragment.TAG)
                .commitNow();
    }

    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {
        setPluginDevice(pluginDevice);
        System.out.println(TAG + "Plugin Device Update : \n"+pluginDevice);
    }

    private void setPluginDevice(PluginDevice pluginDevice) {
        D2SManager.getInstance().setPluginDevice(pluginDevice);
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE|ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_36);
    }

    private void launchActivity(String activityTag, String fragmentTag) {
        Intent intent;
        intent = new Intent(this, AppUtils.getInstance().getActivityClassFromTAG(activityTag));
        intent.putExtra("Fragment", fragmentTag);
        startActivity(intent);
    }

}