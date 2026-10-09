package com.titantrack.models;

public class Exercise {

    private final String name;
    private final String muscleGroup;
    private final String sets;
    private final String level;

    public Exercise(String name, String muscleGroup, String sets, String level) {
        this.name = name;
        this.muscleGroup = muscleGroup;
        this.sets = sets;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public String getSets() {
        return sets;
    }

    public String getLevel() {
        return level;
    }
}