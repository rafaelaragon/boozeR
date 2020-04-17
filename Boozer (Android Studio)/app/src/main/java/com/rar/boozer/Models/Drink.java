package com.rar.boozer.Models;

import java.io.Serializable;

public class Drink implements Serializable {

    private String name;
    private String type;
    private Double graduation;
    private Double price;
    private String details;
    private String image;


    public Drink(String name, String type, Double graduation, Double price, String details, String image) {
        this.details = details;
        this.graduation = graduation;
        this.image = image;
        this.name = name;
        this.price = price;
        this.type = type;
    }


    public Drink(String name, String image) {
        this.name = name;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getGraduation() {
        return graduation;
    }

    public void setGraduation(Double graduation) {
        this.graduation = graduation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
