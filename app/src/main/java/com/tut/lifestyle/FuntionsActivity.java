package com.tut.lifestyle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ui.common.OfflineFragment;
import com.tut.lifestyle.utils.AppUtils;
import com.tut.lifestyle.utils.listeners.ConnectionListener;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

public class FuntionsActivity extends AppCompatActivity implements ConnectionListener, DeviceUpdateListener {

    public static final String TAG = "FuntionsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funtions);
        setToolBar();
        launchFragment(getIntent().getStringExtra("Fragment"));
    }

    private void launchFragment(String fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.function_container, AppUtils.getInstance().getFragmentClassFromTAG(fragment), null).commit();
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE|ActionBar.DISPLAY_HOME_AS_UP);
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

    @Override
    public void onDeviceConnected() {
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
        Fragment offlineFragment = getSupportFragmentManager().findFragmentByTag(OfflineFragment.TAG);
        System.out.println(TAG+" onDeviceDisconnected offlineFragment" + offlineFragment);
        if(offlineFragment == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.setting_container, OfflineFragment.class, null, OfflineFragment.TAG)
                    .commitNow();
        }
    }

    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {

    }
}