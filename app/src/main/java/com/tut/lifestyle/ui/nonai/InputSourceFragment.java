package com.tut.lifestyle.ui.nonai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tut.lifestyle.R;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.utils.D2SManager;

public class InputSourceFragment extends Fragment implements RemoteRepresentationListener {

    public static final String TAG = "InputSourceFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        D2SManager.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_input_source_non_ai, container, false);
    }

    @Override
    public void onDestroy() {
        D2SManager.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {

    }
}