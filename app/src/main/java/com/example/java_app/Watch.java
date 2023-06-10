package com.example.java_app;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Watch implements Serializable {
    private String name;
    private String description;
    private double price;
    private String brand;
    private String movementType;
    private String functionType;
    private List<Integer> imageLinks;
    public boolean favorite;
    public double searches;

    // Constructor
    public Watch(String name, String description, double price, String brand, String movementType, String functionType, List<Integer> imageLinks, boolean favorite, Double searches) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.brand = brand;
        this.movementType = movementType;
        this.functionType = functionType;
        this.imageLinks = imageLinks;
        this.favorite = favorite;
        this.searches = searches;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getBrand() {
        return brand;
    }

    public String getMovementType() {
        return movementType;
    }

    public String getFunctionType() {return functionType;}

    public boolean getFavorite() { return favorite;}

    public double getSearches() { return searches;}

    public List<Integer> getImageLinks() {
        return imageLinks;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setFavorite(boolean favorite) {this.favorite = favorite; }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    public void setImageLinks(List<Integer> imageLinks) {
        this.imageLinks = imageLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Watch watch = (Watch) o;
        return Objects.equals(getName(), watch.getName()) && Objects.equals(getBrand(), watch.getBrand());
    }
}
