package com.example.rentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
        modeloEditText = (EditText) findViewById(R.id.modeloEditText2);
        matriculaEditText = (EditText) findViewById(R.id.matriculaEditText2);
        KmEditText = (EditText) findViewById(R.id.KmEditTextNumber2);
        colorSpinner = (Spinner) findViewById(R.id.colorEditText2);
        dbHelper = new RentACarDB(getApplicationContext(), "RentACar.db");
        db = dbHelper.getWritableDatabase();
    }

    public void onClick(View v){
        createCar();
    }

    private void createCar() {
        // Adición de valores a la BD
        ContentValues values = new ContentValues();
        String modelo =  modeloEditText.getText().toString().toLowerCase();
        String matricula = matriculaEditText.getText().toString().toLowerCase();
        String km = KmEditText.getText().toString().toLowerCase();

        if(!modelo.equalsIgnoreCase("")){
            values.put(CarContract.CarEntry.COLUMN_NAME_MODELO,modelo);
        }else{
            Toast.makeText(getApplicationContext(),"Introduzca un modelo",Toast.LENGTH_SHORT).show();

        }
        if(!matricula.equalsIgnoreCase("")){
            values.put(CarContract.CarEntry.COLUMN_NAME_MATRICULA,matricula);
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
                && values.get(CarContract.CarEntry.COLUMN_NAME_KM) != null){

            db.insert(CarContract.CarEntry.TABLE_NAME,null,values);
            Intent intento = new Intent(this, MainActivity.class);
            startActivity(intento);

        }
    }

}