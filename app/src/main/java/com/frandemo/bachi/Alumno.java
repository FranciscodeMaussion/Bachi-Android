package com.frandemo.bachi;

/**
 * Created by demo on 08/05/17.
 */


public class Alumno {

    private String nombre;
    private String fecha;
    private int escolar = 0;

    public Alumno() {

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEscolar() {
        return escolar;
    }

    public void setEscolar(int escolar) {
        this.escolar = escolar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
