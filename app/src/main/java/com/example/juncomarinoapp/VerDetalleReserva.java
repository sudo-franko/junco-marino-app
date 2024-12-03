package com.example.juncomarinoapp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juncomarinoapp.modelo.dao.ReservaMesaDAO;
import com.example.juncomarinoapp.modelo.dto.ReservaMesa;

public class VerDetalleReserva extends Fragment {

    private TextView tvIDReserva, tvNumMesa, tvNumPersonas, tvEstado;
    private Button btnConfirmar, btnActualizar;
    private ReservaMesa reserva;

    public VerDetalleReserva() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_detalle_reserva, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        if (getArguments() != null) {
            reserva = (ReservaMesa) getArguments().getSerializable("RESERVA");
        }
        if(reserva != null){
            tvIDReserva.setText("RESERVA N° " + reserva.getIdReserva());
            tvNumMesa.setText("Mesa número: " + reserva.getNumMesa());
            tvNumPersonas.setText("Cantidad de personas: " + reserva.getNumPersonas());
            tvEstado.setText("Estado: " + reserva.getEstado());
            if(reserva.getEstado().equals("Pendiente")) {
                btnConfirmar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mostrarDialogoConfirmacion();
                    }
                });
                btnActualizar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }else{
                btnConfirmar.setText("GENERAR QR");
                btnConfirmar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GenerarQrReserva fragment = new GenerarQrReserva();
                        Bundle b = new Bundle();
                        b.putInt("ID_RESERVA", reserva.getIdReserva());
                        fragment.setArguments(b);

                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame1, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
                btnActualizar.setEnabled(false);
                btnActualizar.setBackgroundTintList(
                        ContextCompat.getColorStateList(getContext(), R.color.deshabilitado));
            }
        }else{
            btnActualizar.setEnabled(false);
            btnConfirmar.setEnabled(false);
        }
    }

    private void enlazarControles(View view) {
        tvIDReserva = view.findViewById(R.id.tvIDReserva);
        tvNumMesa = view.findViewById(R.id.tvNumMesa);
        tvNumPersonas = view.findViewById(R.id.tvNumPersonas);
        tvEstado = view.findViewById(R.id.tvEstado);
        btnConfirmar = view.findViewById(R.id.btnConfirmar);
        btnActualizar = view.findViewById(R.id.btnActualizar);
    }

    private void mostrarDialogoConfirmacion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirmación");
        builder.setMessage("¿Deseas continuar? Si lo haces ya no podrás actualizar la información de tu reserva");

        builder.setPositiveButton("Continuar", (dialog, which) -> {
            reserva.setEstado("Confirmado");
            ReservaMesaDAO rDAO = new ReservaMesaDAO(getContext());
            rDAO.actualizarReservaMesa(reserva, new ReservaMesaDAO.ActualizarListener() {
                @Override
                public void onActualizarExitoso(String mensaje) {
                    GenerarQrReserva fragment = new GenerarQrReserva();
                    Bundle b = new Bundle();
                    b.putInt("ID_RESERVA", reserva.getIdReserva());
                    fragment.setArguments(b);

                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame1, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }

                @Override
                public void onActualizarFallido(String error) {
                    Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            });
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}