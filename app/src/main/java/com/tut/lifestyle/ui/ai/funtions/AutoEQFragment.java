package com.tut.lifestyle.ui.ai.funtions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tut.lifestyle.R;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.utils.D2SManager;

public class AutoEQFragment extends Fragment  implements RemoteRepresentationListener {

    public static final String TAG = "AutoEQFragment";
    public static final String TITLE = "Auto Equalizer";
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
        View root = inflater.inflate(R.layout.fragment_auto_e_q, container, false);
        root.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Tuning in Progress...", Toast.LENGTH_SHORT).show();
        });
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
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.auto_eq);
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {
        if(response.getStatus() == OCFConstants.RESULT_OK){
            System.out.println(TAG+".onRepresentationReceived : " + response.getAttr() +" : "+response.getVal());
            if(response.getAttr().equalsIgnoreCase(OCFAttributes.auto_eq)){
                if(response.getVal() != null){
                    // true -> tuned
                    // false -> Required Tuning
                    boolean autoEqStatus = Boolean.parseBoolean(response.getVal());
                    if(autoEqStatus){
                        // dismiss loading
                    }else{
                        Toast.makeText(getContext(), "Failed to tune Equalizer!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}