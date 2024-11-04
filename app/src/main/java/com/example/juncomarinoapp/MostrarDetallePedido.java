package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juncomarinoapp.adapters.DetallePedidoAdapter;
import com.example.juncomarinoapp.modelo.dto.DetallePedido;

import java.util.ArrayList;

public class MostrarDetallePedido extends Fragment {

    private ListView lvPedidos;
    private TextView tvMontoTotal;
    private DetallePedidoAdapter adapter;
    private ArrayList<DetallePedido> listaPedidos;

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
        enlazarControles(view);
        if (getActivity() instanceof MainActivity2) {
            listaPedidos = ((MainActivity2) getActivity()).getPedidos();
            adapter = new DetallePedidoAdapter(getContext(), listaPedidos);
            lvPedidos.setAdapter(adapter);
            double montoTotal = 0;
            for(DetallePedido dp: listaPedidos){
                montoTotal += dp.getSubtotal();
            }
            tvMontoTotal.setText("Monto total: S/. " + montoTotal);
        }
    }

    private void enlazarControles(View view){
        lvPedidos = view.findViewById(R.id.lvPedidos);
        tvMontoTotal = view.findViewById(R.id.tvMontoTotal);
    }
}