package com.tut.lifestyle.ui.adaptors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tut.lifestyle.R;

import java.util.List;

public class SoundFromRecyclerViewAdapter extends RecyclerView.Adapter<SoundFromRecyclerViewAdapter.ViewHolder> {

    private List<String> mDeviceNames;
    private List<String> mConnectionTypes;

    public SoundFromRecyclerViewAdapter(List<String> mDeviceNames, List<String> mConnectionTypes) {
        this.mDeviceNames = mDeviceNames;
        this.mConnectionTypes = mConnectionTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_sound_from, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvDeviceName.setText(mDeviceNames.get(position));
        holder.tvConnectionType.setText(mConnectionTypes.get(position));
    }

    @Override
    public int getItemCount() {
        return mDeviceNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvConnectionType;
        public TextView tvDeviceName;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}