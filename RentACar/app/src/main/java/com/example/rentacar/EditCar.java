package com.example.rentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditCar extends AppCompatActivity {

    private RentACarDB dbHelper;
    private SQLiteDatabase db;
    private EditText modeloEditText;
    private EditText matriculaEditText;
    private EditText kmEditText;
    private Spinner colorSpinner;
    private String matricula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car);
        modeloEditText = (EditText) findViewById(R.id.modeloEditText2);
        matriculaEditText = (EditText) findViewById(R.id.matriculaEditText2);
        kmEditText = (EditText) findViewById(R.id.KmEditTextNumber2);
        colorSpinner = (Spinner) findViewById(R.id.colorEditText2);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        matricula = extras.get(CarContract.CarEntry.COLUMN_NAME_MATRICULA).toString();
        String modelo = extras.get(CarContract.CarEntry.COLUMN_NAME_MODELO).toString();
        String color = extras.get(CarContract.CarEntry.COLUMN_NAME_COLOR).toString();
        String km = extras.get(CarContract.CarEntry.COLUMN_NAME_KM).toString();

        modeloEditText.setText(modelo);
        matriculaEditText.setText(matricula);
        kmEditText.setText(km);

        dbHelper = new RentACarDB(getApplicationContext(), "RentACar.db");
        db = dbHelper.getWritableDatabase();

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.editButton: editCar(); break;
            case R.id.deleteButton: deleteCar(); break;
        }
    }

    private void editCar() {
        // Editar valores de la BD
        ContentValues values = new ContentValues();
        String modelo = modeloEditText.getText().toString().toLowerCase();
        String km = kmEditText.getText().toString().toLowerCase();
        String newMatricula = matriculaEditText.getText().toString().toLowerCase();
        String where = CarContract.CarEntry.COLUMN_NAME_MATRICULA + " = ?";
        String[] whereArgs = {matricula};

        if(!modelo.equalsIgnoreCase("")){
            values.put(CarContract.CarEntry.COLUMN_NAME_MODELO,modelo);
        }else{
            Toast.makeText(getApplicationContext(),"Introduzca un modelo",Toast.LENGTH_SHORT).show();

        }
        if(!newMatricula.equalsIgnoreCase("")){
            values.put(CarContract.CarEntry.COLUMN_NAME_MATRICULA,newMatricula);
        }else{
            Toast.makeText(getApplicationContext(),"Introduzca una matricula",Toast.LENGTH_SHORT).show();
        }
        try{
            Double.parseDouble(km);
            values.put(CarContract.CarEntry.COLUMN_NAME_KM,km);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Introduzca un kilometraje",Toast.LENGTH_SHORT).show();
        }
        values.put(CarContract.CarEntry.COLUMN_NAME_COLOR,colorSpinner.getSelectedItem().toString());

        if(values.get(CarContract.CarEntry.COLUMN_NAME_MODELO) != null
                && values.get(CarContract.CarEntry.COLUMN_NAME_MATRICULA) != null
                && values.get(CarContract.CarEntry.COLUMN_NAME_KM) != null) {
            db.update(CarContract.CarEntry.TABLE_NAME, values, where, whereArgs);
        }
            Intent intento = new Intent(this, MainActivity.class);
            startActivity(intento);

    }

    @SuppressLint("Range")
    private void deleteCar() {
        String where = CarContract.CarEntry.COLUMN_NAME_MODELO + " = ?";
        String modelo = modeloEditText.getText().toString().toLowerCase();
        String[] whereArgs = {modelo};
        db.delete(CarContract.CarEntry.TABLE_NAME,where,whereArgs);
        Intent intento = new Intent(this,MainActivity.class);
        startActivity(intento);
    }

}