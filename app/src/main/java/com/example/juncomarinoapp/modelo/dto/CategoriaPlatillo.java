package com.example.juncomarinoapp.modelo.dto;

public class CategoriaPlatillo {
    private int idCategoria;
    private String categoria;
    private String urlImagen;

    public CategoriaPlatillo() {
    }

    public CategoriaPlatillo(int idCategoria, String categoria, String urlImagen) {
        this.idCategoria = idCategoria;
        this.categoria = categoria;
        this.urlImagen = urlImagen;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
