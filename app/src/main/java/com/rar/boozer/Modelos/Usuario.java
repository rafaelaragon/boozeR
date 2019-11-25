package com.rar.boozer.Modelos;


import java.io.Serializable;

public class Usuario implements Serializable {


    private String usuario;
    private String email;
    private String preferencias;

    public Usuario(String usuario, String email, String preferencias) {
        this.usuario = usuario;
        this.email = email;
        this.preferencias = preferencias;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPreferencias() {
        return preferencias;
    }

    public void setPreferencias(String preferencias) {
        this.preferencias = preferencias;
    }
}
