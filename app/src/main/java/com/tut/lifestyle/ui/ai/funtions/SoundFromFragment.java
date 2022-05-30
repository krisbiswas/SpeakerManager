package com.tut.lifestyle.ui.ai.funtions;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tut.lifestyle.R;
import com.tut.lifestyle.constants.OCFAttributes;
import com.tut.lifestyle.data.OCFResponse;
import com.tut.lifestyle.ocfs.RemoteRepresentationListener;
import com.tut.lifestyle.ui.adaptors.SoundFromRecyclerViewAdapter;
import com.tut.lifestyle.utils.D2SManager;

import java.util.ArrayList;
import java.util.List;

public class SoundFromFragment extends Fragment implements RemoteRepresentationListener {
    public static final String TAG = "SoundFromFragment";
    public static final String TITLE = "Sound From";

    private List<String> mDeviceNames;
    private List<String> mConnectionTypes;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeviceNames = new ArrayList<>();
        mConnectionTypes = new ArrayList<>();
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(TITLE);
        D2SManager.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sound_from, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new SoundFromRecyclerViewAdapter(mDeviceNames,mConnectionTypes));
        }
        return view;
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
        D2SManager.getInstance().getRemoteRepresentation(OCFAttributes.soundFrom);
    }

    @Override
    public void onRepresentationReceived(OCFResponse response) {

    }
}