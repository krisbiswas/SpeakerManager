package com.tut.lifestyle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ui.ai.DeviceInfoFragment;
import com.tut.lifestyle.utils.AppUtils;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

public class DeviceInfoActivity extends BaseActivity implements DeviceUpdateListener {

    public static final String TAG = "DeviceInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        AppUtils.getInstance().addConnectionListener(this);
        AppUtils.getInstance().addDeviceListener(this);
        String fragment = getIntent().getStringExtra("Fragment");
        setToolBar(fragment, null);
        launchFragment(DeviceInfoFragment.TAG);
    }

    @Override
    protected void onDestroy() {
        AppUtils.getInstance().removeConnctionListener(this);
        AppUtils.getInstance().removeDeviceListener(this);
        super.onDestroy();
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
                transaction.add(R.id.fragment_container,DeviceInfoFragment.class, null).commit();
                break;
        }
    }

    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {

    }
}