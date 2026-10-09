package com.titantrack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.titantrack.R;
import com.titantrack.models.Exercise;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private final List<Exercise> exercises;

    public ExerciseAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        Context context = holder.itemView.getContext();

        holder.tvName.setText(exercise.getName());
        holder.tvMuscleGroup.setText(exercise.getMuscleGroup());
        holder.tvSets.setText(context.getString(R.string.exercise_sets_label, exercise.getSets()));
        holder.tvLevel.setText(context.getString(R.string.exercise_level_label, exercise.getLevel()));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    static class ExerciseViewHolder extends RecyclerView.ViewHolder {

        final TextView tvName;
        final TextView tvMuscleGroup;
        final TextView tvSets;
        final TextView tvLevel;

        ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvExerciseName);
            tvMuscleGroup = itemView.findViewById(R.id.tvMuscleGroup);
            tvSets = itemView.findViewById(R.id.tvExerciseSets);
            tvLevel = itemView.findViewById(R.id.tvExerciseLevel);
        }
    }
}