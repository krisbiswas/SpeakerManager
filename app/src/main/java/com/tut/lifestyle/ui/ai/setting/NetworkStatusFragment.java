package com.tut.lifestyle.ui.ai.setting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tut.lifestyle.R;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.utils.D2SManager;

public class NetworkStatusFragment extends Fragment implements RemoteRepresentationListener {
    public static final String TAG = "NetworkStatusFragment";
    public static final String TITLE = "Network Status";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
//        assert actionBar != null;
//        actionBar.setTitle(TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_network_status, container, false);
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
//        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.soundFrom);
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {

    }
}