package com.titantrack.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.titantrack.R;
import com.titantrack.activities.TrainingActivity;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnStartTraining = view.findViewById(R.id.btnStartTraining);
        btnStartTraining.setOnClickListener(v ->
                startActivity(new Intent(requireContext(), TrainingActivity.class)));
    }
}