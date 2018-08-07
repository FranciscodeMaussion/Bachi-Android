package com.frandemo.bachi;

/**
 * Created by demo on 21/06/17.
 */

public class EntradaBase {

    private String fecha;
    private String profesor;
    private String observaciones;

    public EntradaBase(){

    }

    public EntradaBase(String fecha, String profesor, TareaBase[] tareas, String observaciones) {
        this.fecha = fecha;
        this.profesor = profesor;
        this.observaciones = observaciones;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
