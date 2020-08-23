package com.example.pizzadelivery.model;


import java.io.Serializable;

public class User implements Serializable {

    private String firstName;
    private String lastName;
    private String PhoneNumber;
    private String username;
    private String password;
    private String image;

    public User() {
    }

    public User(String firstName, String lastName, String phoneNumber, String username, String password, String image) {
        this.firstName = firstName;
        this.lastName = lastName;
        PhoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}