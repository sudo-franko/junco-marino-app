package com.example.juncomarinoapp.servicios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.juncomarinoapp.interfaces.ConstantesApp;

public class ConectaBDD extends SQLiteOpenHelper {

    public ConectaBDD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase bdd) {
        bdd.execSQL(ConstantesApp.TABLA_USUARIO_DDL);
        bdd.execSQL(ConstantesApp.TABLA_PEDIDO_DDL);
        bdd.execSQL(ConstantesApp.TABLA_DETALLE_PEDIDO_DDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
