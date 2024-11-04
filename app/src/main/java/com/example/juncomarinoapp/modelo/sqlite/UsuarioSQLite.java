package com.example.juncomarinoapp.modelo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.juncomarinoapp.interfaces.ConstantesApp;
import com.example.juncomarinoapp.modelo.dto.Usuario;
import com.example.juncomarinoapp.servicios.ConectaBDD;

public class UsuarioSQLite {

    private SQLiteDatabase bdd;

    public UsuarioSQLite(Context context) {
        bdd = new ConectaBDD(
                context,
                ConstantesApp.BDD,
                null,
                ConstantesApp.VERSION
        ).getWritableDatabase();
    }

    public String registrarUsuarioSQLite(Usuario u) {
        String rpta = "";
        ContentValues registro = new ContentValues();
        registro.put("idCliente", u.getIdCliente());
        registro.put("nombres", u.getNombres());
        registro.put("apellidos", u.getApellidos());
        registro.put("correo", u.getCorreo());
        registro.put("telefono", u.getTelefono());
        registro.put("direccion", u.getDireccion());
        registro.put("usuario", u.getUsuario());
        registro.put("contrasena", u.getContrasena());
        try {
            bdd.insertOrThrow(ConstantesApp.TABLA_USUARIO, null, registro);
        } catch(SQLException ex) {
            rpta = ex.getMessage().toString();
        }
        return rpta;
    }

    public String actualizarUsuarioSQLite(Usuario u) {
        String rpta = "";
        ContentValues registro = new ContentValues();
        registro.put("idCliente", u.getIdCliente());
        registro.put("nombres", u.getNombres());
        registro.put("apellidos", u.getApellidos());
        registro.put("correo", u.getCorreo());
        registro.put("telefono", u.getTelefono());
        registro.put("direccion", u.getDireccion());
        registro.put("usuario", u.getUsuario());
        registro.put("contrasena", u.getContrasena());
        String condicion = "idCliente = " + u.getIdCliente();
        try {
            int filas = bdd.update(ConstantesApp.TABLA_USUARIO, registro, condicion, null);
            if (filas == 0 || filas > 1)
                rpta = "No se pudo actualizar el registro";
        } catch(SQLException ex) {
            rpta = ex.getMessage().toString();
        }
        return rpta;
    }

    public Usuario obtenerUsuarioSQLite() {
        Usuario u = new Usuario();
        String cadSQL = "SELECT * FROM USUARIO";
        Cursor c = bdd.rawQuery(cadSQL, null);
        u.setIdCliente(c.getInt(c.getColumnIndexOrThrow("idCliente")));
        u.setNombres(c.getString(c.getColumnIndexOrThrow("nombres")));
        u.setApellidos(c.getString(c.getColumnIndexOrThrow("apellidos")));
        u.setCorreo(c.getString(c.getColumnIndexOrThrow("correo")));
        u.setTelefono(c.getString(c.getColumnIndexOrThrow("telefono")));
        u.setDireccion(c.getString(c.getColumnIndexOrThrow("direccion")));
        u.setUsuario(c.getString(c.getColumnIndexOrThrow("usuario")));
        u.setContrasena(c.getString(c.getColumnIndexOrThrow("contrasena")));
        c.close();
        return u;
    }
}
