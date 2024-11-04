package com.example.juncomarinoapp.modelo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.juncomarinoapp.interfaces.ConstantesApp;
import com.example.juncomarinoapp.modelo.dto.DetallePedido;
import com.example.juncomarinoapp.servicios.ConectaBDD;

import java.util.ArrayList;

public class DetallePedidoSQLite {
    private SQLiteDatabase bdd;

    public DetallePedidoSQLite(Context context) {
        bdd = new ConectaBDD(
                context,
                ConstantesApp.BDD,
                null,
                ConstantesApp.VERSION
        ).getWritableDatabase();
    }

    public String registrarDetalle(DetallePedido detalle) {
        String rpta = "";
        ContentValues registro = new ContentValues();
        registro.put("idDetalle", detalle.getIdDetalle());
        registro.put("idPedido", detalle.getIdPedido());
        registro.put("idPlatillo", detalle.getIdPlatillo());
        registro.put("nombrePlatillo", detalle.getNombrePlatillo());
        registro.put("cantidad", detalle.getCantidad());
        registro.put("subtotal", detalle.getSubtotal());
        try {
            bdd.insertOrThrow(ConstantesApp.TABLA_DETALLE_PEDIDO, null, registro);
        } catch(Exception ex) {
            rpta = ex.getMessage().toString();
        }
        return rpta;
    }

    public ArrayList<DetallePedido> listarDetalles(int idPedido) {
        ArrayList<DetallePedido> lista = new ArrayList<>();
        String cadSQL = "SELECT idDetalle, idPedido, idPlatillo, nombrePlatillo, cantidad, subtotal FROM DETALLE_PEDIDO WHERE idPedido = " + idPedido;
        Cursor c = bdd.rawQuery(cadSQL, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    DetallePedido detalle = new DetallePedido();
                    detalle.setIdDetalle(c.getInt(c.getColumnIndexOrThrow("idDetalle")));
                    detalle.setIdPedido(c.getInt(c.getColumnIndexOrThrow("idPedido")));
                    detalle.setIdPlatillo(c.getInt(c.getColumnIndexOrThrow("idPlatillo")));
                    detalle.setNombrePlatillo(c.getString(c.getColumnIndexOrThrow("nombrePlatillo")));
                    detalle.setCantidad(c.getInt(c.getColumnIndexOrThrow("cantidad")));
                    detalle.setSubtotal(c.getDouble(c.getColumnIndexOrThrow("subtotal")));
                    lista.add(detalle);
                } while (c.moveToNext());
            }
            c.close();
        }
        return lista;
    }
}
