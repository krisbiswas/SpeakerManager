package com.tut.lifestyle.ui.ai.funtions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tut.lifestyle.R;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;

public class SmartHubFragment extends Fragment implements RemoteRepresentationListener {
    public static final String TAG = "SmartHubFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.layout_hub_setup, container, false);
        return root;
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {

    }
}
