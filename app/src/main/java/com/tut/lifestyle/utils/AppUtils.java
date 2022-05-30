package com.tut.lifestyle.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import androidx.fragment.app.Fragment;

import com.tut.lifestyle.DeviceInfoActivity;
import com.tut.lifestyle.FuntionsActivity;
import com.tut.lifestyle.SettingsActivity;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.data.PluginDevice;
import com.tut.lifestyle.ui.ai.funtions.AdvancedAudioFragment;
import com.tut.lifestyle.ui.ai.funtions.AutoEQFragment;
import com.tut.lifestyle.ui.ai.funtions.EqualizerFragment;
import com.tut.lifestyle.ui.ai.funtions.SoundFromFragment;
import com.tut.lifestyle.ui.ai.funtions.SoundModeFragment;
import com.tut.lifestyle.ui.ai.funtions.WooferFragment;
import com.tut.lifestyle.ui.ai.setting.AlexaSettingsFragment;
import com.tut.lifestyle.ui.ai.setting.DeviceSettingFragment;
import com.tut.lifestyle.ui.ai.setting.NetworkStatusFragment;
import com.tut.lifestyle.ui.ai.setting.SpotifySettingsFragment;
import com.tut.lifestyle.utils.listeners.ConnectionListener;
import com.tut.lifestyle.utils.listeners.DeviceUpdateListener;

import java.util.HashSet;
import java.util.Set;

public class AppUtils implements DeviceUpdateListener{
    public static final String TAG = "AppUtils";
    private static AppUtils INSTANCE;
    public static AppUtils getInstance(){
        if(INSTANCE == null){
            INSTANCE = new AppUtils();
        }
        return INSTANCE;
    }

    private AppUtils(){
        connectionListeners = new HashSet<>();
        deviceListeners = new HashSet<>();
    }

    private Set<ConnectionListener> connectionListeners;
    private Set<DeviceUpdateListener> deviceListeners;
    public boolean isOffline = true;

    public void addConnectionListener(ConnectionListener connectionListener) {
        this.connectionListeners.add(connectionListener);
    }

    public void addDeviceListener(DeviceUpdateListener deviceListener) {
        this.deviceListeners.add(deviceListener);
    }

    public void removeConnctionListener(ConnectionListener connectionListener) {
        this.connectionListeners.remove(connectionListener);
    }

    public void removeDeviceListener(DeviceUpdateListener deviceListener) {
        this.deviceListeners.remove(deviceListener);
    }

    @Override
    public void onDeviceUpdate(PluginDevice pluginDevice) {
        for(DeviceUpdateListener listener : deviceListeners){
            listener.onDeviceUpdate(pluginDevice);
        }
    }

    private BroadcastReceiver wifiConnectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION)) {
                if (intent.getBooleanExtra(WifiManager.EXTRA_SUPPLICANT_CONNECTED, false)) {
                    System.out.println(TAG + " Wifi Connection established");
                    isOffline = false;
                    for(ConnectionListener listener : connectionListeners){
                        listener.onDeviceConnected(D2SManager.getInstance().getPluginDevice());
                    }
                } else {
                    // wifi connection was lost
                    System.out.println(TAG + " Wifi Connection Lost");
                    isOffline = true;
                    for(ConnectionListener listener : connectionListeners){
                        listener.onDeviceDisconnected(D2SManager.getInstance().getPluginDevice());
                    }
                }
            }

        }
    };

    public BroadcastReceiver getWifiConnectionReceiver(){
        return wifiConnectionReceiver;
    }

    public String titleToAttr(String title){
        String attr = null;
        switch(title) {
            case "Sound Mode":
                attr = OCFAttributes.soundMode;
                break;
            case OCFAttributes.spk_vol:
                break;
            case "Equalizer":
                attr = OCFAttributes.eq;
                break;
            case "Woofer":
                attr = OCFAttributes.woofer;
                break;
            case "Auto Equalizer":
                attr = OCFAttributes.auto_eq;
                break;
            case "Space Fit":
                attr = OCFAttributes.spaceFit;
                break;
            case "Voice Assistant":
                attr = OCFAttributes.ava;
                break;
            case "Advanced Audio Setting":
                attr = OCFAttributes.adv_audio;
                break;
        }
        return attr;
    }

    public Class<? extends Fragment> getFragmentClassFromTAG(String fragmentTag){
        switch (fragmentTag){
            case SoundFromFragment.TAG:
                return SoundFromFragment.class;
            case SoundModeFragment.TAG:
                return SoundModeFragment.class;
            case EqualizerFragment.TAG:
                return EqualizerFragment.class;
            case WooferFragment.TAG:
                return WooferFragment.class;
            case AutoEQFragment.TAG:
                return AutoEQFragment.class;
            case AdvancedAudioFragment.TAG:
                return AdvancedAudioFragment.class;
            case DeviceSettingFragment.TAG:
                return DeviceSettingFragment.class;
            case NetworkStatusFragment.TAG:
                return NetworkStatusFragment.class;
            case SpotifySettingsFragment.TAG:
                return SpotifySettingsFragment.class;
            case AlexaSettingsFragment.TAG:
                return AlexaSettingsFragment.class;
        }
        return null;
    }

    public Class<? extends Activity> getActivityClassFromTAG(String activityTag){
        switch (activityTag){
            case DeviceInfoActivity.TAG:
                return DeviceInfoActivity.class;
            case SettingsActivity.TAG:
                return SettingsActivity.class;
            case FuntionsActivity.TAG:
                return FuntionsActivity.class;
        }
        return null;
    }
}
