package com.example.pizzadelivery.url;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Url {
    public static final String base_Url = "http://10.0.2.2:3000/";
    public  static String token="Bearer ";
    public static String imagepath = base_Url + "uploads/" ;

 public static Retrofit getInstance() {
     Retrofit retrofit = new Retrofit.Builder()
             .baseUrl(base_Url).addConverterFactory(GsonConverterFactory.create()).build();

     return retrofit;

 }
    }

