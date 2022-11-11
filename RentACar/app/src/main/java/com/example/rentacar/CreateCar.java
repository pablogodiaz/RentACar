package com.example.rentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateCar extends AppCompatActivity {

    private RentACarDB dbHelper;
    private SQLiteDatabase db;
    private EditText modeloEditText;
    private EditText matriculaEditText;
    private EditText KmEditText;
    private Spinner colorSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_car);
        modeloEditText = (EditText) findViewById(R.id.modeloEditText);
        matriculaEditText = (EditText) findViewById(R.id.matriculaEditText);
        KmEditText = (EditText) findViewById(R.id.KmEditTextNumber);
        colorSpinner = (Spinner) findViewById(R.id.colorEditText);
        dbHelper = new RentACarDB(getApplicationContext(), "RentACar.db");
        db = dbHelper.getWritableDatabase();
    }

    public void onClick(View v){
        createCar();
        Intent intento = new Intent(this, MainActivity.class);
        startActivity(intento);
    }

    private void createCar() {
        // Adición de valores a la BD
        ContentValues values = new ContentValues();
        values.put(CarContract.CarEntry.COLUMN_NAME_MODELO, modeloEditText.getText().toString());
        values.put(CarContract.CarEntry.COLUMN_NAME_MATRICULA, matriculaEditText.getText().toString());
        values.put(CarContract.CarEntry.COLUMN_NAME_COLOR, matriculaEditText.getText().toString());
        values.put(CarContract.CarEntry.COLUMN_NAME_COLOR,colorSpinner.getSelectedItem().toString());
        db.insert(CarContract.CarEntry.TABLE_NAME,null,values);
    }

}