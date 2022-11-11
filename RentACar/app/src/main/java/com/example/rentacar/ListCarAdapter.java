package com.example.rentacar;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListCarAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] modelo;
    private final String[] matricula;

    public ListCarAdapter(Activity context, String[] modelo,String[] matricula) {
        super(context, R.layout.listcar, modelo);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.modelo = modelo;
        this.matricula = matricula;

    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listcar, null,true);

        TextView modelTextView = (TextView) rowView.findViewById(R.id.modelTextViewList);
        TextView matriculaTextView = (TextView) rowView.findViewById(R.id.matriculaTextViewList);

        modelTextView.setText(modelo[position]);
        matriculaTextView.setText(matricula[position]);
        return rowView;

    };
}