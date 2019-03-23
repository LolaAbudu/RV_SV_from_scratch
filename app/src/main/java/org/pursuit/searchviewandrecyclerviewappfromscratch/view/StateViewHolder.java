package org.pursuit.searchviewandrecyclerviewappfromscratch.view;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.pursuit.searchviewandrecyclerviewappfromscratch.R;
import org.pursuit.searchviewandrecyclerviewappfromscratch.model.State;

public class StateViewHolder extends RecyclerView.ViewHolder {
    private TextView stateTextView;

    public StateViewHolder(@NonNull View itemView) {
        super(itemView);
        stateTextView = itemView.findViewById(R.id.state_text_view);
    }

    public void onBind(State state) {
        String stateNameAndCapital = state.getName() + " " + state.getCapital();
        stateTextView.setText(stateNameAndCapital);
    }
}
