package com.tut.lifestyle;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ui.common.OfflineFragment;
import com.tut.lifestyle.utils.listeners.ConnectionListener;

public class BaseActivity extends AppCompatActivity implements ConnectionListener{

    private String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDeviceConnected(PluginDevice pluginDevice) {
        // Called when phone or soundbar comes back online from offline state
        Fragment f= getSupportFragmentManager().findFragmentByTag(OfflineFragment.TAG);
        if(f != null){
            getSupportFragmentManager().beginTransaction()
                    .remove(f)
                    .commitNowAllowingStateLoss();
        }
    }

    @Override
    public void onDeviceDisconnected(PluginDevice pluginDevice) {
        // Called when phone or soundbar goes to offline state
//        launchFragment(OfflineFragment.TAG);
        System.out.println(TAG+" Device Offline");
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container,
                        OfflineFragment.class,
                        null,
                        OfflineFragment.TAG)
                .disallowAddToBackStack()
                .commitNowAllowingStateLoss();
    }
}
