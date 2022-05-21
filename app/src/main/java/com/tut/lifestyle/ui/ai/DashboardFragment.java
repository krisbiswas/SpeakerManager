package com.tut.lifestyle.ui.ai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tut.lifestyle.R;

public class DashboardFragment extends Fragment {
    public static final String TAG = "DashboardFragment";

    private View root;
    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        // Sound Mode
        setupMenuItem(R.id.stub_dashboard_item_sound_mode, "Sound Mode","Music");
        // Equalizer
        setupMenuItem(R.id.stub_dashboard_item_equalizer, "Equalizer","Rock");
        return root;
    }

    private void setupMenuItem(int id, String title, String value) {
        ViewStub stub = root.findViewById(id);
        stub.inflate();
        View inflatedStub = root.findViewById(stub.getInflatedId());
        TextView funcName = inflatedStub.findViewById(R.id.tv_function_name);
        TextView funcMode = inflatedStub.findViewById(R.id.tv_function_mode);
        if(funcName != null && funcMode != null){
            funcName.setText(title);
            funcMode.setText(value);
        }
    }
}