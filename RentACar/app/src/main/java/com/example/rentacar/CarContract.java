package com.example.rentacar;
import android.provider.BaseColumns;

public final class CarContract {
    private CarContract() {}

    public static abstract class CarEntry implements BaseColumns {
        public static final String TABLE_NAME = "Car";

        public static final String COLUMN_NAME_MODELO = "modelo";
        public static final String COLUMN_NAME_MATRICULA = "matricula";
        public static final String COLUMN_NAME_COLOR = "color";
        public static final String COLUMN_NAME_KM = "km";
    }
}
