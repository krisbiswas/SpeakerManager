package com.tut.lifestyle.ui.ai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.tut.lifestyle.R;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.utils.D2SManager;

public class PlayerFragment extends Fragment implements RemoteRepresentationListener {

    private static final String TAG = "PlayerFragment";
    private View root;
    private FragmentContainerView mPlayerContainer;
    private SeekBar mVolSeekBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        D2SManager.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_player, container, false);
        mPlayerContainer = root.findViewById(R.id.player_container);
        mVolSeekBar = root.findViewById(R.id.seekbar_volume);
        mVolSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return root;
    }

    @Override
    public void onDestroy() {
        D2SManager.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {
        if(response.getStatus() == OCFConstants.RESULT_OK){
            String attr = response.getAttr();
            System.out.println(TAG+".onRepresentationReceived : " + response.getAttr() +" : "+response.getVal());

            try{
                switch(attr) {
                    case OCFAttributes.soundFrom:
                        String[] soundFromData = response.getVal().split(",");
//                        ((TextView)root.findViewById(R.id.tv_sound_from_title)).setText(soundFromData[0]);
//                        ((TextView)root.findViewById(R.id.tv_connection_type)).setText(soundFromData[1]);
                        break;
                    case OCFAttributes.spk_vol:
                        if(response.getVal() != null){
                            int seekPos = Integer.parseInt(response.getVal());
                            mVolSeekBar.setProgress(seekPos);
                        }
                        break;
                }
            }catch (Exception npe){
                System.out.println(TAG+"Attr null");
            }
        }
    }
}