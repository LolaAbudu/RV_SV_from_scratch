package org.pursuit.searchviewandrecyclerviewappfromscratch.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.pursuit.searchviewandrecyclerviewappfromscratch.R;
import org.pursuit.searchviewandrecyclerviewappfromscratch.model.State;
import org.pursuit.searchviewandrecyclerviewappfromscratch.view.StateViewHolder;

import java.util.List;

public class StateAdapter extends RecyclerView.Adapter<StateViewHolder> {
    private List<State> stateList;

    public StateAdapter(List<State> stateList) {
        this.stateList = stateList;
    }

    @NonNull
    @Override
    public StateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View childview = LayoutInflater.from(parent.getContext()).inflate(R.layout.state_itemview, parent, false);
        return new StateViewHolder(childview);
    }

    @Override
    public void onBindViewHolder(@NonNull StateViewHolder stateViewHolder, int position) {
        stateViewHolder.onBind(stateList.get(position));
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public void setData(List<State> newStateList) {
        this.stateList = newStateList;
        notifyDataSetChanged();
    }
}
