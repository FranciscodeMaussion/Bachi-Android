package com.frandemo.bachi;

/**
 * Created by demo on 10/07/17.
 */

public class TareaBase {
    private String nombre;
    private int puntuacion;

    public TareaBase() {
    }

    public TareaBase(String nombre, int puntuacion) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }
}
