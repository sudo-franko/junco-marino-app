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
import android.widget.Toast;

import com.example.juncomarinoapp.modelo.dao.UsuarioDAO;
import com.example.juncomarinoapp.modelo.dto.Usuario;
import com.example.juncomarinoapp.modelo.sqlite.UsuarioSQLite;

public class LoginCuenta extends Fragment {

    private EditText etUsuario, etContrasenia;
    private Button btnIngresar, btnCrearCuenta;

    public LoginCuenta() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_cuenta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario u = new Usuario();
                u.setUsuario(etUsuario.getText().toString());
                u.setContrasena(etContrasenia.getText().toString());
                UsuarioDAO uDAO = new UsuarioDAO(getContext());
                uDAO.loginCliente(u, new UsuarioDAO.LoginListener() {
                    @Override
                    public void onLoginExitoso(String mensaje, Usuario usuario) {
                        UsuarioSQLite uSQL = new UsuarioSQLite(getContext());
                        uSQL.registrarUsuario(usuario);
                        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }

                    @Override
                    public void onLoginFallido(String mensaje) {
                        Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame1, new GuardarCuenta())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void enlazarControles(View view){
        etUsuario = view.findViewById(R.id.etUsuario);
        etContrasenia = view.findViewById(R.id.etContrasenia);
        btnIngresar = view.findViewById(R.id.btnIngresar);
        btnCrearCuenta = view.findViewById(R.id.btnCrearCuenta);
    }
}