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
import android.widget.Toast;

import com.example.juncomarinoapp.interfaces.ConstantesApp;
import com.example.juncomarinoapp.modelo.dto.Platillo;
import com.squareup.picasso.Picasso;


public class VerDetallePlatillo extends Fragment {

    private Platillo platillo;
    private ImageView ivPlatillo;
    private TextView tvNombre, tvDescripcion, tvPrecio;

    public VerDetallePlatillo() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_detalle_platillo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        if (getArguments() != null) {
            platillo = (Platillo) getArguments().getSerializable("PLATILLO");
        }
        if (platillo != null) {
            String imageUrl = ConstantesApp.URL_GENERAL+"imagenPlatillo/"+ platillo.getIdPlatillo();
            Picasso.get().load(imageUrl).into(ivPlatillo);
            tvNombre.setText(platillo.getNombre());
            tvDescripcion.setText(platillo.getDescripcion());
            tvPrecio.setText("S/. "+platillo.getPrecio());
        }
    }

    private void enlazarControles(View view){
        ivPlatillo = view.findViewById(R.id.ivPlatillo);
        tvNombre = view.findViewById(R.id.tvNombre);
        tvDescripcion = view.findViewById(R.id.tvDescripcion);
        tvPrecio = view.findViewById(R.id.tvPrecio);
    }
}