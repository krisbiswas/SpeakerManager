package com.tut.lifestyle.ui.ai.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.tut.lifestyle.R;
import com.tut.lifestyle.SettingsActivity;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.ui.ai.funtions.AdvancedAudioFragment;
import com.tut.lifestyle.ui.ai.funtions.AutoEQFragment;
import com.tut.lifestyle.ui.ai.funtions.WooferFragment;
import com.tut.lifestyle.utils.AppUtils;
import com.tut.lifestyle.utils.D2SManager;

public class DeviceSettingFragment extends Fragment implements RemoteRepresentationListener {
    public static final String TAG = "DeviceSettingFragment";
    public static final String TITLE = "Device Setting";

    private View root;
    private CardView mItemNetworkStatus;
    private CardView mItemBtPairing;
    private CardView mItemChannelVolume;
    private CardView mItemAutoEq;
    private CardView mItemAudioFeedback;

    //region System Callbacks
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setToolbar();
        D2SManager.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_device_setting, container, false);
        initViews();
        return root;
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
    //endregion

    private void initialGets(){
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.nw_status);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.bt_pair);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.channel_vol);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.auto_eq);
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.audio_feedback);
    }

    private void initViews() {
        mItemNetworkStatus = root.findViewById(R.id.item_network_status);
        mItemBtPairing = root.findViewById(R.id.item_bt_pairing);
        mItemChannelVolume = root.findViewById(R.id.item_channel_volume);
        mItemAutoEq = root.findViewById(R.id.item_autoeq);
        mItemAudioFeedback = root.findViewById(R.id.item_audio_feedback);
    }

    void setToolbar(){
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(TITLE);
    }

    private void setupMenuItem(View menuItem, String title, String subtitle) {
//        View stub = root.findViewById(id);
//        View menuItem = root.findViewById(stub.getInflatedId());
//        View menuItem = root.findViewById(id);
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
                String tag = getFragmentTagFromID(menuItem.getId());
                if(!tag.isEmpty()){
                    ((SettingsActivity)getActivity()).launchFragment(tag);
                }
            });
        }
    }

    private String getFragmentTagFromID(int id) {
        String fragmentTag = "";
        switch (id){
            case R.id.item_network_status:
                fragmentTag = NetworkStatusFragment.TAG;break;
                // ToDo Create Fragments for following items
            case R.id.item_channel_volume:
                fragmentTag = WooferFragment.TAG;break;
            case R.id.item_autoeq:
                fragmentTag = AutoEQFragment.TAG;break;
            case R.id.item_audio_feedback:
                fragmentTag = AdvancedAudioFragment.TAG;break;
        }
        return fragmentTag;
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {
        if(response.getStatus() == OCFConstants.RESULT_OK) {
            String attr = response.getAttr();
            System.out.println(TAG + ".onRepresentationReceived : " + response.getAttr() + " : " + response.getVal());
            try {
                switch (attr) {
                    case OCFAttributes.nw_status:
                        if (response.getVal() != null) {
                            setupMenuItem(mItemNetworkStatus, "Network Status", null);
                        }
                        break;
                    case OCFAttributes.bt_pair:
                        if (response.getVal() != null) {
                            mItemBtPairing.setVisibility(View.VISIBLE);
                        }
                        break;
                    case OCFAttributes.channel_vol:
                        if (response.getVal() != null) {
                            setupMenuItem(mItemChannelVolume, "Channel Volume", null);
                        }
                        break;
                    case OCFAttributes.auto_eq:
                        if (response.getVal() != null) {
                            setupMenuItem(mItemAutoEq, "Auto Equalizer", null);
                        }
                        break;
                    case OCFAttributes.audio_feedback:
                        if (response.getVal() != null) {
                            setupMenuItem(mItemAudioFeedback, "Audio Feedback", response.getVal());
                        }
                        break;
                }
            }catch (Exception npe){
                System.out.println(TAG+"Attr null");
            }
        }
    }
}
