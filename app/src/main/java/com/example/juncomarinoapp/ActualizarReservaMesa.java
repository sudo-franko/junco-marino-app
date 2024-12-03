package com.example.juncomarinoapp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.juncomarinoapp.modelo.dao.ReservaMesaDAO;
import com.example.juncomarinoapp.modelo.dto.ReservaMesa;
import com.example.juncomarinoapp.modelo.dto.Usuario;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ActualizarReservaMesa extends Fragment {

    private TextView tvFecha;
    private EditText etNumPersonas;
    private Button mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, btnActualizar, btnCancelar;
    private LinearLayout loadingOverlay;
    private ImageView loadingGif;
    private int numSeleccionado = 0;
    private ReservaMesa reserva;

    public ActualizarReservaMesa() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actualizar_reserva_mesa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        Glide.with(this)
                .asGif()
                .load(R.drawable.loading_animation)
                .into(loadingGif);
        new Handler().postDelayed(() -> {
            loadingOverlay.setVisibility(View.GONE);
            view.findViewById(R.id.linearLayout2).setVisibility(View.VISIBLE);
        }, 1000);
        if (getArguments() != null) {
            reserva = (ReservaMesa) getArguments().getSerializable("RESERVA");
        }
        if(reserva != null) {
            tvFecha.setText("Fecha de reserva: " + reserva.getFecha());
            etNumPersonas.setText("" + reserva.getNumPersonas());
            establecerEventosDeBotones();
            ReservaMesaDAO dao = new ReservaMesaDAO(getContext());
            dao.listarMesasReservasPorFecha(reserva.getFecha(), new ReservaMesaDAO.ListarListener() {
                @Override
                public void onListarExitoso(ArrayList<ReservaMesa> reservas) {
                    cambiarSeleccion(0, numSeleccionado);
                    cambiarSeleccion(reserva.getNumMesa(), numSeleccionado);
                    inhabilitarBotones(reservas);
                    switch(numSeleccionado){
                        case 1: mesa1.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado)); break;
                        case 2: mesa2.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado)); break;
                        case 3: mesa3.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado)); break;
                        case 4: mesa4.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado)); break;
                        case 5: mesa5.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado)); break;
                        case 6: mesa6.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado)); break;
                        default: break;
                    }
                }

                @Override
                public void onListarFallido(String mensaje) {
                    Toast.makeText(getContext(), "Error:" + mensaje, Toast.LENGTH_LONG).show();
                    btnActualizar.setEnabled(false);
                    btnCancelar.setEnabled(false);
                }
            });
            btnActualizar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reserva.setNumMesa(numSeleccionado);
                    reserva.setNumPersonas(Integer.parseInt(etNumPersonas.getText().toString()));
                    dao.actualizarReservaMesa(reserva, new ReservaMesaDAO.ActualizarListener() {
                        @Override
                        public void onActualizarExitoso(String mensaje) {
                            Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame1, new GestionarReservas())
                                    .commit();
                        }

                        @Override
                        public void onActualizarFallido(String error) {
                            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
            btnCancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mostrarDialogoConfirmacion();
                }
            });
        }else{
            btnActualizar.setEnabled(false);
            btnCancelar.setEnabled(false);
        }
    }

    private void enlazarControles(View view) {
        mesa1 = view.findViewById(R.id.mesa1);
        mesa2 = view.findViewById(R.id.mesa2);
        mesa3 = view.findViewById(R.id.mesa3);
        mesa4 = view.findViewById(R.id.mesa4);
        mesa5 = view.findViewById(R.id.mesa5);
        mesa6 = view.findViewById(R.id.mesa6);
        btnActualizar = view.findViewById(R.id.btnActualizar);
        btnCancelar = view.findViewById(R.id.btnCancelar);
        loadingOverlay = view.findViewById(R.id.loadingOverlay);
        loadingGif = view.findViewById(R.id.loadingGif);
        tvFecha = view.findViewById(R.id.tvFecha);
        etNumPersonas = view.findViewById(R.id.etNumPersonas);
    }

    private void establecerEventosDeBotones(){
        mesa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesa1.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado));
                if(numSeleccionado == 0 || numSeleccionado == 1) {
                    numSeleccionado = 1;
                }else{
                    cambiarSeleccion(1, numSeleccionado);
                }
            }
        });
        mesa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesa2.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado));
                if(numSeleccionado == 0 || numSeleccionado == 2) {
                    numSeleccionado = 2;
                }else{
                    cambiarSeleccion(2, numSeleccionado);
                }
            }
        });
        mesa3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesa3.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado));
                if(numSeleccionado == 0 || numSeleccionado == 3) {
                    numSeleccionado = 3;
                }else{
                    cambiarSeleccion(3, numSeleccionado);
                }
            }
        });
        mesa4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesa4.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado));
                if(numSeleccionado == 0 || numSeleccionado == 4) {
                    numSeleccionado = 4;
                }else{
                    cambiarSeleccion(4, numSeleccionado);
                }
            }
        });
        mesa5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesa5.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado));
                if(numSeleccionado == 0 || numSeleccionado == 5) {
                    numSeleccionado = 5;
                }else{
                    cambiarSeleccion(5, numSeleccionado);
                }
            }
        });
        mesa6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesa6.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.seleccionado));
                if(numSeleccionado == 0 || numSeleccionado == 6) {
                    numSeleccionado = 6;
                }else{
                    cambiarSeleccion(6, numSeleccionado);
                }}
        });
    }

    private void cambiarSeleccion(int nuevo, int anterior){
        numSeleccionado = nuevo;
        switch(anterior){
            case 1: mesa1.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.mesaColor)); break;
            case 2: mesa2.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.mesaColor)); break;
            case 3: mesa3.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.mesaColor)); break;
            case 4: mesa4.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.mesaColor)); break;
            case 5: mesa5.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.mesaColor)); break;
            case 6: mesa6.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.mesaColor)); break;
            default: break;
        }
    }

    private void inhabilitarBotones(ArrayList<ReservaMesa> reservas){
        Set<Integer> mesasReservadas = new HashSet<>();
        for (ReservaMesa r : reservas) {
            if(!r.getEstado().equals("Cancelada") && r.getNumMesa() != numSeleccionado) {
                mesasReservadas.add(r.getNumMesa());
            }
        }
        if (mesasReservadas.contains(1)) inhabilitar(mesa1);
        if (mesasReservadas.contains(2)) inhabilitar(mesa2);
        if (mesasReservadas.contains(3)) inhabilitar(mesa3);
        if (mesasReservadas.contains(4)) inhabilitar(mesa4);
        if (mesasReservadas.contains(5)) inhabilitar(mesa5);
        if (mesasReservadas.contains(6)) inhabilitar(mesa6);
    }

    private void inhabilitar(Button btn){
        btn.setEnabled(false);
        btn.setBackgroundTintList(ContextCompat.getColorStateList(getContext(), R.color.deshabilitado));
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirmación");
        builder.setMessage("¿Deseas continuar? Se eliminará esta reserva");

        builder.setPositiveButton("Continuar", (dialog, which) -> {
            reserva.setEstado("Cancelada");
            ReservaMesaDAO rDAO = new ReservaMesaDAO(getContext());
            rDAO.actualizarReservaMesa(reserva, new ReservaMesaDAO.ActualizarListener() {
                @Override
                public void onActualizarExitoso(String mensaje) {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame1, new GestionarReservas())
                            .commit();
                }

                @Override
                public void onActualizarFallido(String error) {
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
        });

        builder.setNegativeButton("Volver", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}