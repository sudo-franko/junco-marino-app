package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MostrarPedidoFinalizado extends Fragment {

    private TextView tvNumPedido;
    private Button btnVerEstadoPedido;
    private static final String ARG_ID_PEDIDO = "idPedido";
    private int idPedido;

    public static MostrarPedidoFinalizado newInstance(int idPedido) {
        MostrarPedidoFinalizado fragment = new MostrarPedidoFinalizado();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_PEDIDO, idPedido);
        fragment.setArguments(args);
        return fragment;
    }

    public MostrarPedidoFinalizado() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPedido = getArguments().getInt(ARG_ID_PEDIDO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_pedido_finalizado, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.frame1, new MostrarMenu())
                        .commit();
            }
        });
        tvNumPedido = view.findViewById(R.id.tvNumPedido);
        btnVerEstadoPedido = view.findViewById(R.id.btnVerEstadoPedido);
        tvNumPedido.setText("Pedido N° " + idPedido);
        btnVerEstadoPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.frame1, new GestionarPedidos())
                        .commit();
            }
        });
    }

}