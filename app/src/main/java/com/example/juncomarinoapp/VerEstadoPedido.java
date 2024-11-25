package com.example.juncomarinoapp;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;

import com.example.juncomarinoapp.adapters.DetallePedidoAdapter;
import com.example.juncomarinoapp.modelo.dto.Pedido;

public class VerEstadoPedido extends Fragment {

    private TextView tvNumeroPedido, tvEstadoPedido;
    private Button btnGenerarQR;
    private ListView lvDetallePedido;
    private ProgressBar barraProgreso;
    private Pedido pedido;

    public VerEstadoPedido() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_estado_pedido, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        if (getArguments() != null) {
            pedido = (Pedido) getArguments().getSerializable("PEDIDO");
        }
        if(pedido != null){
            DetallePedidoAdapter adapter = new DetallePedidoAdapter(getContext(), pedido.getDetalles());
            lvDetallePedido.setAdapter(adapter);
            tvNumeroPedido.setText("PEDIDO #00" + pedido.getIdPedido());
            switch(pedido.getEstado()){
                case "Recibido":
                    tvEstadoPedido.setText("Recibido");
                    tvEstadoPedido.setTextColor(ContextCompat.getColor(getContext(), R.color.yellow));
                    barraProgreso.setProgress(10);
                    barraProgreso.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.yellow)));
                    break;
                case "En preparacion":
                    tvEstadoPedido.setText("En preparación");
                    tvEstadoPedido.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
                    barraProgreso.setProgress(40);
                    barraProgreso.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.orange)));
                    break;
                case "En camino":
                    tvEstadoPedido.setText("En camino");
                    tvEstadoPedido.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                    barraProgreso.setProgress(80);
                    barraProgreso.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green)));
                    break;
                case "Listo para recoger":
                    tvEstadoPedido.setText("Listo para recoger");
                    tvEstadoPedido.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                    barraProgreso.setProgress(90);
                    barraProgreso.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green)));
                    break;
                case "Entregado":
                    tvEstadoPedido.setText("En preparación");
                    tvEstadoPedido.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                    barraProgreso.setProgress(100);
                    barraProgreso.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green)));
                    break;
                default:
                    break;
            }
            btnGenerarQR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GenerarQrPedido fragment = new GenerarQrPedido();
                    Bundle b = new Bundle();
                    b.putInt("ID_PEDIDO", pedido.getIdPedido());
                    fragment.setArguments(b);

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame1, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
    }

    private void enlazarControles(View view){
        tvNumeroPedido = view.findViewById(R.id.tvNumeroPedido);
        tvEstadoPedido = view.findViewById(R.id.tvEstadoPedido);
        barraProgreso = view.findViewById(R.id.barraProgreso);
        lvDetallePedido = view.findViewById(R.id.lvDetallePedido);
        btnGenerarQR = view.findViewById(R.id.btnGenerarQR);
    }
}