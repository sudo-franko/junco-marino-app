package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juncomarinoapp.modelo.dto.Pedido;

public class RegistrarPedidoConCuenta extends Fragment {

    private static final String ARG_PEDIDO = "pedido";
    private static final String ARG_MEDIO = "medioPedido";
    private Pedido pedido;
    private boolean enMedioPedido;

    public RegistrarPedidoConCuenta() {
        // Required empty public constructor
    }

    public static RegistrarPedidoConCuenta newInstance(Pedido pedido) {
        RegistrarPedidoConCuenta fragment = new RegistrarPedidoConCuenta();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PEDIDO, pedido);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pedido = (Pedido) getArguments().getSerializable(ARG_PEDIDO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrar_pedido_con_cuenta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}