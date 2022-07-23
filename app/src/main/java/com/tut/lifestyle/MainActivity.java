package com.tut.lifestyle;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

public class MainActivity extends BaseActivity implements DeviceUpdateListener {

    public static final String TAG = "MainActivity";

    //region System Callbacks
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        registerReceiver(AppUtils.getInstance().getWifiConnectionReceiver(), intentFilter);

        System.out.println(D2SManager.getInstance().getPluginDevice());

        D2SManager.getInstance().getPluginDevice().setCloudID(getIntent().getIntExtra("CloudID",-1));
        D2SManager.getInstance().getPluginDevice().setDeviceID(getIntent().getIntExtra("DeviceID",-1));
        D2SManager.getInstance().getPluginDevice().setIsAISoundbar(getIntent().getStringExtra("VID").contains("002S"));

        AppUtils.getInstance().addConnectionListener(this);
        AppUtils.getInstance().addDeviceListener(this);

        setToolBar(D2SManager.getInstance().getPluginDevice().getDeviceName(), D2SManager.getInstance().getPluginDevice().getDeviceLoc());
        launchFragment(DashboardFragment.TAG);
    }

    @Override
    protected void onDestroy() {
        System.out.println("Main Destroyed");
        AppUtils.getInstance().removeConnctionListener(this);
        AppUtils.getInstance().removeDeviceListener(this);
        unregisterReceiver(AppUtils.getInstance().getWifiConnectionReceiver());
        super.onDestroy();
    }
    //endregion

    // region Action Bar
    private void setToolBar(String devName, String devLoc) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE|ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setTitle(devName);
        actionBar.setSubtitle(devLoc);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_36);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
    //endregion

    //region launchers
    private void launchActivity(String activityTag, String fragmentTag) {
        Intent intent;
        switch (activityTag){
            case SettingsActivity.TAG:
                intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("Fragment", fragmentTag);
                startActivity(intent);
                break;
            case FunctionsActivity.TAG:
                intent = new Intent(this, FunctionsActivity.class);
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
                transaction.add(R.id.fragment_container, DashboardFragment.class, null,DashboardFragment.TAG).commit();
                break;

            case OfflineFragment.TAG:
                transaction.add(R.id.fragment_container, OfflineFragment.class, null, OfflineFragment.TAG).commit();
                break;
        }
    }
    //endregion

    //region Registered Callbacks
    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {

    }

    //endregion
}