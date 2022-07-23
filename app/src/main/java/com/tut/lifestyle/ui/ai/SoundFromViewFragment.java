package com.tut.lifestyle.ui.ai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tut.lifestyle.R;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.utils.D2SManager;

public class SoundFromViewFragment extends Fragment implements RemoteRepresentationListener {

    private static final String TAG = "SoundFromViewFragment";
    private View root;
    private TextView mSoundFromTitle;
    private TextView mConnectionTypeSubTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        D2SManager.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_sound_from_view_ai, container, false);
        mSoundFromTitle = root.findViewById(R.id.tv_sound_from_title);
        mConnectionTypeSubTitle = root.findViewById(R.id.tv_connection_type_subtitle);
        return root;
    }

    @Override
    public void onDestroy() {
        D2SManager.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {
        if(response.getStatus() == OCFConstants.RESULT_OK) {
            String attr = response.getAttr();
            System.out.println(TAG + ".onRepresentationReceived : " + response.getAttr() + " : " + response.getVal());
            try {
                if (OCFAttributes.soundFrom.equals(attr)) {
                    String[] soundFromData = response.getVal().split(",");
                    mSoundFromTitle.setText(soundFromData[0]);
                    mConnectionTypeSubTitle.setText(soundFromData[1]);
                }
            } catch (Exception npe) {
                System.out.println(TAG + "Attr null");
            }
        }
    }
}