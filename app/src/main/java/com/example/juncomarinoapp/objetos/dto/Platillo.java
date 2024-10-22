package com.example.juncomarinoapp.objetos.dto;

public class Platillo {
    private String name;
    private String description;
    private String price;
    private int imageResId;

    public Platillo(String name, String description, String price, int imageResId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public int getImageResId() { return imageResId; }
}
