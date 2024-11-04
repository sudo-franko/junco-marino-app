package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.juncomarinoapp.modelo.dto.Pedido;
import com.example.juncomarinoapp.modelo.sqlite.PedidoSQLite;

import java.util.ArrayList;

public class GestionarPedidos extends Fragment {

    private ListView lvPedidos;

    public GestionarPedidos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestionar_pedidos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lvPedidos = view.findViewById(R.id.lvPedidos);

        ArrayList<String> dataList = new ArrayList<>();
        PedidoSQLite pSQL = new PedidoSQLite(getContext());
        ArrayList<Pedido> pedidos = pSQL.listarPedidos();
        for(Pedido p: pedidos){
            dataList.add(p.getIdPedido()+" - "+p.getNombreCliente()+" (S/. "+p.getMonto()+")");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                dataList
        );

        lvPedidos.setAdapter(adapter);
    }
}