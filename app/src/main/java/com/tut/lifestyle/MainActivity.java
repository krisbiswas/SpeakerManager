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
import androidx.fragment.app.FragmentTransaction;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ui.ai.DeviceInfoFragment;
import com.tut.lifestyle.ui.ai.setting.AlexaSettingsFragment;
import com.tut.lifestyle.ui.ai.setting.DeviceSettingFragment;
import com.tut.lifestyle.ui.ai.setting.SpotifySettingsFragment;
import com.tut.lifestyle.ui.common.DashboardFragment;
import com.tut.lifestyle.ui.common.OfflineFragment;
import com.tut.lifestyle.utils.AppUtils;
import com.tut.lifestyle.utils.D2SManager;
import com.tut.lifestyle.utils.listeners.ConnectionListener;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

public class MainActivity extends AppCompatActivity implements ConnectionListener, DeviceUpdateListener {

    public static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        registerReceiver(AppUtils.getInstance().getWifiConnectionReceiver(), intentFilter);

        D2SManager.getInstance().getPluginDevice().setCloudID(getIntent().getIntExtra("CloudID",-1));
        D2SManager.getInstance().getPluginDevice().setDeviceID(getIntent().getIntExtra("DeviceID",-1));
        D2SManager.getInstance().getPluginDevice().setIsAISoundbar(getIntent().getStringExtra("VID").contains("002S"));

        setToolBar(D2SManager.getInstance().getPluginDevice().getDeviceName(), D2SManager.getInstance().getPluginDevice().getDeviceLoc());
        launchFragment(DashboardFragment.TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    private void setToolBar(String devName, String devLoc) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE|ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setTitle(devName);
        actionBar.setSubtitle(devLoc);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_36);
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

    private void launchFragment(String fragmentName) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(fragmentName){
            case DashboardFragment.TAG:
                transaction.add(R.id.dashboard_main_fragment_container, DashboardFragment.class, null,DashboardFragment.TAG).commit();
                break;

            case OfflineFragment.TAG:
                transaction.add(R.id.dashboard_main_fragment_container, OfflineFragment.class, null, OfflineFragment.TAG).commit();
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
        System.out.println(TAG+" Device Online");
        Fragment f = getSupportFragmentManager().findFragmentByTag(OfflineFragment.TAG);
        if(f != null){
            getSupportFragmentManager().beginTransaction()
                    .remove(f)
                    .commitNow();
        }
    }

    @Override
    public void onDeviceDisconnected(PluginDevice pluginDevice) {
        // Called when phone or soundbar goes to offline state
//        launchFragment(OfflineFragment.TAG);
        System.out.println(TAG+" Device Offline");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.dashboard_main_fragment_container,
                        OfflineFragment.class,
                        null,
                        OfflineFragment.TAG)
                .disallowAddToBackStack()
                .commitNow();
    }

    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {

    }
}