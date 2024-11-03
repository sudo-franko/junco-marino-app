package com.example.juncomarinoapp.modelo.dto;

public class Platillo {
    private int idPlatillo;
    private String name;
    private String description;
    private String price;
    private int idCategoria;
    private String categoria;

    public Platillo(int idPlatillo, String name, String description, String price,
                    int idCategoria, String categoria) {
        this.idPlatillo = idPlatillo;
        this.name = name;
        this.description = description;
        this.price = price;
        this.idCategoria = idCategoria;
        this.categoria = categoria;
    }

    public int getIdPlatillo(){ return idPlatillo; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public int getIdCategoria(){ return idCategoria; }
    public String getCategoria(){ return categoria; }

}
