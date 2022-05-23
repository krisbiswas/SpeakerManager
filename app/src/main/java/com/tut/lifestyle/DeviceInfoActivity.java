package com.tut.lifestyle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.tut.lifestyle.ui.ai.DeviceInfoFragment;

public class DeviceInfoActivity extends AppCompatActivity {

    public static final String TAG = "DeviceInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        setToolBar();
        launchFragment(DeviceInfoFragment.TAG);
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
}