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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.juncomarinoapp.modelo.dao.UsuarioDAO;
import com.example.juncomarinoapp.modelo.dto.Pedido;
import com.example.juncomarinoapp.modelo.dto.Usuario;
import com.example.juncomarinoapp.modelo.sqlite.UsuarioSQLite;

public class GuardarCuenta extends Fragment {

    private EditText etNombre, etApellido, etEmail, etTelefono, etDireccion, etUsuario, etContrasena;
    private Button btnGuardarCuenta;
    private static final String ARG_PEDIDO = "pedido";
    private static final String ARG_MEDIO = "medioPedido";

    public GuardarCuenta() {
        // Required empty public constructor
    }

    public static GuardarCuenta newInstance(Pedido pedido, boolean enMedioPedido) {
        GuardarCuenta fragment = new GuardarCuenta();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PEDIDO, pedido);
        args.putBoolean(ARG_MEDIO, enMedioPedido);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guardar_cuenta, container, false);
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
        btnGuardarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = etNombre.getText().toString().trim();
                String apellido = etApellido.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String telefono = etTelefono.getText().toString().trim();
                String direccion = etDireccion.getText().toString().trim();
                String usuario = etUsuario.getText().toString().trim();
                String contrasena = etContrasena.getText().toString().trim();

                if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() ||
                        telefono.isEmpty() || direccion.isEmpty() || usuario.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor, complete todos los campos.", Toast.LENGTH_LONG).show();
                } else {
                    Usuario u = new Usuario();
                    u.setNombres(nombre);
                    u.setApellidos(apellido);
                    u.setCorreo(email);
                    u.setTelefono(telefono);
                    u.setDireccion(direccion);
                    u.setUsuario(usuario);
                    u.setContrasena(contrasena);
                    UsuarioDAO uDAO = new UsuarioDAO(getContext());
                    uDAO.registrarCliente(u, new UsuarioDAO.RegistroListener() {
                        @Override
                        public void onRegistroExitoso(String mensaje, Usuario usuario) {
                            UsuarioSQLite uSQL = new UsuarioSQLite(getContext());
                            String rpta = uSQL.registrarUsuario(usuario);
                            if(rpta != ""){
                                Toast.makeText(getContext(), rpta, Toast.LENGTH_LONG).show();
                            }else {
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onRegistroFallido(String mensaje) {
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
        etUsuario = view.findViewById(R.id.etUsuario);
        etContrasena = view.findViewById(R.id.etContrasena);
        btnGuardarCuenta = view.findViewById(R.id.btnGuardarCuenta);
    }
}