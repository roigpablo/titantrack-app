package com.titantrack.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.titantrack.R;
import com.titantrack.adapters.HistoryAdapter;
import com.titantrack.database.DatabaseHelper;
import com.titantrack.models.WorkoutRecord;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends Fragment {

    private DatabaseHelper dbHelper;
    private HistoryAdapter historyAdapter;
    private RecyclerView rvHistory;
    private TextView tvHistoryEmpty;

    public ProgressFragment() {
        super(R.layout.fragment_progress);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvHistory = view.findViewById(R.id.rvHistory);
        tvHistoryEmpty = view.findViewById(R.id.tvHistoryEmpty);

        dbHelper = new DatabaseHelper(requireContext());
        historyAdapter = new HistoryAdapter(new ArrayList<>());
        rvHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvHistory.setAdapter(historyAdapter);
    }

    // Se ejecuta cada vez que la pantalla vuelve a ser visible:
    // así el historial se refresca solo tras guardar un entrenamiento nuevo
    @Override
    public void onResume() {
        super.onResume();
        loadHistory();
    }

    // SELECT sobre la base de datos -> lista de objetos -> adapter
    private void loadHistory() {
        List<WorkoutRecord> records = dbHelper.obtenerHistorial();
        historyAdapter.setRecords(records);

        boolean empty = records.isEmpty();
        tvHistoryEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
        rvHistory.setVisibility(empty ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        dbHelper.close();
        super.onDestroyView();
    }
}