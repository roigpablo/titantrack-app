package com.titantrack.models;

public class WorkoutSet {

    private final int setNumber;
    private final double weightKg;
    private final int reps;

    public WorkoutSet(int setNumber, double weightKg, int reps) {
        this.setNumber = setNumber;
        this.weightKg = weightKg;
        this.reps = reps;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public double getWeightKg() {
        return weightKg;
    }

    public int getReps() {
        return reps;
    }

    // Volumen de la serie: kilos x repeticiones
    public double getVolume() {
        return weightKg * reps;
    }
}