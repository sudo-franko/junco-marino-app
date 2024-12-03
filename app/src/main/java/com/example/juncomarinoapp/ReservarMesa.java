package com.example.juncomarinoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.juncomarinoapp.modelo.dao.ReservaMesaDAO;
import com.example.juncomarinoapp.modelo.dto.ReservaMesa;
import com.example.juncomarinoapp.modelo.dto.Usuario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class ReservarMesa extends Fragment {

    private DatePicker datePicker;
    private Button mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, btnReservar;
    private LinearLayout loadingOverlay;
    private ImageView loadingGif;
    private int numSeleccionado = 0;
    private Usuario usuario;
    private String fecha;
    private ReservaMesa reserva = new ReservaMesa();

    public ReservarMesa() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservar_mesa, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        Glide.with(this)
                .asGif()
                .load(R.drawable.loading_animation)
                .into(loadingGif);
        configurarDatePicker();
        new Handler().postDelayed(() -> {
            loadingOverlay.setVisibility(View.GONE);
            view.findViewById(R.id.linearLayout2).setVisibility(View.VISIBLE);
        }, 1000);
        capturarFechaSeleccionada();
        establecerEventosDeBotones();
        btnReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(numSeleccionado != 0) {
                    reserva.setNumMesa(numSeleccionado);
                    if (getActivity() instanceof MainActivity2) {
                        usuario = ((MainActivity2) getActivity()).getUsuario();
                    }
                    reserva.setIdCliente(usuario.getIdCliente());
                    reserva.setFecha(fecha);
                    reserva.setEstado("Pendiente");
                    mostrarDialogo();
                }else{
                    Toast.makeText(getContext(), "Debe seleccionar una mesa", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void enlazarControles(View view) {
        datePicker = view.findViewById(R.id.datePicker);
        mesa1 = view.findViewById(R.id.mesa1);
        mesa2 = view.findViewById(R.id.mesa2);
        mesa3 = view.findViewById(R.id.mesa3);
        mesa4 = view.findViewById(R.id.mesa4);
        mesa5 = view.findViewById(R.id.mesa5);
        mesa6 = view.findViewById(R.id.mesa6);
        btnReservar = view.findViewById(R.id.btnReservar);
        loadingOverlay = view.findViewById(R.id.loadingOverlay);
        loadingGif = view.findViewById(R.id.loadingGif);
    }

    private void configurarDatePicker() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.setMinDate(calendar.getTimeInMillis());
        datePicker.updateDate(year, month, day);
    }

    private void capturarFechaSeleccionada() {
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String fechaSeleccionada = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                fecha = fechaSeleccionada;
                mostrarCargando();
                habilitarBotones();
                ReservaMesaDAO dao = new ReservaMesaDAO(getContext());
                dao.listarMesasReservasPorFecha(fechaSeleccionada, new ReservaMesaDAO.ListarListener() {
                    @Override
                    public void onListarExitoso(ArrayList<ReservaMesa> reservas) {
                        cambiarSeleccion(0, numSeleccionado);
                        inhabilitarBotones(reservas);
                    }

                    @Override
                    public void onListarFallido(String mensaje) {
                        Toast.makeText(getContext(), "Error:" + mensaje, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
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

    private void mostrarCargando() {
        loadingOverlay.setVisibility(View.VISIBLE);
        new Handler().postDelayed(() -> loadingOverlay.setVisibility(View.GONE), 3000);
    }

    private void habilitarBotones() {
        mesa1.setEnabled(true);
        mesa2.setEnabled(true);
        mesa3.setEnabled(true);
        mesa4.setEnabled(true);
        mesa5.setEnabled(true);
        mesa6.setEnabled(true);
    }

    private void inhabilitarBotones(ArrayList<ReservaMesa> reservas){
        Set<Integer> mesasReservadas = new HashSet<>();
        for (ReservaMesa r : reservas) {
            mesasReservadas.add(r.getNumMesa());
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

    private void mostrarDialogo() {
        EditText input = new EditText(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Finalizando la reserva");
        builder.setMessage("Ingrese la cantidad de personas para la reserva:");
        builder.setView(input);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int numPersonas = Integer.parseInt(input.getText().toString());
                reserva.setNumPersonas(numPersonas);
                ReservaMesaDAO rDAO = new ReservaMesaDAO(getContext());
                rDAO.registrarReservaMesa(reserva, new ReservaMesaDAO.RegistroListener() {
                    @Override
                    public void onRegistroExitoso(String mensaje, ReservaMesa reserva) {
                        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                        Fragment mostrarReservaFinalizada = MostrarReservaFinalizada.newInstance(reserva.getIdReserva());
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.frame1, mostrarReservaFinalizada)
                                .addToBackStack(null)
                                .commit();
                    }

                    @Override
                    public void onRegistroFallido(String error) {
                        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

}