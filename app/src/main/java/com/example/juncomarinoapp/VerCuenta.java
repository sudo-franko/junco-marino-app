package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juncomarinoapp.modelo.dto.Usuario;
import com.example.juncomarinoapp.modelo.sqlite.UsuarioSQLite;

public class VerCuenta extends Fragment {

    private TextView tvNombres, tvCorreo, tvTelefono, tvDireccion;
    private Button btnActualizar, btnCerrarCuenta;
    private Usuario usuario;

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
            usuario = ((MainActivity2) getActivity()).getUsuario();
            tvNombres.setText(usuario.getNombres()+" "+usuario.getApellidos());
            tvTelefono.setText(usuario.getTelefono());
            tvCorreo.setText(usuario.getCorreo());
            tvDireccion.setText(usuario.getDireccion());
        }

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame1, new ActualizarCuenta());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        btnCerrarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UsuarioSQLite uSQL = new UsuarioSQLite(getContext());
                String rpta = uSQL.eliminarUsuario("" + usuario.getIdCliente());
                if(rpta.equals("")){
                    getActivity().finish();
                }else{
                    Toast.makeText(getContext(), rpta, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void enlazarControles(View view){
        tvNombres = view.findViewById(R.id.tvNombreCuenta);
        tvTelefono = view.findViewById(R.id.tvTelefonoCuenta);
        tvCorreo = view.findViewById(R.id.tvCorreoCuenta);
        tvDireccion = view.findViewById(R.id.tvDireccionCuenta);
        btnActualizar = view.findViewById(R.id.btnActualizar);
        btnCerrarCuenta = view.findViewById(R.id.btnCerrarCuenta);
    }
}