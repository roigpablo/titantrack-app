package com.titantrack.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;                                // NUEVO (S7)

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.titantrack.R;
import com.titantrack.adapters.SetAdapter;
import com.titantrack.database.DatabaseHelper;              // NUEVO (S7)
import com.titantrack.models.WorkoutSet;
import com.titantrack.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TrainingActivity extends AppCompatActivity {

    private static final int REST_SECONDS = 60;

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
    private TextView tvTimer;
    private Button btnConfirmSet;
    private Button btnFinishTraining;                       // NUEVO (S7)
    private CountDownTimer restTimer;
    private DatabaseHelper dbHelper;                        // NUEVO (S7)

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
        tvTimer = findViewById(R.id.tvTimer);
        rvSets = findViewById(R.id.rvSets);
        btnConfirmSet = findViewById(R.id.btnConfirmSet);
        btnFinishTraining = findViewById(R.id.btnFinishTraining);   // NUEVO (S7)

        dbHelper = new DatabaseHelper(this);                // NUEVO (S7)

        setAdapter = new SetAdapter(sets);
        rvSets.setLayoutManager(new LinearLayoutManager(this));
        rvSets.setAdapter(setAdapter);

        btnConfirmSet.setOnClickListener(v -> confirmSet());
        btnFinishTraining.setOnClickListener(v -> finishTraining());   // NUEVO (S7)

        tvTimer.setText(formatTime(0));
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
        startRestTimer();
    }

    // NUEVO (S7): vuelca la sesión en memoria al disco (SQLite) y cierra la pantalla
    private void finishTraining() {
        if (sets.isEmpty()) {
            Toast.makeText(this, R.string.training_no_sets, Toast.LENGTH_SHORT).show();
            return;
        }

        dbHelper.insertarEntrenamiento(
                FormatUtils.formatDateTime(System.currentTimeMillis()),
                sets.size(),
                getTotalVolume());

        Toast.makeText(this, R.string.training_saved, Toast.LENGTH_SHORT).show();
        finish();
    }

    // Descanso entre series con cuenta atrás visual
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

    // Convierte milisegundos a formato mm:ss
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

    // NUEVO (S7): el cálculo del volumen se extrae para usarlo en el resumen y al guardar
    private double getTotalVolume() {
        double totalVolume = 0;
        for (WorkoutSet workoutSet : sets) {
            totalVolume += workoutSet.getVolume();
        }
        return totalVolume;
    }

    private void updateSummary() {
        tvSummary.setText(getString(R.string.training_summary,
                sets.size(), FormatUtils.formatKg(getTotalVolume())));
    }

    // Evita que el temporizador siga vivo y libera la base de datos al cerrar la pantalla
    @Override
    protected void onDestroy() {
        if (restTimer != null) {
            restTimer.cancel();
        }
        dbHelper.close();                                   // NUEVO (S7)
        super.onDestroy();
    }
}