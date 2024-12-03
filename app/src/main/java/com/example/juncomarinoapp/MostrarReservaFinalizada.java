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

public class MostrarReservaFinalizada extends Fragment {

    private TextView tvNumReserva;
    private Button btnVerEstadoReserva;
    private static final String ARG_ID_RESERVA = "idReserva";
    private int idReserva;

    public static MostrarReservaFinalizada newInstance(int idReserva) {
        Bundle args = new Bundle();
        args.putInt(ARG_ID_RESERVA, idReserva);
        MostrarReservaFinalizada fragment = new MostrarReservaFinalizada();
        fragment.setArguments(args);
        return fragment;
    }

    public MostrarReservaFinalizada() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idReserva = getArguments().getInt(ARG_ID_RESERVA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_reserva_finalizada, container, false);
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
                        .replace(R.id.frame1, new GestionarReservas())
                        .commit();
            }
        });
        tvNumReserva = view.findViewById(R.id.tvNumReserva);
        btnVerEstadoReserva = view.findViewById(R.id.btnVerEstadoReserva);
        tvNumReserva.setText("Reserva N° " + idReserva);
        btnVerEstadoReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction()
                        .replace(R.id.frame1, new GestionarReservas())
                        .commit();
            }
        });
    }
}

