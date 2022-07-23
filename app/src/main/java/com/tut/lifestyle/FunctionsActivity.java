package com.tut.lifestyle;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentTransaction;

import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ui.ai.funtions.AdvancedAudioFragment;
import com.tut.lifestyle.ui.ai.funtions.EqualizerFragment;
import com.tut.lifestyle.ui.ai.funtions.SmartHubFragment;
import com.tut.lifestyle.ui.ai.funtions.SoundModeFragment;
import com.tut.lifestyle.ui.ai.funtions.WooferFragment;
import com.tut.lifestyle.utils.AppUtils;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

public class FunctionsActivity extends BaseActivity implements DeviceUpdateListener {

    public static final String TAG = "FunctionsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funtions);
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

    private void launchFragment(String fragmentName) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch(fragmentName){
            case SoundModeFragment.TAG:
                transaction.add(R.id.fragment_container,SoundModeFragment.class, null).commit();
                break;
            case EqualizerFragment.TAG:
                transaction.add(R.id.fragment_container,EqualizerFragment.class, null).commit();
                break;
            case WooferFragment.TAG:
                transaction.add(R.id.fragment_container,WooferFragment.class, null).commit();
                break;
            case AdvancedAudioFragment.TAG:
                transaction.add(R.id.fragment_container,AdvancedAudioFragment.class, null).commit();
                break;
            case SmartHubFragment.TAG:
                transaction.add(R.id.fragment_container,SmartHubFragment.class, null).commit();
                break;
        }
    }

    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {

    }
}