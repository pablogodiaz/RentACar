package com.example.rentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.database.sqlite.SQLiteDatabase;
import android.widget.EditText;
import android.widget.ListView;
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
                CarContract.CarEntry.COLUMN_NAME_MATRICULA
        };
        Cursor cursor  = db.query(CarContract.CarEntry.TABLE_NAME,columns
                , null, null, null, null, null);
        List<String> modeloList = new ArrayList();
        List<String> matriculaList = new ArrayList();
        try {
            while (cursor.moveToNext()) {
                modeloList.add(cursor.getString(cursor.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_MODELO)));
                matriculaList.add(cursor.getString(cursor.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_MATRICULA)));
            }
        } finally {
            cursor.close();
        }

        String[] modelo = new String[modeloList.size()];
        modelo = modeloList.toArray(modelo);
        String[] matricula = new String[matriculaList.size()];
        matricula = matriculaList.toArray(matricula);

        ListCarAdapter adapter=new ListCarAdapter(this,modelo ,matricula);
        list=(ListView)findViewById(R.id.listCar);
        list.setAdapter(adapter);
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
        List<String> modeloList = new ArrayList();
        List<String> matriculaList = new ArrayList();
        try {
            while (cursor.moveToNext()) {
                modeloList.add(cursor.getString(cursor.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_MODELO)));
                matriculaList.add(cursor.getString(cursor.getColumnIndex(CarContract.CarEntry.COLUMN_NAME_MATRICULA)));
            }
        } finally {
            cursor.close();
        }

        String[] modelo = new String[modeloList.size()];
        modelo = modeloList.toArray(modelo);
        String[] matricula = new String[matriculaList.size()];
        matricula = matriculaList.toArray(matricula);

        ListCarAdapter adapter=new ListCarAdapter(this,modelo ,matricula);
        list=(ListView)findViewById(R.id.listCar);
        list.setAdapter(adapter);
    }


}