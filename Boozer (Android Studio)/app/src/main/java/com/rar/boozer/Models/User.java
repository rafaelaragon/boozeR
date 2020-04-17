package com.rar.boozer.Models;


import java.io.Serializable;

public class User implements Serializable {


    private String user;
    private String email;
    private String preferences;

    public User(String user, String email, String preferences) {
        this.user = user;
        this.email = email;
        this.preferences = preferences;
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

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

}
