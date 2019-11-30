package com.rar.boozer.Modelos;

import java.io.Serializable;

public class Bebida implements Serializable {

    private String nombre;
    private String tipo;
    private Float graduacion;
    private Float precioLitro;
    private String detalles;
    private String imagen;

    public Bebida(String nombre, String tipo, Float graduacion, Float precioLitro, String detalles, String imagen) {
        this.detalles = detalles;
        this.graduacion = graduacion;
        this.imagen = imagen;
        this.nombre = nombre;
        this.precioLitro = precioLitro;
        this.tipo = tipo;
    }

    public Bebida() {
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

    public float getGraduacion() {
        return graduacion;
    }

    public void setGraduacion(float graduacion) {
        this.graduacion = graduacion;
    }

    public float getPrecioLitro() {
        return precioLitro;
    }

    public void setPrecioLitro(float precioLitro) {
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
