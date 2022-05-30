package com.tut.lifestyle.ui.ai;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tut.lifestyle.FuntionsActivity;
import com.tut.lifestyle.R;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.ui.ai.funtions.AdvancedAudioFragment;
import com.tut.lifestyle.ui.ai.funtions.AutoEQFragment;
import com.tut.lifestyle.ui.ai.funtions.EqualizerFragment;
import com.tut.lifestyle.ui.ai.funtions.SoundModeFragment;
import com.tut.lifestyle.ui.ai.funtions.WooferFragment;
import com.tut.lifestyle.utils.AppUtils;
import com.tut.lifestyle.utils.D2SManager;

public class DashboardFragment extends Fragment implements RemoteRepresentationListener {
    public static final String TAG = "DashboardFragment";

    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        D2SManager.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        boolean isAi = D2SManager.getInstance().getPluginDevice().isAISoundbar();
        root = inflater.inflate((isAi ? R.layout.fragment_dashboard_ai : R.layout.fragment_dashboard_non_ai), container, false);
        initView(isAi);
        return root;
    }

    private void initView(boolean isAi) {
        if(isAi){
            ((SeekBar)root.findViewById(R.id.seekbar_volume)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    int progress = seekBar.getProgress();
                    D2SManager.getInstance().sendRemoteRepresentation(OCFAttributes.spk_vol, String.valueOf(progress));
                }
            });
        }else{

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initialGets();
    }

    @Override
    public void onDestroy() {
        D2SManager.getInstance().unregister(this);
        super.onDestroy();
    }

    private void setupMenuItem(int id, String title, String subtitle) {
//        View stub = root.findViewById(id);
//        View menuItem = root.findViewById(stub.getInflatedId());
        View menuItem = root.findViewById(id);
        menuItem.setVisibility(View.VISIBLE);
        TextView funcName = menuItem.findViewById(R.id.tv_function_name);
        TextView funcMode = menuItem.findViewById(R.id.tv_function_value);
        Switch functionalSwitch = menuItem.findViewById(R.id.switchWidget);
        if(functionalSwitch != null){
            if(funcName != null){
                funcName.setText(title);
            }
            if(!functionalSwitch.hasOnClickListeners() && !menuItem.hasOnClickListeners()){
                functionalSwitch.setOnClickListener((view)->{
                    // Send POST request with switch val
                    System.out.println("Switch Click Listener : clicked "+title);
                    D2SManager.getInstance().sendRemoteRepresentation(
                            AppUtils.getInstance().titleToAttr(title),
                            String.valueOf(((Switch)view).isChecked())
                    );
                });
                menuItem.setOnClickListener((view)->{
                    functionalSwitch.performClick();
                });
            }
            functionalSwitch.setChecked(Boolean.parseBoolean(subtitle));

        }else{
            // Functional Setting activity
            if(funcName != null && funcMode != null){
                funcName.setText(title);
                if(subtitle != null){
                    funcMode.setText(subtitle);
                    funcMode.setVisibility(View.VISIBLE);
                }
            }
            menuItem.setOnClickListener((view)->{
                launchActivity(FuntionsActivity.TAG, getFragmentTagFromID(id));
            });
        }
    }

    private String getFragmentTagFromID(int id) {
        String fragmentTag = "";
        switch (id){
            case R.id.menu_item_sound_mode:
                fragmentTag = SoundModeFragment.TAG;break;
            case R.id.menu_item_equalizer:
                fragmentTag = EqualizerFragment.TAG;break;
            case R.id.menu_item_woofer:
                fragmentTag = WooferFragment.TAG;break;
            case R.id.menu_item_autoeq:
                fragmentTag = AutoEQFragment.TAG;break;
            case R.id.menu_item_advanced_audio:
                fragmentTag = AdvancedAudioFragment.TAG;break;
        }
        return fragmentTag;
    }

    private void launchActivity(String activityTag, String fragmentTag) {
        Intent intent;
        switch (activityTag){
            case FuntionsActivity.TAG:
                intent = new Intent(getContext(), FuntionsActivity.class);
                intent.putExtra("Fragment", fragmentTag);
                startActivity(intent);
                break;
        }
    }

    private void initialGets(){
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.device_name);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.device_loc);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.soundFrom);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.soundMode);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.eq);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.woofer);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.auto_eq);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.spaceFit);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.ava);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.adv_audio);
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {
        if(response.getStatus() == OCFConstants.RESULT_OK){
            String attr = response.getAttr();
            System.out.println(TAG+".onRepresentationReceived : " + response.getAttr() +" : "+response.getVal());

            if(D2SManager.getInstance().getPluginDevice().isAISoundbar()){
                try{
                    switch(attr) {
                        case OCFAttributes.device_name:
                            ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                            assert actionBar != null;
                            if(response.getVal() != null){
                                actionBar.setTitle(response.getVal());
                            }
                            break;
                        case OCFAttributes.device_loc:
                            actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                            assert actionBar != null;
                            if(response.getVal() != null){
                                actionBar.setSubtitle(response.getVal());
                            }
                            break;
                        case OCFAttributes.soundFrom:
                            String[] soundFromData = response.getVal().split(",");
                            ((TextView)root.findViewById(R.id.tv_device_name)).setText(soundFromData[0]);
                            ((TextView)root.findViewById(R.id.tv_connection_type)).setText(soundFromData[1]);
                            break;
                        case OCFAttributes.spk_vol:
                            if(response.getVal() != null){
                                int seekPos = Integer.parseInt(response.getVal());
                                ((SeekBar)root.findViewById(R.id.seekbar_volume)).setProgress(seekPos);
                            }
                            break;
                        case OCFAttributes.soundMode:
                            if(response.getVal() != null){
                                setupMenuItem(R.id.menu_item_sound_mode, "Sound Mode", response.getVal());
                            }
                            break;
                        case OCFAttributes.eq:
                            if(response.getVal() != null){
                                setupMenuItem(R.id.menu_item_equalizer, "Equalizer", response.getVal());
                            }
                            break;
                        case OCFAttributes.woofer:
                            int wooferVal = Integer.parseInt(response.getVal());
                            if(wooferVal >= 0){
                                setupMenuItem(R.id.menu_item_woofer, "Woofer", null);
                            }
                            break;
                        case OCFAttributes.auto_eq:
                            if(response.getVal() != null){
                                setupMenuItem(R.id.menu_item_autoeq, "Auto Equalizer",null);
                            }
                            break;
                        case OCFAttributes.spaceFit:
                            if(response.getVal() != null){
                                setupMenuItem(R.id.menu_item_spacefit, "Space Fit",response.getVal());
                            }
                            break;
                        case OCFAttributes.ava:
                            if(response.getVal() != null){
                                setupMenuItem(R.id.menu_item_ava, "Voice Assistant",response.getVal());
                            }
                            break;
                        case OCFAttributes.adv_audio:
                            if(response.getVal() != null){
                                setupMenuItem(R.id.menu_item_advanced_audio, "Advanced Audio Setting",null);
                            }
                            break;
                    }
                }catch (Exception npe){
                    System.out.println(TAG+"Attr null");
                }
            }else{
                try{
                    switch(attr) {
                        case OCFAttributes.device_name:
                            ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                            assert actionBar != null;
                            if(response.getVal() != null){
                                actionBar.setTitle(response.getVal());
                            }
                            break;
                        case OCFAttributes.device_loc:
                            actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                            assert actionBar != null;
                            if(response.getVal() != null){
                                actionBar.setSubtitle(response.getVal());
                            }
                            break;
                        case OCFAttributes.soundFrom:
                            String[] soundFromData = response.getVal().split(",");
                            ((TextView)root.findViewById(R.id.tv_device_name)).setText(soundFromData[0]);
                            ((TextView)root.findViewById(R.id.tv_connection_type)).setText(soundFromData[1]);
                            break;
                        case OCFAttributes.spk_vol:
                            if(response.getVal() != null){
                                int seekPos = Integer.parseInt(response.getVal());
                                ((SeekBar)root.findViewById(R.id.seekbar_volume)).setProgress(seekPos);
                            }
                            break;
                        case OCFAttributes.eq:
                            if(response.getVal() != null){
                                setupMenuItem(R.id.menu_item_equalizer, "Equalizer", response.getVal());
                            }
                            break;
                        case OCFAttributes.woofer:
                            int wooferVal = Integer.parseInt(response.getVal());
                            if(wooferVal >= 0){
                                setupMenuItem(R.id.menu_item_woofer, "Woofer", null);
                            }
                            break;
                    }
                }catch (Exception npe){
                    System.out.println(TAG+"Attr null");
                }
            }
        }
    }
}