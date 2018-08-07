package com.frandemo.bachi;

/**
 * Created by demo on 08/05/17.
 */


public class AlumnoBase {

    private String nombre;
    private String fecha;
    private int escolar = 0;

    public AlumnoBase() {

    }

    public AlumnoBase(String nombre, String fecha, int escolar) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.escolar = escolar;
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
