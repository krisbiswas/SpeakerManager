package com.tut.lifestyle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ui.ai.AlexaSettingsFragment;
import com.tut.lifestyle.ui.ai.DeviceSettingFragment;
import com.tut.lifestyle.ui.ai.setting.NetworkStatusFragment;
import com.tut.lifestyle.ui.ai.setting.SpotifySettingsFragment;
import com.tut.lifestyle.ui.common.OfflineFragment;
import com.tut.lifestyle.utils.AppUtils;
import com.tut.lifestyle.utils.listeners.ConnectionListener;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

public class SettingsActivity extends AppCompatActivity implements ConnectionListener, DeviceUpdateListener {
    public static final String TAG = "SettingsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setToolBar();
        launchFragment(getIntent().getStringExtra("Fragment"));
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
        super.onDestroy();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_36);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println("Toolbar Home button "+item);
        switch(item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchFragment(String fragmentName) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(fragmentName){
            case DeviceSettingFragment.TAG:
                transaction.add(R.id.setting_container,DeviceSettingFragment.class, null).commit();
                break;
            case NetworkStatusFragment.TAG:
                transaction.add(R.id.setting_container,NetworkStatusFragment.class, null).commit();
                break;
            case SpotifySettingsFragment.TAG:
                transaction.add(R.id.setting_container,SpotifySettingsFragment.class, null).commit();
                break;
            case AlexaSettingsFragment.TAG:
                transaction.add(R.id.setting_container,AlexaSettingsFragment.class, null).commit();
                break;
        }
    }

    @Override
    public void onDeviceConnected(PluginDevice pluginDevice) {
        // Called when phone or soundbar comes back online from offline state
        Fragment f= getSupportFragmentManager().findFragmentByTag(OfflineFragment.TAG);
        if(f != null){
            getSupportFragmentManager().beginTransaction()
                    .remove(f)
                    .commitNow();
        }
    }

    @Override
    public void onDeviceDisconnected(PluginDevice pluginDevice) {
        // Called when phone or soundbar goes to offline state
        getSupportFragmentManager().beginTransaction()
                .add(R.id.setting_container,
                        OfflineFragment.class,
                        null,
                        OfflineFragment.TAG)
                .commitNow();
    }

    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {

    }
}