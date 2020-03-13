package com.example.blescanpractice;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewBeaconListAdapter extends RecyclerView.Adapter<RecyclerViewBeaconListViewHolder> {

    private List<Beacon> beaconList;

    @NonNull
    @Override
    public RecyclerViewBeaconListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_beacon_list_items,parent,false);
        RecyclerViewBeaconListViewHolder holder = new RecyclerViewBeaconListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewBeaconListViewHolder holder, int position) {
        Beacon beacon = beaconList.get(position);
        holder.getBeaconInfo().setText(beacon.getMacAddress());
    }

    @Override
    public int getItemCount() {
        return beaconList.size();
    }

    public List<Beacon> getBeaconList() {
        return beaconList;
    }

    public void setBeaconList(List<Beacon> beaconList) {
        this.beaconList = beaconList;
    }
}
