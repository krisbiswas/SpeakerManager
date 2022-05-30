package com.tut.lifestyle.ui.ai.funtions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tut.lifestyle.R;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.utils.D2SManager;

public class AdvancedAudioFragment extends Fragment implements RemoteRepresentationListener {

    public static final String TAG = "AdvancedAudioFragment";
    public static final String TITLE = "Advanced Audio Setting";
    View root;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(TITLE);
        D2SManager.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_advanced_audio, container, false);
        setOnClickListeners();
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

    private void initialGets(){
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.adv_audio);
    }

    private void setOnClickListeners(){
        root.findViewById(R.id.option1).setOnClickListener(v -> {
            D2SManager.getInstance().sendRemoteRepresentation(OCFAttributes.adv_audio, ((RadioButton)v).getText().toString());
        });
        root.findViewById(R.id.option2).setOnClickListener(v -> {
            D2SManager.getInstance().sendRemoteRepresentation(OCFAttributes.adv_audio, ((RadioButton)v).getText().toString());
        });
        root.findViewById(R.id.option3).setOnClickListener(v -> {
            D2SManager.getInstance().sendRemoteRepresentation(OCFAttributes.adv_audio, ((RadioButton)v).getText().toString());
        });
        root.findViewById(R.id.option4).setOnClickListener(v -> {
            D2SManager.getInstance().sendRemoteRepresentation(OCFAttributes.adv_audio, ((RadioButton)v).getText().toString());
        });
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {
        if(response.getStatus() == OCFConstants.RESULT_OK){
            if(response.getAttr().equalsIgnoreCase(OCFAttributes.adv_audio)){
                // dismiss loading
                System.out.println(TAG+".onRepresentationReceived() "+response.getVal());
            }
        }
    }
}