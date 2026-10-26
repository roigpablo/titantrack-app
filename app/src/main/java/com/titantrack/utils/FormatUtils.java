package com.titantrack.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    // NUEVO (S7): fecha y hora legible, p. ej. "26/10/2026 16:07"
    public static String formatDateTime(long millis) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(new Date(millis));
    }
}