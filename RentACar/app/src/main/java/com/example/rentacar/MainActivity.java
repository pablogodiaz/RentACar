// Pablo Gonzalez Diaz y Alfonso Gonzalez Ortiz
package com.example.rentacar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private ListView list;
    private String[] columns;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtenemos la base de datos
        RentACarDB dbHelper = new RentACarDB(getApplicationContext(), "RentACar.db");
        db = dbHelper.getWritableDatabase();

        // Introducimos las columnas de la tabla Car
        columns = new String[]{
                CarContract.CarEntry._ID,
                CarContract.CarEntry.COLUMN_NAME_MODELO,
                CarContract.CarEntry.COLUMN_NAME_MATRICULA,
                CarContract.CarEntry.COLUMN_NAME_COLOR,
                CarContract.CarEntry.COLUMN_NAME_KM
        };

        // Obtenemos todos los coches de la bd
        Cursor cursor  = db.query(CarContract.CarEntry.TABLE_NAME,columns
                , null, null, null, null, null);

        obtenerListaCoches(cursor);

        // Creamos un ItemClickListener para poder seleccionar los coches
        list.setOnItemClickListener((adapter1, v, position, id) -> {

            onItemClickListener(position,v);
        });
    }

    @SuppressLint("Range")
    private void onItemClickListener(int position,View v) {
        // Obtenemos el coche seleccionado
        Car car = (Car) list.getItemAtPosition(position);

        // Establecemos la condición where de la sentencia
        String where = CarContract.CarEntry.COLUMN_NAME_MATRICULA + " = ? ";
        String[] whereArgs = {car.getMatricula()};

        // Extraemos todos los parametros del cursor
        try (Cursor cursor1 = db.query(CarContract.CarEntry.TABLE_NAME, columns
                , where, whereArgs, null, null, null)) {
            while (cursor1.moveToNext()) {
                car.setModelo(cursor1.getString(cursor1.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_MODELO)));
                car.setMatricula(cursor1.getString(cursor1.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_MATRICULA)));
                car.setColor(cursor1.getString(cursor1.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_COLOR)));
                car.setKm(Double.parseDouble(cursor1.getString(cursor1.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_KM))));
            }
        }

        // Llamamos a la actividad editarCar pasandole los datos como parámetros
        Intent intento = new Intent(v.getContext(), EditCar.class);
        intento.putExtra(CarContract.CarEntry.COLUMN_NAME_MATRICULA,car.getMatricula());
        intento.putExtra(CarContract.CarEntry.COLUMN_NAME_MODELO,car.getModelo());
        intento.putExtra(CarContract.CarEntry.COLUMN_NAME_COLOR,car.getColor());
        intento.putExtra(CarContract.CarEntry.COLUMN_NAME_KM,car.getKm());
        startActivity(intento);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    @SuppressLint("NonConstantResourceId")
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.createButton: createCar(); break;
            case R.id.buscarButton: search(); break;
        }
    }

    // Método que te lleva a la actividad crear coche
    private void createCar(){
        Intent intento = new Intent(this, CreateCar.class);
        startActivity(intento);
    }

    public void search() {
        // Obtenemos el valor del filtro
        EditText filtro = (EditText) findViewById(R.id.filtrarText);

        // Establecemos la condición where de la sentencia
        String where = CarContract.CarEntry.COLUMN_NAME_MATRICULA + " LIKE ? ";
        String[] whereArgs = { '%' + filtro.getText().toString().toLowerCase() + '%'};

        // Extraemos todos los parametros del cursor
        Cursor cursor  = db.query(CarContract.CarEntry.TABLE_NAME,columns
                , where, whereArgs, null, null, null);

        obtenerListaCoches(cursor);
    }

    @SuppressLint("Range")
    private void obtenerListaCoches(Cursor cursor) {

        // Extraemos todos los parametros del cursor
        List<Car> carList = new ArrayList();
        try {
            while (cursor.moveToNext()) {
                String modelo = cursor.getString(cursor.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_MODELO));
                String matricula = cursor.getString(cursor.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_MATRICULA));
                carList.add(new Car(modelo,matricula));
            }
        } finally {
            cursor.close();
        }

        // Pasamos la lista de coches a un array
        Car[] lcar = new Car[carList.size()];
        lcar = carList.toArray(lcar);

        // Llamamos al adapter con nuestro array de coches
        ListCarAdapter adapter=new ListCarAdapter(this,lcar);
        list=(ListView)findViewById(R.id.listCar);
        list.setAdapter(adapter);
    }


}