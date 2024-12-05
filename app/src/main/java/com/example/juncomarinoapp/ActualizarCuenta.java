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

public class ActualizarCuenta extends Fragment {

    private EditText etNombre, etApellido, etEmail, etTelefono, etDireccion;
    private Button btnActualizarCuenta;
    private Usuario usuario;

    public ActualizarCuenta() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_actualizar_cuenta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        cargarControles();
        btnActualizarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombre.getText().toString().trim();
                String apellido = etApellido.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String telefono = etTelefono.getText().toString().trim();
                String direccion = etDireccion.getText().toString().trim();
                if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() ||
                        telefono.isEmpty() || direccion.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, complete todos los campos.", Toast.LENGTH_LONG).show();
                } else {
                    usuario.setNombres(nombre);
                    usuario.setApellidos(apellido);
                    usuario.setCorreo(email);
                    usuario.setTelefono(telefono);
                    usuario.setDireccion(direccion);
                    UsuarioDAO uDAO = new UsuarioDAO(getContext());
                    uDAO.actualizarCliente(usuario, new UsuarioDAO.ActualizarListener() {
                        @Override
                        public void onActualizarExitoso(String mensaje) {
                            UsuarioSQLite uSQL = new UsuarioSQLite(getContext());
                            String rpta = uSQL.actualizarUsuario(usuario);
                            if(rpta != ""){
                                Toast.makeText(getContext(), rpta, Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getContext(), "Datos actualizados correctamente", Toast.LENGTH_LONG).show();
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onActualizarFallido(String mensaje) {
                            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void enlazarControles(View view){
        etNombre = view.findViewById(R.id.etNombre);
        etApellido = view.findViewById(R.id.etApellido);
        etEmail = view.findViewById(R.id.etEmail);
        etTelefono = view.findViewById(R.id.etTelefono);
        etDireccion = view.findViewById(R.id.etDireccion);
        btnActualizarCuenta = view.findViewById(R.id.btnActualizarCuenta);
    }

    private void cargarControles(){
        if (getActivity() instanceof MainActivity2) {
            usuario = ((MainActivity2) getActivity()).getUsuario();
            etNombre.setText(usuario.getNombres());
            etApellido.setText(usuario.getApellidos());
            etTelefono.setText(usuario.getTelefono());
            etEmail.setText(usuario.getCorreo());
            etDireccion.setText(usuario.getDireccion());
        }
    }
}