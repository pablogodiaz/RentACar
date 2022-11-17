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

import java.util.Locale;
import java.util.Scanner;

public class CreateCar extends AppCompatActivity {

    private SQLiteDatabase db;
    private EditText modeloEditText;
    private EditText matriculaEditText;
    private EditText KmEditText;
    private Spinner colorSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_car);

        // Obtenemos la base de datos
        RentACarDB dbHelper = new RentACarDB(getApplicationContext(), "RentACar.db");
        db = dbHelper.getWritableDatabase();

        // Obtenemos los componentes
        modeloEditText = (EditText) findViewById(R.id.modeloEditText2);
        matriculaEditText = (EditText) findViewById(R.id.matriculaEditText2);
        KmEditText = (EditText) findViewById(R.id.KmEditTextNumber2);
        colorSpinner = (Spinner) findViewById(R.id.colorEditText2);

    }

    public void onClick(View v){
        createCar();
    }

    private void createCar() {

        // Obtenemos el valor de los componentes
        String modelo =  modeloEditText.getText().toString();
        String matricula = matriculaEditText.getText().toString().toUpperCase();
        String km = KmEditText.getText().toString();

        // Introducimos los valores si se han introducido correctamente
        // en caso contrario mostramos un mensaje de error con el Toast
        ContentValues values = new ContentValues();

        //MODELO
        if(!modelo.equalsIgnoreCase("")){
            values.put(CarContract.CarEntry.COLUMN_NAME_MODELO,firstLetterUppercase(modelo));
        }else{
            Toast.makeText(getApplicationContext(),R.string.toastModel,Toast.LENGTH_SHORT).show();
        }

        //MATRICULA
        if(!matricula.equalsIgnoreCase("")){
            values.put(CarContract.CarEntry.COLUMN_NAME_MATRICULA,matricula);
        }else{
            Toast.makeText(getApplicationContext(),R.string.toastLicense,Toast.LENGTH_SHORT).show();
        }

        //KM
        try{
            Double.parseDouble(km);
            values.put(CarContract.CarEntry.COLUMN_NAME_KM,km);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),R.string.toastKilometer,Toast.LENGTH_SHORT).show();
        }

        //Introducimos los valores
        values.put(CarContract.CarEntry.COLUMN_NAME_COLOR,colorSpinner.getSelectedItem().toString());

        // Comprobamos que todos los campos se han introducido correctamente
        if(values.get(CarContract.CarEntry.COLUMN_NAME_MODELO) != null
                && values.get(CarContract.CarEntry.COLUMN_NAME_MATRICULA) != null
                && values.get(CarContract.CarEntry.COLUMN_NAME_KM) != null){

            //Insertamos los valores en la BD
            db.insert(CarContract.CarEntry.TABLE_NAME,null,values);

            // Volvemos a la vista principal
            volver();

        }

    }

    // Método que permite volver a la actividad principal
    private void volver(){
        Intent intento = new Intent(this,MainActivity.class);
        startActivity(intento);
    }

    private String firstLetterUppercase(String s) {
        Scanner sc = new Scanner(s);
        sc.useDelimiter(" ");
        String result = "";
        while(sc.hasNext()) {
            String aux = sc.next();
            aux = aux.substring(0, 1).toUpperCase() + aux.substring(1);
            result += aux + " ";
        }
        sc.close();
        return result;
    }
}