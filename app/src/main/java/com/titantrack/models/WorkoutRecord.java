package com.titantrack.models;

// Una fila de tabla_historial: un entrenamiento ya terminado y guardado en disco
public class WorkoutRecord {

    private final long id;
    private final String fecha;
    private final int totalSeries;
    private final double volumenTotal;

    public WorkoutRecord(long id, String fecha, int totalSeries, double volumenTotal) {
        this.id = id;
        this.fecha = fecha;
        this.totalSeries = totalSeries;
        this.volumenTotal = volumenTotal;
    }

    public long getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public int getTotalSeries() {
        return totalSeries;
    }

    public double getVolumenTotal() {
        return volumenTotal;
    }
}