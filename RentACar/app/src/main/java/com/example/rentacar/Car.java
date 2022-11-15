package com.example.rentacar;

public class Car {
    private String modelo;
    private String matricula;
    private String color;
    private double km;

    public Car(String modelo, String matricula) {
        this.modelo = modelo;
        this.matricula = matricula;
        this.color = "Blanco";
        this.km = 0;
    }

    public Car(String modelo, String matricula, String color, double km) {
        this.modelo = modelo;
        this.matricula = matricula;
        this.color = color;
        this.km = km;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }
}
