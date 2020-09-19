package com.example.pizzadelivery.model;

import java.io.Serializable;

public class Update implements Serializable {
    private String firstName;
    private String lastName;
    private String PhoneNumber;
    private String username;
    private String image;

    public Update() {
    }

    public Update(String firstName, String lastName, String phoneNumber, String username, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        PhoneNumber = phoneNumber;
        this.username = username;
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
