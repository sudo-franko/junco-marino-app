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
import com.example.juncomarinoapp.modelo.dto.Pedido;
import com.squareup.picasso.Picasso;

public class GenerarQrPedido extends Fragment {

    private ImageView ivQRPedido;
    private TextView tvIDPedido;
    private int idPedido;

    public GenerarQrPedido() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generar_qr_pedido, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        if (getArguments() != null) {
            idPedido = getArguments().getInt("ID_PEDIDO");
            String imageUrl = ConstantesApp.URL_GENERAL+"obtenerQRPedido/"+ idPedido;
            Picasso.get().load(imageUrl).into(ivQRPedido);
            tvIDPedido.setText("PEDIDO #00" +idPedido);
        }
    }

    private void enlazarControles(View view){
        ivQRPedido = view.findViewById(R.id.ivQRPedido);
        tvIDPedido = view.findViewById(R.id.tvIDPedido);
    }
}