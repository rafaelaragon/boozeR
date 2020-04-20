package com.rar.boozer.Models;


import java.io.Serializable;

public class User implements Serializable {


    private String user;
    private String email;
    private Boolean isAdmin;

    public User(String user, String email, Boolean isAdmin) {
        this.user = user;
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public User() {
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
