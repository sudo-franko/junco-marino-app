package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class ReservarMesa extends Fragment {

    private DatePicker datePicker;
    private Button mesa1, mesa2, mesa3, mesa4, mesa5, mesa6, btnReservar;

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
        configurarDatePicker();
        capturarFechaSeleccionada();

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
                // Capturar la fecha seleccionada
                String fechaSeleccionada = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                Toast.makeText(getContext(), "Fecha seleccionada: " + fechaSeleccionada, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inhabilitarBotonesMesas() {
        mesa1.setEnabled(false);
        mesa2.setEnabled(false);
        mesa3.setEnabled(false);
        mesa4.setEnabled(false);
        mesa5.setEnabled(false);
        mesa6.setEnabled(false);
    }

    private void habilitarBotonesMesas() {
        mesa1.setEnabled(true);
        mesa2.setEnabled(true);
        mesa3.setEnabled(true);
        mesa4.setEnabled(true);
        mesa5.setEnabled(true);
        mesa6.setEnabled(true);
    }
}