package com.example.rentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Scanner;

public class EditCar extends AppCompatActivity {

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

        // Obtenemos la base de datos
        RentACarDB dbHelper = new RentACarDB(getApplicationContext(), "RentACar.db");
        db = dbHelper.getWritableDatabase();

        // Obtenemos los componentes
        modeloEditText = (EditText) findViewById(R.id.modeloEditText2);
        matriculaEditText = (EditText) findViewById(R.id.matriculaEditText2);
        kmEditText = (EditText) findViewById(R.id.KmEditTextNumber2);
        colorSpinner = (Spinner) findViewById(R.id.colorEditText2);

        //Obtenemos la información del coche seleccionado
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        matricula = extras.get(CarContract.CarEntry.COLUMN_NAME_MATRICULA).toString();
        String modelo = extras.get(CarContract.CarEntry.COLUMN_NAME_MODELO).toString();
        String km = extras.get(CarContract.CarEntry.COLUMN_NAME_KM).toString();
        String color = extras.get(CarContract.CarEntry.COLUMN_NAME_COLOR).toString();

        // Rellenamos los campos con la información obtenida
        modeloEditText.setText(modelo);
        matriculaEditText.setText(matricula);
        kmEditText.setText(km);
        colorSpinner.setSelection(getIndex(colorSpinner, color));

    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.editButton: editCar(); break;
            case R.id.deleteButton: deleteCar(); break;
        }
    }

    private void editCar() {

        // Obtenemos el valor de los componentes
        String modelo = modeloEditText.getText().toString();
        String km = kmEditText.getText().toString();
        String newMatricula = matriculaEditText.getText().toString().toUpperCase();

        // Establecemos la condición where de la sentencia
        String where = CarContract.CarEntry.COLUMN_NAME_MATRICULA + " = ?";
        String[] whereArgs = {matricula};

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
        if(!newMatricula.equalsIgnoreCase("")){
            values.put(CarContract.CarEntry.COLUMN_NAME_MATRICULA,newMatricula);
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
                && values.get(CarContract.CarEntry.COLUMN_NAME_KM) != null) {

            // Actualizamos el coche en la BD
            db.update(CarContract.CarEntry.TABLE_NAME, values, where, whereArgs);

            // Volvemos a la vista principal
            volver();
        }
    }

    @SuppressLint("Range")
    private void deleteCar() {

        //Obtenemos el valor de la matricula
        String matricula = matriculaEditText.getText().toString().toUpperCase();

        // Establecemos la condición where de la sentencia
        String where = CarContract.CarEntry.COLUMN_NAME_MATRICULA + " = ?";
        String[] whereArgs = {matricula};

        // Borramos el coche de la BD
        db.delete(CarContract.CarEntry.TABLE_NAME,where,whereArgs);

        // Volvemos a la actividad principal
        volver();
    }

    // Método que permite volver a la actividad principal
    private void volver(){
        Intent intento = new Intent(this,MainActivity.class);
        startActivity(intento);
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
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