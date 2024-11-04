package com.example.juncomarinoapp.modelo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.juncomarinoapp.interfaces.ConstantesApp;
import com.example.juncomarinoapp.modelo.dto.Pedido;
import com.example.juncomarinoapp.servicios.ConectaBDD;

import java.util.ArrayList;

public class PedidoSQLite {
    private SQLiteDatabase bdd;

    public PedidoSQLite(Context context) {
        bdd = new ConectaBDD(
                context,
                ConstantesApp.BDD,
                null,
                ConstantesApp.VERSION
        ).getWritableDatabase();
    }


    public String registrarPedido(Pedido p) {
        String rpta = "";
        ContentValues registro = new ContentValues();
        registro.put("idPedido", p.getIdPedido());
        registro.put("idCliente", p.getIdCliente());
        registro.put("nombreCliente", p.getNombreCliente());
        registro.put("telefonoCliente", p.getTelefonoCliente());
        registro.put("direccionCliente", p.getDireccionCliente());
        registro.put("notas", p.getNotas());
        registro.put("tipoEntrega", p.getTipoEntrega());
        registro.put("tipoPago", p.getTipoPago());
        registro.put("fecha", p.getFecha());
        registro.put("monto", p.getMonto());
        registro.put("estado", p.getEstado());
        registro.put("calificacion", p.getCalificacion());
        registro.put("comentario", p.getComentario());
        try {
            bdd.insertOrThrow(ConstantesApp.TABLA_PEDIDO, null, registro);
        } catch(Exception ex) {
            rpta = ex.getMessage().toString();
        }
        return rpta;
    }

    public String actualizarPedido(Pedido p) {
        String rpta = "";
        ContentValues registro = new ContentValues();
        registro.put("idPedido", p.getIdPedido());
        registro.put("idCliente", p.getIdCliente());
        registro.put("nombreCliente", p.getNombreCliente());
        registro.put("telefonoCliente", p.getTelefonoCliente());
        registro.put("direccionCliente", p.getDireccionCliente());
        registro.put("notas", p.getNotas());
        registro.put("tipoEntrega", p.getTipoEntrega());
        registro.put("tipoPago", p.getTipoPago());
        registro.put("fecha", p.getFecha());
        registro.put("monto", p.getMonto());
        registro.put("estado", p.getEstado());
        registro.put("calificacion", p.getCalificacion());
        registro.put("comentario", p.getComentario());
        String condicion = "idPedido = " + p.getIdPedido();
        try {
            int filas = bdd.update(ConstantesApp.TABLA_PEDIDO, registro, condicion, null);
            if (filas == 0 || filas > 1)
                rpta = "No se pudo actualizar el registro";
        } catch(Exception ex) {
            rpta = ex.getMessage().toString();
        }
        return rpta;
    }

    public Pedido buscarPedido(int idPedido) {
        Pedido p = new Pedido();
        p.setIdPedido(-1);
        String cadSQL = "SELECT idPedido, idCliente, nombreCliente, telefonoCliente, direccionCliente, notas, tipoEntrega, tipoPago, fecha, monto, estado, calificacion, comentario FROM pedido WHERE idPedido = " + idPedido;
        Cursor c = bdd.rawQuery(cadSQL, null);
        if (c != null) {
            if (c.moveToFirst()) {
                p.setIdPedido(c.getInt(c.getColumnIndexOrThrow("idPedido")));
                p.setIdCliente(c.getInt(c.getColumnIndexOrThrow("idCliente")));
                p.setNombreCliente(c.getString(c.getColumnIndexOrThrow("nombreCliente")));
                p.setTelefonoCliente(c.getString(c.getColumnIndexOrThrow("telefonoCliente")));
                p.setDireccionCliente(c.getString(c.getColumnIndexOrThrow("direccionCliente")));
                p.setNotas(c.getString(c.getColumnIndexOrThrow("notas")));
                p.setTipoEntrega(c.getString(c.getColumnIndexOrThrow("tipoEntrega")));
                p.setTipoPago(c.getString(c.getColumnIndexOrThrow("tipoPago")));
                p.setFecha(c.getString(c.getColumnIndexOrThrow("fecha")));
                p.setMonto(c.getDouble(c.getColumnIndexOrThrow("monto")));
                p.setEstado(c.getString(c.getColumnIndexOrThrow("estado")));
                p.setCalificacion(c.getString(c.getColumnIndexOrThrow("calificacion")));
                p.setComentario(c.getString(c.getColumnIndexOrThrow("comentario")));
            }
            c.close();
        }
        return p;
    }

    public ArrayList<Pedido> listarPedidos() {
        ArrayList<Pedido> lista = new ArrayList<>();
        String cadSQL = "SELECT * FROM PEDIDO";
        Cursor c = bdd.rawQuery(cadSQL, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Pedido p = new Pedido();
                    p.setIdPedido(c.getInt(c.getColumnIndexOrThrow("idPedido")));
                    p.setIdCliente(c.getInt(c.getColumnIndexOrThrow("idCliente")));
                    p.setNombreCliente(c.getString(c.getColumnIndexOrThrow("nombreCliente")));
                    p.setTelefonoCliente(c.getString(c.getColumnIndexOrThrow("telefonoCliente")));
                    p.setDireccionCliente(c.getString(c.getColumnIndexOrThrow("direccionCliente")));
                    p.setNotas(c.getString(c.getColumnIndexOrThrow("notas")));
                    p.setTipoEntrega(c.getString(c.getColumnIndexOrThrow("tipoEntrega")));
                    p.setTipoPago(c.getString(c.getColumnIndexOrThrow("tipoPago")));
                    p.setFecha(c.getString(c.getColumnIndexOrThrow("fecha")));
                    p.setMonto(c.getDouble(c.getColumnIndexOrThrow("monto")));
                    p.setEstado(c.getString(c.getColumnIndexOrThrow("estado")));
                    p.setCalificacion(c.getString(c.getColumnIndexOrThrow("calificacion")));
                    p.setComentario(c.getString(c.getColumnIndexOrThrow("comentario")));
                    lista.add(p);
                } while (c.moveToNext());
            }
            c.close();
        }
        return lista;
    }
}
