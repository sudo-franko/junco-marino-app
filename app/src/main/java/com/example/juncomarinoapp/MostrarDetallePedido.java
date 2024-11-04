package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.juncomarinoapp.modelo.dto.DetallePedido;

import java.util.ArrayList;

public class MostrarDetallePedido extends Fragment {

    public MostrarDetallePedido() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_detalle_pedido, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() instanceof MainActivity2) {
            ArrayList<DetallePedido> pedidos = ((MainActivity2) getActivity()).getPedidos();
            Toast.makeText(getContext(), "Obtenidos los datos...", Toast.LENGTH_LONG).show();
        }
    }
}