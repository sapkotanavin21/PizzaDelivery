package com.example.pizzadelivery.model;

public class LargePizza {
    private String name;
    private String location;
    private String price;
    private String image;
    private String food;

    public LargePizza(String name, String location, String price, String image, String food) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.image = image;
        this.food = food;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}