package com.titantrack.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.titantrack.R;
import com.titantrack.adapters.ExerciseAdapter;
import com.titantrack.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class RoutinesFragment extends Fragment {

    public RoutinesFragment() {
        super(R.layout.fragment_routines);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvExercises = view.findViewById(R.id.rvExercises);
        rvExercises.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvExercises.setAdapter(new ExerciseAdapter(createMockExercises()));
    }

    // Mock data: datos simulados en memoria, sin red ni base de datos
    private List<Exercise> createMockExercises() {
        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Press banca", "Pecho", "4 x 10", "Intermedio"));
        exercises.add(new Exercise("Sentadilla", "Piernas", "4 x 8", "Intermedio"));
        exercises.add(new Exercise("Peso muerto", "Espalda baja", "3 x 6", "Avanzado"));
        exercises.add(new Exercise("Dominadas", "Espalda", "4 x 8", "Avanzado"));
        exercises.add(new Exercise("Press militar", "Hombros", "4 x 10", "Intermedio"));
        exercises.add(new Exercise("Curl de bíceps", "Bíceps", "3 x 12", "Principiante"));
        exercises.add(new Exercise("Fondos en paralelas", "Tríceps", "3 x 10", "Intermedio"));
        exercises.add(new Exercise("Remo con barra", "Espalda", "4 x 10", "Intermedio"));
        exercises.add(new Exercise("Zancadas", "Piernas", "3 x 12", "Principiante"));
        exercises.add(new Exercise("Plancha abdominal", "Core", "3 x 45 s", "Principiante"));
        exercises.add(new Exercise("Elevaciones laterales", "Hombros", "3 x 15", "Principiante"));
        exercises.add(new Exercise("Hip thrust", "Glúteos", "4 x 12", "Intermedio"));
        exercises.add(new Exercise("Prensa de piernas", "Piernas", "4 x 12", "Principiante"));
        exercises.add(new Exercise("Aperturas con mancuernas", "Pecho", "3 x 12", "Principiante"));
        exercises.add(new Exercise("Jalón al pecho", "Espalda", "4 x 10", "Principiante"));
        exercises.add(new Exercise("Crunch abdominal", "Core", "3 x 20", "Principiante"));
        return exercises;
    }
}