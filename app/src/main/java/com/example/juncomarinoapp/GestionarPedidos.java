package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.juncomarinoapp.adapters.MenuAdapter;
import com.example.juncomarinoapp.adapters.PedidoAdapter;
import com.example.juncomarinoapp.modelo.dto.Pedido;
import com.example.juncomarinoapp.modelo.dto.Platillo;
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

        lvPedidos = view.findViewById(R.id.lvPedidos);
        PedidoSQLite pSQL = new PedidoSQLite(getContext());
        ArrayList<Pedido> pedidos = pSQL.listarPedidos();

        PedidoAdapter adapter = new PedidoAdapter(getContext(), pedidos);
        lvPedidos.setAdapter(adapter);
        lvPedidos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Pedido pedidoSeleccionado = (Pedido) parent.getItemAtPosition(position);
                VerEstadoPedido fragment = new VerEstadoPedido();
                Bundle b = new Bundle();
                b.putSerializable("PEDIDO", pedidoSeleccionado);
                fragment.setArguments(b);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame1, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }
}