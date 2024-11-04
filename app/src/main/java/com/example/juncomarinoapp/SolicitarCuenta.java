package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.juncomarinoapp.modelo.dto.Pedido;

public class SolicitarCuenta extends Fragment {

    private static final String ARG_PEDIDO = "pedido";
    private Pedido pedido;
    private Button btnCrearCuenta, btnContinuarInvitado;

    public SolicitarCuenta() {
        // Required empty public constructor
    }

    public static SolicitarCuenta newInstance(Pedido pedido) {
        SolicitarCuenta fragment = new SolicitarCuenta();
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
        return inflater.inflate(R.layout.fragment_solicitar_cuenta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnContinuarInvitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegistrarPedidoSinCuenta fragmento = RegistrarPedidoSinCuenta.newInstance(pedido);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame1, fragmento)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void enlazarControles(View view){
        btnCrearCuenta = view.findViewById(R.id.btnCrearCuenta);
        btnContinuarInvitado = view.findViewById(R.id.btnContinuarInvitado);
    }

}