package com.tut.lifestyle.ui.nonai;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tut.lifestyle.FunctionsActivity;
import com.tut.lifestyle.R;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.ui.ai.funtions.EqualizerFragment;
import com.tut.lifestyle.ui.ai.funtions.WooferFragment;
import com.tut.lifestyle.utils.D2SManager;

public class FunctionMenuFragment extends Fragment implements RemoteRepresentationListener {

    private static final String TAG = "FunctionMenuFragment";
    private View root;
    private LinearLayout mItemMusicService;
    private LinearLayout mItemEqualizer;
    private LinearLayout mItemInputSource;
    private TextView mEqModeValue;
    private LinearLayout mItemWoofer;
    private LinearLayout mItemSleepTimer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        D2SManager.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_function_menu_non_ai, container, false);
        initViews();
        return root;
    }

    @Override
    public void onDestroy() {
        D2SManager.getInstance().unregister(this);
        super.onDestroy();
    }

    private void initViews() {
        mItemMusicService = root.findViewById(R.id.menu_item_music_service);
        mItemMusicService.setOnClickListener(v -> {});
        mItemInputSource = root.findViewById(R.id.menu_item_input_source);
        mItemInputSource.setOnClickListener(v -> {});
        mItemEqualizer = root.findViewById(R.id.menu_item_equalizer);
        mEqModeValue = mItemEqualizer.findViewById(R.id.tv_function_value);
        mItemEqualizer.setOnClickListener(v -> {});
        mItemWoofer = root.findViewById(R.id.menu_item_woofer);
        mItemWoofer.setOnClickListener(v -> {});
        mItemSleepTimer = root.findViewById(R.id.menu_item_sleep_timer);
        mItemSleepTimer.setOnClickListener(v -> {});
    }

    private void setupMenuItem(View menuItem, String title, String subtitle) {
//        View stub = root.findViewById(id);
//        View menuItem = root.findViewById(stub.getInflatedId());
//        View menuItem = root.findViewById(id);
        menuItem.setVisibility(View.VISIBLE);
        TextView funcName = menuItem.findViewById(R.id.tv_function_name);
        TextView funcMode = menuItem.findViewById(R.id.tv_function_value);
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

    private String getFragmentTagFromID(int id) {
        String fragmentTag = "";
        switch (id){
            case R.id.menu_item_equalizer:
                fragmentTag = EqualizerFragment.TAG;break;
            case R.id.menu_item_woofer:
                fragmentTag = WooferFragment.TAG;break;
            case R.id.menu_item_input_source:
                fragmentTag = InputSourceFragment.TAG;break;
            case R.id.menu_item_music_service:
                // Music Service Fragment
                break;
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
                    case OCFAttributes.inputsource:
                        if(response.getVal() != null){
                            setupMenuItem(mItemInputSource, "Input Source", response.getVal());
                        }
                        break;
                    case OCFAttributes.sleeptimer:
                        setupMenuItem(mItemSleepTimer, "Sleep Timers", null);
                        break;
                }
            }catch (Exception npe){
                System.out.println(TAG+"Attr null");
            }
        }
    }
}