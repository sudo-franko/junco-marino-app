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
import android.widget.TextView;

import com.example.juncomarinoapp.modelo.dto.Usuario;

public class VerCuenta extends Fragment {

    private TextView tvNombres, tvCorreo, tvTelefono, tvDireccion;

    public VerCuenta() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_cuenta, container, false);
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
                        .replace(R.id.frame1, new MostrarMenu())
                        .commit();
            }
        });

        enlazarControles(view);
        if (getActivity() instanceof MainActivity2) {
            Usuario u = ((MainActivity2) getActivity()).getUsuario();
            tvNombres.setText(u.getNombres()+" "+u.getApellidos());
            tvTelefono.setText(u.getTelefono());
            tvCorreo.setText(u.getCorreo());
            tvDireccion.setText(u.getDireccion());
        }
    }

    private void enlazarControles(View view){
        tvNombres = view.findViewById(R.id.tvNombreCuenta);
        tvTelefono = view.findViewById(R.id.tvTelefonoCuenta);
        tvCorreo = view.findViewById(R.id.tvCorreoCuenta);
        tvDireccion = view.findViewById(R.id.tvDireccionCuenta);
    }
}