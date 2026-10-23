package com.titantrack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.titantrack.R;
import com.titantrack.models.WorkoutSet;
import com.titantrack.utils.FormatUtils;

import java.util.List;

public class SetAdapter extends RecyclerView.Adapter<SetAdapter.SetViewHolder> {

    private final List<WorkoutSet> sets;

    public SetAdapter(List<WorkoutSet> sets) {
        this.sets = sets;
    }

    @NonNull
    @Override
    public SetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_set, parent, false);
        return new SetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetViewHolder holder, int position) {
        WorkoutSet workoutSet = sets.get(position);
        Context context = holder.itemView.getContext();

        holder.tvSetInfo.setText(context.getString(
                R.string.training_set_item,
                workoutSet.getSetNumber(),
                FormatUtils.formatKg(workoutSet.getWeightKg()),
                workoutSet.getReps()));
    }

    @Override
    public int getItemCount() {
        return sets.size();
    }

    public static class SetViewHolder extends RecyclerView.ViewHolder {

        final TextView tvSetInfo;

        SetViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSetInfo = itemView.findViewById(R.id.tvSetInfo);
        }
    }
}