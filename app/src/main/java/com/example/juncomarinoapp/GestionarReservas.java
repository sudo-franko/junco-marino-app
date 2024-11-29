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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.juncomarinoapp.adapters.ReservaAdapter;
import com.example.juncomarinoapp.modelo.dao.ReservaMesaDAO;
import com.example.juncomarinoapp.modelo.dto.ReservaMesa;
import com.example.juncomarinoapp.modelo.dto.Usuario;

import java.util.ArrayList;
import java.util.List;

public class GestionarReservas extends Fragment {

    private Button btnNuevaReserva;
    private ListView lvReservas;
    private Usuario usuario;

    public GestionarReservas() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gestionar_reservas, container, false);
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

        enlazarControles(view);
        ReservaMesaDAO rDAO = new ReservaMesaDAO(getContext());
        if (getActivity() instanceof MainActivity2) {
            usuario = ((MainActivity2) getActivity()).getUsuario();
        }
        rDAO.listarReservasPorCliente(usuario.getIdCliente(), new ReservaMesaDAO.ListarListener() {
            @Override
            public void onListarExitoso(ArrayList<ReservaMesa> reservas) {
                if(!reservas.isEmpty()){
                    ReservaAdapter adapter = new ReservaAdapter(getContext(), reservas);
                    lvReservas.setAdapter(adapter);
                }
            }

            @Override
            public void onListarFallido(String error) {
                Toast.makeText(getContext(), "Error al obtener las reservas: "+error, Toast.LENGTH_LONG).show();
            }
        });
        btnNuevaReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReservarMesa fragment = new ReservarMesa();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame1, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void enlazarControles(View view){
        btnNuevaReserva = view.findViewById(R.id.btnNuevaReserva);
        lvReservas = view.findViewById(R.id.lvReservas);
    }
}