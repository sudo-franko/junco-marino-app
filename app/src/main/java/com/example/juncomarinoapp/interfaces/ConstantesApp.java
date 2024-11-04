package com.example.juncomarinoapp.interfaces;

public interface ConstantesApp {
    // CONSTANTES API
    String URL_GENERAL = "http://192.168.1.37:4000/";

    // CONSTANTES SQLITE
    String TABLA_USUARIO = "USUARIO";
    String TABLA_PEDIDO = "PEDIDO";
    String TABLA_DETALLE_PEDIDO = "DETALLE_PEDIDO";
    String BDD = "juncoMarino.db";
    int VERSION = 1;
    String TABLA_USUARIO_DDL = "CREATE TABLE USUARIO (\n" +
            "\tidCliente INTEGER PRIMARY KEY,\n" +
            "\tnombres VARCHAR(100) NOT NULL,\n" +
            "\tapellidos VARCHAR(100) NOT NULL,\n" +
            "\tcorreo VARCHAR(100) NOT NULL,\n" +
            "\ttelefono VARCHAR(15) NOT NULL,\n" +
            "\tdireccion VARCHAR(255) NOT NULL,\n" +
            "\tusuario VARCHAR(100) NOT NULL,\n" +
            "\tcontrasena VARCHAR(20) NOT NULL\n" +
            ");\n";
    String TABLA_PEDIDO_DDL = "CREATE TABLE PEDIDO (\n" +
            "\tidPedido INTEGER PRIMARY KEY,\n" +
            "\tidCliente INTEGER NULL,\n" +
            "\tnombreCliente VARCHAR(100) NULL,\n" +
            "\ttelefonoCliente VARCHAR(15) NULL,\n" +
            "\tdireccionCliente VARCHAR(255) NULL,\n" +
            "\tnotas VARCHAR(150) NOT NULL,\n" +
            "\ttipoEntrega VARCHAR(30) NOT NULL,\n" +
            "\ttipoPago VARCHAR(30) NOT NULL,\n" +
            "\tfecha TEXT NOT NULL,\n" +
            "\tmonto DECIMAL(10, 2) NOT NULL,\n" +
            "\testado VARCHAR(30) NOT NULL,\n" +
            "\tcalificacion VARCHAR(1) NOT NULL,\n" +
            "\tcomentario VARCHAR(255) NOT NULL\n" +
            ");\n";
    String TABLA_DETALLE_PEDIDO_DDL = "CREATE TABLE DETALLE_PEDIDO (\n" +
            "\tidDetalle INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "\tidPedido INTEGER,\n" +
            "\tidPlatillo INTEGER,\n" +
            "\tnombrePlatillo VARCHAR(100) NOT NULL,\n" +
            "\tcantidad INTEGER NOT NULL,\n" +
            "\tsubtotal DECIMAL(10, 2) NOT NULL\n" +
            ");\n";
}
