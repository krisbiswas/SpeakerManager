package com.tut.lifestyle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ui.ai.DeviceInfoFragment;
import com.tut.lifestyle.ui.common.OfflineFragment;
import com.tut.lifestyle.utils.AppUtils;
import com.tut.lifestyle.utils.listeners.ConnectionListener;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

public class DeviceInfoActivity extends AppCompatActivity implements ConnectionListener, DeviceUpdateListener {

    public static final String TAG = "DeviceInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        setToolBar();
        launchFragment(DeviceInfoFragment.TAG);
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
            case DeviceInfoFragment.TAG:
                transaction.add(R.id.device_info_container,DeviceInfoFragment.class, null).commit();
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
                .add(R.id.device_info_container,
                        OfflineFragment.class,
                        null,
                        OfflineFragment.TAG)
                .commitNow();
    }

    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {

    }
}