package com.tut.lifestyle.ui.ai;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.tut.lifestyle.FunctionsActivity;
import com.tut.lifestyle.R;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.ui.ai.funtions.AdvancedAudioFragment;
import com.tut.lifestyle.ui.ai.funtions.AutoEQFragment;
import com.tut.lifestyle.ui.ai.funtions.EqualizerFragment;
import com.tut.lifestyle.ui.ai.funtions.SmartHubFragment;
import com.tut.lifestyle.ui.ai.funtions.SoundModeFragment;
import com.tut.lifestyle.ui.ai.funtions.WooferFragment;
import com.tut.lifestyle.utils.AppUtils;
import com.tut.lifestyle.utils.D2SManager;

public class FunctionMenuFragment extends Fragment implements RemoteRepresentationListener {

    private static final String TAG = "FunctionMenuFragment";
    private View root;
    private CardView mItemSoundMode;
    private TextView mSoundModeValue;
    private CardView mItemEqualizer;
    private TextView mEqModeValue;
    private CardView mItemWoofer;
    private CardView mItemSpaceFit;
    private CardView mItemAVA;
    private CardView mItemAutoEq;
    private CardView mItemAdvancedAudio;
    private CardView mItemSmartHub;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        D2SManager.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_function_menu_ai, container, false);
        initViews();
        return root;
    }

    @Override
    public void onDestroy() {
        D2SManager.getInstance().unregister(this);
        super.onDestroy();
    }

    private void initViews() {
        mItemSoundMode = root.findViewById(R.id.menu_item_sound_mode);
        mItemSoundMode.setOnClickListener(v -> {});
        mSoundModeValue = mItemSoundMode.findViewById(R.id.tv_function_value);
        mItemEqualizer = root.findViewById(R.id.menu_item_equalizer);
        mEqModeValue = mItemEqualizer.findViewById(R.id.tv_function_value);
        mItemEqualizer.setOnClickListener(v -> {});
        mItemWoofer = root.findViewById(R.id.menu_item_woofer);
        mItemWoofer.setOnClickListener(v -> {});
        mItemSpaceFit = root.findViewById(R.id.menu_item_spacefit);
        mItemSpaceFit.setOnClickListener(v -> {});
        mItemAVA = root.findViewById(R.id.menu_item_ava);
        mItemAVA.setOnClickListener(v -> {});
        mItemAutoEq = root.findViewById(R.id.menu_item_autoeq);
        mItemAutoEq.setOnClickListener(v -> {});
        mItemAdvancedAudio = root.findViewById(R.id.menu_item_advanced_audio);
        mItemAdvancedAudio.setOnClickListener(v -> {});
        mItemSmartHub = root.findViewById(R.id.menu_item_smart_hub);
        mItemSmartHub.setOnClickListener(v -> {});
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
                    launchActivity(FunctionsActivity.TAG, tag);
                }
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
            case R.id.menu_item_smart_hub:
                fragmentTag = SmartHubFragment.TAG;break;
        }
        return fragmentTag;
    }

    private void launchActivity(String activityTag, String fragmentTag) {
        Intent intent;
        switch (activityTag){
            case FunctionsActivity.TAG:
                intent = new Intent(getContext(), FunctionsActivity.class);
                intent.putExtra("Fragment", fragmentTag);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {
        if(response.getStatus() == OCFConstants.RESULT_OK){
            String attr = response.getAttr();
            System.out.println(TAG+".onRepresentationReceived : " + response.getAttr() +" : "+response.getVal());
            try{
                switch(attr) {
                    case OCFAttributes.soundMode:
                        if(response.getVal() != null){
                            setupMenuItem(mItemSoundMode, "Sound Mode", response.getVal());
                        }
                        break;
                    case OCFAttributes.eq:
                        if(response.getVal() != null){
                            setupMenuItem(mItemEqualizer, "Equalizer", response.getVal());
                        }
                        break;
                    case OCFAttributes.woofer:
                        int wooferVal = Integer.parseInt(response.getVal());
                        if(wooferVal >= 0){
                            setupMenuItem(mItemWoofer, "Woofer", null);
                        }
                        break;
                    case OCFAttributes.auto_eq:
                        if(response.getVal() != null){
                            setupMenuItem(mItemAutoEq, "Auto Equalizer",null);
                        }
                        break;
                    case OCFAttributes.spaceFit:
                        if(response.getVal() != null){
                            setupMenuItem(mItemSpaceFit, "Space Fit",response.getVal());
                        }
                        break;
                    case OCFAttributes.ava:
                        if(response.getVal() != null){
                            setupMenuItem(mItemAVA, "Voice Assistant",response.getVal());
                        }
                        break;
                    case OCFAttributes.adv_audio:
                        if(response.getVal() != null){
                            setupMenuItem(mItemAdvancedAudio, "Advanced Audio Setting",null);
                        }
                        break;
                    case OCFAttributes.smart_hub:
                        if(response.getVal() != null){
                            setupMenuItem(mItemSmartHub, "Smart Hub",response.getVal());
                        }
                        break;
                }
            }catch (Exception npe){
                System.out.println(TAG+"Attr null");
            }
        }
    }
}