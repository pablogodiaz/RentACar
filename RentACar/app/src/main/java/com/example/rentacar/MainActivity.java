package com.example.rentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RentACarDB dbHelper;
    private SQLiteDatabase db;
    private ListView list;
    private String[] columns;
    private EditText filtro;
    private TextView matricula;


    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new RentACarDB(getApplicationContext(), "RentACar.db");
        db = dbHelper.getWritableDatabase();

        columns = new String[]{
                CarContract.CarEntry._ID,
                CarContract.CarEntry.COLUMN_NAME_MODELO,
                CarContract.CarEntry.COLUMN_NAME_MATRICULA,
                CarContract.CarEntry.COLUMN_NAME_COLOR,
                CarContract.CarEntry.COLUMN_NAME_KM
        };
        Cursor cursor  = db.query(CarContract.CarEntry.TABLE_NAME,columns
                , null, null, null, null, null);
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

        Car[] lcar = new Car[carList.size()];
        lcar = carList.toArray(lcar);

        ListCarAdapter adapter=new ListCarAdapter(this,lcar);
        list=(ListView)findViewById(R.id.listCar);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {


                Car car = (Car) list.getItemAtPosition(position);
                String where = CarContract.CarEntry.COLUMN_NAME_MATRICULA + " = ? ";
                String[] whereArgs = {car.getMatricula()};
                Cursor cursor  = db.query(CarContract.CarEntry.TABLE_NAME,columns
                        , where, whereArgs, null, null, null);
                try {
                    while (cursor.moveToNext()) {
                        car.setModelo(cursor.getString(cursor.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_MODELO)));
                        car.setMatricula(cursor.getString(cursor.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_MATRICULA)));
                        car.setColor(cursor.getString(cursor.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_COLOR)));
                        car.setKm(Double.parseDouble(cursor.getString(cursor.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_KM))));
                    }
                } finally {
                    cursor.close();
                }
                Intent intento = new Intent(v.getContext(), EditCar.class);
                intento.putExtra(CarContract.CarEntry.COLUMN_NAME_MATRICULA,car.getMatricula());
                intento.putExtra(CarContract.CarEntry.COLUMN_NAME_MODELO,car.getModelo());
                intento.putExtra(CarContract.CarEntry.COLUMN_NAME_COLOR,car.getColor());
                intento.putExtra(CarContract.CarEntry.COLUMN_NAME_KM,car.getKm());
                startActivity(intento);
            }
        });
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.createButton: createCar(); break;
            case R.id.buscarButton: search(); break;
        }
    }

    private void createCar(){
        Intent intento = new Intent(this, CreateCar.class);
        startActivity(intento);
    }

    @SuppressLint("Range")
    public void search() {
        String where = CarContract.CarEntry.COLUMN_NAME_MATRICULA + " LIKE ? ";
        filtro = (EditText) findViewById(R.id.filtrarText);
        String[] whereArgs = { '%' + filtro.getText().toString().toLowerCase() + '%'};
        Cursor cursor  = db.query(CarContract.CarEntry.TABLE_NAME,columns
                , where, whereArgs, null, null, null);
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

        Car[] lcar = new Car[carList.size()];
        lcar = carList.toArray(lcar);

        ListCarAdapter adapter=new ListCarAdapter(this,lcar);
        list=(ListView)findViewById(R.id.listCar);
        list.setAdapter(adapter);
    }


}