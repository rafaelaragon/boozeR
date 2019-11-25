package com.rar.boozer.Modelos;

import java.io.Serializable;

public class Bebida implements Serializable {

    private String nombre;
    private String tipo;
    private int graduacion;
    private int precioLitro;
    private String detalles;
    private String imagen;

    public Bebida(String nombre, String tipo, int graduacion, int precioLitro, String detalles, String imagen) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.graduacion = graduacion;
        this.precioLitro = precioLitro;
        this.detalles = detalles;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getGraduacion() {
        return graduacion;
    }

    public void setGraduacion(int graduacion) {
        this.graduacion = graduacion;
    }

    public int getPrecioLitro() {
        return precioLitro;
    }

    public void setPrecioLitro(int precioLitro) {
        this.precioLitro = precioLitro;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Bebida{" +
                "nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", graduacion=" + graduacion +
                ", precioLitro=" + precioLitro +
                ", detalles='" + detalles + '\'' +
                ", imagen='" + imagen + '\'' +
                '}';
    }
}
