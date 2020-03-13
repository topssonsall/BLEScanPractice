package com.example.blescanpractice;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewBeaconListViewHolder extends RecyclerView.ViewHolder {

    private TextView beaconInfo;

    public RecyclerViewBeaconListViewHolder(@NonNull View itemView) {
        super(itemView);
        beaconInfo = itemView.findViewById(R.id.rv_items_tv_1);
    }

    public TextView getBeaconInfo() {
        return beaconInfo;
    }

    public void setBeaconInfo(TextView beaconInfo) {
        this.beaconInfo = beaconInfo;
    }
}
