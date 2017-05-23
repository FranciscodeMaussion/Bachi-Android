package com.frandemo.bachi;

/**
 * Created by demo on 16/05/17.
 */

public class Profesor {
    private String email;
    private String fecha;
    private String nombre;
    private Long telefono;
    private int aprobado;


    public Profesor() {

    }

    public Profesor(String email, String fecha, String nombre, Long telefono, int aprobado) {
        this.email = email;
        this.fecha = fecha;
        this.nombre = nombre;
        this.telefono = telefono;
        this.aprobado = aprobado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public int getAprobado() {
        return aprobado;
    }

    public void setAprobado(int aprobado) {
        this.aprobado = aprobado;
    }
}
