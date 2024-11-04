package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juncomarinoapp.interfaces.ConstantesApp;
import com.example.juncomarinoapp.modelo.dto.DetallePedido;
import com.example.juncomarinoapp.modelo.dto.Platillo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class VerDetallePlatillo extends Fragment {

    private ImageView btnAumentar, btnDisminuir;
    private Platillo platillo;
    private ImageView ivPlatillo;
    private TextView tvNombre, tvDescripcion, tvPrecio;
    private EditText etCantidad;
    private Button btnAgregarCompra;
    private boolean actualizar;
    private int indice;

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
            if (getActivity() instanceof MainActivity2) {
                ArrayList<DetallePedido> pedidos = ((MainActivity2) getActivity()).getPedidos();
                for(int i=0; i<pedidos.size(); i++){
                    if(pedidos.get(i).getIdPlatillo() == platillo.getIdPlatillo()){
                        etCantidad.setText(""+pedidos.get(i).getCantidad());
                        actualizar = true;
                        indice = i;
                    }
                }
            }
            String imageUrl = ConstantesApp.URL_GENERAL+"imagenPlatillo/"+ platillo.getIdPlatillo();
            Picasso.get().load(imageUrl).into(ivPlatillo);
            tvNombre.setText(platillo.getNombre());
            tvDescripcion.setText(platillo.getDescripcion());
            tvPrecio.setText("S/. "+platillo.getPrecio());

            btnAumentar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cantidadActual = Integer.parseInt(etCantidad.getText().toString());
                    cantidadActual++;
                    etCantidad.setText(String.valueOf(cantidadActual));
                }
            });

            btnDisminuir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cantidadActual = Integer.parseInt(etCantidad.getText().toString());
                    if (cantidadActual > 0) {
                        cantidadActual--;
                        etCantidad.setText(String.valueOf(cantidadActual));
                    }
                }
            });

            btnAgregarCompra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int cantidad = Integer.parseInt(etCantidad.getText().toString());
                    int idPlatillo = platillo.getIdPlatillo();
                    String nombrePlatillo = platillo.getNombre();
                    double precioPlatillo = Double.parseDouble(platillo.getPrecio());
                    double subtotal = cantidad * precioPlatillo;
                    if(!actualizar){
                        DetallePedido dp = new DetallePedido();
                        dp.setIdPlatillo(idPlatillo);
                        dp.setNombrePlatillo(nombrePlatillo);
                        dp.setCantidad(cantidad);
                        dp.setSubtotal(subtotal);

                        if (getActivity() instanceof MainActivity2) {
                            ArrayList<DetallePedido> pedidos = ((MainActivity2) getActivity()).getPedidos();
                            pedidos.add(dp);
                        }
                    }else{
                        if (getActivity() instanceof MainActivity2) {
                            ArrayList<DetallePedido> pedidos = ((MainActivity2) getActivity()).getPedidos();
                            pedidos.get(indice).setCantidad(cantidad);
                            pedidos.get(indice).setSubtotal(subtotal);
                        }
                    }
                }
            });
        }
    }

    private void enlazarControles(View view){
        ivPlatillo = view.findViewById(R.id.ivPlatillo);
        tvNombre = view.findViewById(R.id.tvNombre);
        tvDescripcion = view.findViewById(R.id.tvDescripcion);
        tvPrecio = view.findViewById(R.id.tvPrecio);
        etCantidad = view.findViewById(R.id.etCantidad);
        btnAumentar = view.findViewById(R.id.btnAum);
        btnDisminuir = view.findViewById(R.id.btnRes);
        btnAgregarCompra = view.findViewById(R.id.btnAgregarCompra);
        etCantidad.setText("0");
    }
}