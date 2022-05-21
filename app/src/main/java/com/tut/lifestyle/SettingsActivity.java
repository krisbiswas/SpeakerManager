package com.tut.lifestyle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.tut.lifestyle.ui.ai.AlexaSettingsFragment;
import com.tut.lifestyle.ui.ai.DeviceSettingFragment;
import com.tut.lifestyle.ui.ai.NetworkStatusFragment;
import com.tut.lifestyle.ui.ai.SpotifySettingsFragment;

public class SettingsActivity extends AppCompatActivity {
    public static final String TAG = "SettingsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setToolBar();
        launchFragment(getIntent().getStringExtra("Fragment"));
    }

    private void setToolBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_left_48);
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
}