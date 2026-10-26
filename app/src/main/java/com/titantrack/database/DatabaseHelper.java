package com.titantrack.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.titantrack.models.WorkoutRecord;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Esquema
    public static final String DB_NAME = "titantrack.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_HISTORIAL = "tabla_historial";
    public static final String COL_ID = "id";
    public static final String COL_FECHA = "fecha";
    public static final String COL_TOTAL_SERIES = "total_series";
    public static final String COL_VOLUMEN_TOTAL = "volumen_total";

    // Sentencias SQL en crudo
    private static final String SQL_CREATE_HISTORIAL =
            "CREATE TABLE " + TABLE_HISTORIAL + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_FECHA + " TEXT NOT NULL, " +
                    COL_TOTAL_SERIES + " INTEGER NOT NULL, " +
                    COL_VOLUMEN_TOTAL + " REAL NOT NULL)";

    private static final String SQL_INSERT_HISTORIAL =
            "INSERT INTO " + TABLE_HISTORIAL + " (" +
                    COL_FECHA + ", " +
                    COL_TOTAL_SERIES + ", " +
                    COL_VOLUMEN_TOTAL + ") VALUES (?, ?, ?)";

    private static final String SQL_SELECT_HISTORIAL =
            "SELECT " + COL_ID + ", " + COL_FECHA + ", " +
                    COL_TOTAL_SERIES + ", " + COL_VOLUMEN_TOTAL +
                    " FROM " + TABLE_HISTORIAL +
                    " ORDER BY " + COL_ID + " DESC";

    private static final String SQL_DROP_HISTORIAL =
            "DROP TABLE IF EXISTS " + TABLE_HISTORIAL;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_HISTORIAL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_HISTORIAL);
        onCreate(db);
    }

    // Transaccion de insercion: se guarda todo o no se guarda nada
    public void insertarEntrenamiento(String fecha, int totalSeries, double volumenTotal) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.execSQL(SQL_INSERT_HISTORIAL,
                    new Object[]{fecha, totalSeries, volumenTotal});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    // Lectura con Cursor: filas de la tabla convertidas en objetos WorkoutRecord
    public List<WorkoutRecord> obtenerHistorial() {
        List<WorkoutRecord> historial = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.rawQuery(SQL_SELECT_HISTORIAL, null)) {
            int idxId = cursor.getColumnIndexOrThrow(COL_ID);
            int idxFecha = cursor.getColumnIndexOrThrow(COL_FECHA);
            int idxSeries = cursor.getColumnIndexOrThrow(COL_TOTAL_SERIES);
            int idxVolumen = cursor.getColumnIndexOrThrow(COL_VOLUMEN_TOTAL);

            while (cursor.moveToNext()) {
                historial.add(new WorkoutRecord(
                        cursor.getLong(idxId),
                        cursor.getString(idxFecha),
                        cursor.getInt(idxSeries),
                        cursor.getDouble(idxVolumen)));
            }
        }
        return historial;
    }
}