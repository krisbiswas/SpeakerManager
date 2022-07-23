package com.tut.lifestyle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentTransaction;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ui.ai.setting.AlexaSettingsFragment;
import com.tut.lifestyle.ui.ai.setting.DeviceSettingFragment;
import com.tut.lifestyle.ui.ai.setting.NetworkStatusFragment;
import com.tut.lifestyle.ui.ai.setting.SpotifySettingsFragment;
import com.tut.lifestyle.utils.AppUtils;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

public class SettingsActivity extends BaseActivity implements DeviceUpdateListener {
    public static final String TAG = "SettingsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        AppUtils.getInstance().addConnectionListener(this);
        AppUtils.getInstance().addDeviceListener(this);

        String fragment = getIntent().getStringExtra("Fragment");
        setToolBar(fragment, null);
        launchFragment(fragment);
    }

    @Override
    protected void onDestroy() {
        AppUtils.getInstance().removeConnctionListener(this);
        AppUtils.getInstance().removeDeviceListener(this);
        super.onDestroy();
    }

    private void setToolBar(String title, @Nullable String subTitle) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setTitle(title);
        if(subTitle!=null){
            actionBar.setSubtitle(subTitle);
        }
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

    public void launchFragment(String fragmentName) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(fragmentName){
            case DeviceSettingFragment.TAG:
                transaction.add(R.id.fragment_container,DeviceSettingFragment.class, null).commit();
                break;
            case NetworkStatusFragment.TAG:
                transaction.add(R.id.fragment_container,NetworkStatusFragment.class, null).commit();
                break;
            case SpotifySettingsFragment.TAG:
                transaction.add(R.id.fragment_container,SpotifySettingsFragment.class, null).commit();
                break;
            case AlexaSettingsFragment.TAG:
                transaction.add(R.id.fragment_container,AlexaSettingsFragment.class, null).commit();
                break;
        }
    }

    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {

    }
}