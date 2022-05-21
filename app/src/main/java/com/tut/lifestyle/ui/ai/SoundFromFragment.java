package com.tut.lifestyle.ui.ai;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tut.lifestyle.R;
import com.tut.lifestyle.ui.adaptors.SoundFromRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SoundFromFragment extends Fragment {
    public static final String TAG = "SoundFromFragment";
    private List<String> mDeviceNames;
    private List<String> mConnectionTypes;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeviceNames = new ArrayList<>();
        mConnectionTypes = new ArrayList<>();
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
}