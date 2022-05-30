package com.tut.lifestyle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
                .add(R.id.functions_container,
                        OfflineFragment.class,
                        null,
                        OfflineFragment.TAG)
                .commitNow();
    }

    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {

    }
}