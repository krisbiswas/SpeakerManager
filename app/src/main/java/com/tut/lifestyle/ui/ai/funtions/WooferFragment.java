package com.tut.lifestyle.ui.ai.funtions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tut.lifestyle.R;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.constants.OCFConstants;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.utils.D2SManager;

public class WooferFragment extends Fragment implements RemoteRepresentationListener {

    public static final String TAG = "WooferFragment";
    public static final String TITLE = "Woofer Setting";

    private View root;
    private SeekBar seekBar;
    private final int DEFAULT = 0;

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
        root = inflater.inflate(R.layout.fragment_woofer, container, false);
        root.findViewById(R.id.tv_reset).setOnClickListener(v -> {
            seekBar.setProgress(DEFAULT);
            D2SManager.getInstance().sendRemoteRepresentation(OCFAttributes.woofer, String.valueOf(DEFAULT));
        });
        seekBar = root.findViewById(R.id.seekbar_woofer);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int pos = seekBar.getProgress();
                System.out.println("Seek Bar seek position : "+pos);
                D2SManager.getInstance().sendRemoteRepresentation(OCFAttributes.woofer, String.valueOf(pos));
            }
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
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.woofer);
    }
    @Override
    public void onRepresentationReceived(OCFResponse response) {
        if(response.getStatus() == OCFConstants.RESULT_OK){
            String attr = response.getAttr();
            System.out.println(TAG+".onRepresentationReceived : " + response.getAttr() +" : "+response.getVal());

            switch(attr) {
                case OCFAttributes.woofer:
                    if (response.getVal() != null) {
                        seekBar.setProgress(Integer.parseInt(response.getVal()));
                    }
                    break;
            }
        }
    }
}