package com.example.rentacar;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListCarAdapter extends ArrayAdapter<Car> {

    private final Activity context;
    private final Car[] cars;

    public ListCarAdapter(Activity context, Car[] cars) {
        super(context, R.layout.listcar, cars);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.cars = cars;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listcar, null,true);

        TextView modelTextView = (TextView) rowView.findViewById(R.id.modelTextViewList);
        TextView matriculaTextView = (TextView) rowView.findViewById(R.id.matriculaTextViewList);

        modelTextView.setText(cars[position].getModelo());
        matriculaTextView.setText(cars[position].getMatricula());
        return rowView;

    };
}