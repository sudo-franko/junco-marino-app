package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juncomarinoapp.interfaces.ConstantesApp;
import com.squareup.picasso.Picasso;

public class GenerarQrReserva extends Fragment {

    private ImageView ivQRReserva;
    private TextView tvIDReserva;
    private int idReserva;

    public GenerarQrReserva() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generar_qr_reserva, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        if (getArguments() != null) {
            idReserva = getArguments().getInt("ID_RESERVA");
            String imageUrl = ConstantesApp.URL_GENERAL+"obtenerQRReserva/"+ idReserva;
            Picasso.get().load(imageUrl).into(ivQRReserva);
            tvIDReserva.setText("PEDIDO #00" +idReserva);
        }
    }

    private void enlazarControles(View view){
        ivQRReserva = view.findViewById(R.id.ivQRReserva);
        tvIDReserva = view.findViewById(R.id.tvIDReserva);
    }
}