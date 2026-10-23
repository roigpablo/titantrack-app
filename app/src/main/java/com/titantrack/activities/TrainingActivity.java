package com.titantrack.activities;

import android.os.Bundle;
import android.os.CountDownTimer;                       // NUEVO
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.titantrack.R;
import com.titantrack.adapters.SetAdapter;
import com.titantrack.models.WorkoutSet;
import com.titantrack.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;                                // NUEVO

public class TrainingActivity extends AppCompatActivity {

    private static final int REST_SECONDS = 60;         // NUEVO: duración del descanso

    // Registro volátil en memoria: se pierde al destruirse la Activity
    private final List<WorkoutSet> sets = new ArrayList<>();

    private SetAdapter setAdapter;
    private RecyclerView rvSets;
    private TextInputLayout tilWeight;
    private TextInputLayout tilReps;
    private TextInputEditText etWeight;
    private TextInputEditText etReps;
    private TextView tvCurrentSet;
    private TextView tvSummary;
    private TextView tvTimer;                           // NUEVO
    private Button btnConfirmSet;                       // NUEVO (ahora es un campo)
    private CountDownTimer restTimer;                   // NUEVO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        tilWeight = findViewById(R.id.tilWeight);
        tilReps = findViewById(R.id.tilReps);
        etWeight = findViewById(R.id.etWeight);
        etReps = findViewById(R.id.etReps);
        tvCurrentSet = findViewById(R.id.tvCurrentSet);
        tvSummary = findViewById(R.id.tvSummary);
        tvTimer = findViewById(R.id.tvTimer);           // NUEVO
        rvSets = findViewById(R.id.rvSets);
        btnConfirmSet = findViewById(R.id.btnConfirmSet);

        setAdapter = new SetAdapter(sets);
        rvSets.setLayoutManager(new LinearLayoutManager(this));
        rvSets.setAdapter(setAdapter);

        btnConfirmSet.setOnClickListener(v -> confirmSet());

        tvTimer.setText(formatTime(0));                 // NUEVO: muestra 00:00 al empezar
        updateCurrentSet();
        updateSummary();
    }

    private void confirmSet() {
        double weight = parseWeight(textOf(etWeight));
        int reps = parseReps(textOf(etReps));

        // & (y no &&) para evaluar ambas validaciones y mostrar los dos errores a la vez
        boolean valid = validateWeight(weight) & validateReps(reps);
        if (!valid) {
            return;
        }

        sets.add(new WorkoutSet(sets.size() + 1, weight, reps));
        setAdapter.notifyItemInserted(sets.size() - 1);
        rvSets.scrollToPosition(sets.size() - 1);

        etWeight.setText("");
        etReps.setText("");

        updateCurrentSet();
        updateSummary();
        startRestTimer();                               // NUEVO
    }

    // NUEVO: descanso entre series con cuenta atrás visual
    private void startRestTimer() {
        btnConfirmSet.setEnabled(false);

        restTimer = new CountDownTimer(REST_SECONDS * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(formatTime(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                tvTimer.setText(formatTime(0));
                btnConfirmSet.setEnabled(true);
            }
        }.start();
    }

    // NUEVO: convierte milisegundos a formato mm:ss
    private String formatTime(long millis) {
        long totalSeconds = Math.round(millis / 1000.0);
        return String.format(Locale.getDefault(), "%02d:%02d",
                totalSeconds / 60, totalSeconds % 60);
    }

    private boolean validateWeight(double weight) {
        if (weight <= 0) {
            tilWeight.setError(getString(R.string.error_weight_invalid));
            return false;
        }
        tilWeight.setError(null);
        return true;
    }

    private boolean validateReps(int reps) {
        if (reps <= 0) {
            tilReps.setError(getString(R.string.error_reps_invalid));
            return false;
        }
        tilReps.setError(null);
        return true;
    }

    // Devuelve 0 si el texto está vacío o no es un número válido
    private double parseWeight(String text) {
        try {
            return Double.parseDouble(text.replace(',', '.'));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int parseReps(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String textOf(TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString().trim();
    }

    private void updateCurrentSet() {
        tvCurrentSet.setText(getString(R.string.training_current_set, sets.size() + 1));
    }

    private void updateSummary() {
        double totalVolume = 0;
        for (WorkoutSet workoutSet : sets) {
            totalVolume += workoutSet.getVolume();
        }
        tvSummary.setText(getString(R.string.training_summary,
                sets.size(), FormatUtils.formatKg(totalVolume)));
    }

    // NUEVO: evita que el temporizador siga vivo cuando la pantalla ya no existe
    @Override
    protected void onDestroy() {
        if (restTimer != null) {
            restTimer.cancel();
        }
        super.onDestroy();
    }
}