package com.titantrack.utils;

import java.util.Locale;

public final class FormatUtils {

    private FormatUtils() {
        // Clase de utilidades: no se instancia
    }

    // 60.0 -> "60"   |   62.5 -> "62,5" (según el idioma del dispositivo)
    public static String formatKg(double kg) {
        if (kg == Math.rint(kg)) {
            return String.valueOf((long) kg);
        }
        return String.format(Locale.getDefault(), "%.1f", kg);
    }
}