package com.titantrack.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.titantrack.R;
import com.titantrack.models.WorkoutRecord;
import com.titantrack.utils.FormatUtils;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final List<WorkoutRecord> records;

    public HistoryAdapter(List<WorkoutRecord> records) {
        this.records = records;
    }

    // Sustituye el contenido por los datos recién leídos de la base de datos
    public void setRecords(List<WorkoutRecord> newRecords) {
        records.clear();
        records.addAll(newRecords);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        WorkoutRecord record = records.get(position);
        holder.tvDate.setText(record.getFecha());
        holder.tvSummary.setText(holder.itemView.getContext().getString(
                R.string.history_item_summary,
                record.getTotalSeries(),
                FormatUtils.formatKg(record.getVolumenTotal())));
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        final TextView tvDate;
        final TextView tvSummary;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvHistoryDate);
            tvSummary = itemView.findViewById(R.id.tvHistorySummary);
        }
    }
}