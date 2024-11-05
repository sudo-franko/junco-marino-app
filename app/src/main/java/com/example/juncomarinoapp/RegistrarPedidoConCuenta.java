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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.juncomarinoapp.modelo.dao.PedidoDAO;
import com.example.juncomarinoapp.modelo.dto.Pedido;
import com.example.juncomarinoapp.modelo.dto.Usuario;
import com.example.juncomarinoapp.modelo.sqlite.PedidoSQLite;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrarPedidoConCuenta extends Fragment {

    private static final String ARG_PEDIDO = "pedido";
    private static final String ARG_MEDIO = "medioPedido";
    private Pedido pedido;
    private Usuario usuario;
    private boolean enMedioPedido;
    private Spinner spTipoEntrega, spTipoPago;
    private EditText etNombresCliente, etTelefono, etDireccion;
    private Button btnRealizarPedido;

    public RegistrarPedidoConCuenta() {
        // Required empty public constructor
    }

    public static RegistrarPedidoConCuenta newInstance(Pedido pedido, boolean enMedioPedido) {
        RegistrarPedidoConCuenta fragment = new RegistrarPedidoConCuenta();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PEDIDO, pedido);
        args.putBoolean(ARG_MEDIO, enMedioPedido);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pedido = (Pedido) getArguments().getSerializable(ARG_PEDIDO);
            enMedioPedido = getArguments().getBoolean(ARG_MEDIO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registrar_pedido_con_cuenta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        if(enMedioPedido) {
            requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame1, new MostrarDetallePedido())
                            .commit();
                }
            });
        }

        if (getActivity() instanceof MainActivity2) {
            usuario = ((MainActivity2) getActivity()).getUsuario();
            etNombresCliente.setText(usuario.getNombres()+" "+usuario.getApellidos());
            etTelefono.setText(usuario.getTelefono());
            etDireccion.setText(usuario.getDireccion());
        }

        spTipoEntrega.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    etDireccion.setEnabled(true);
                }
                if(i==0){
                    etDireccion.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnRealizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String entrega = spTipoEntrega.getSelectedItem().toString();
                String pago = spTipoPago.getSelectedItem().toString();
                String nombres = etNombresCliente.getText().toString();
                String telefono = etTelefono.getText().toString();
                String direccion = etDireccion.getText().toString();
                if(!entrega.isEmpty() && !pago.isEmpty() && !nombres.isEmpty() && !telefono.isEmpty()){
                    if(entrega.equals("Entrega a domicilio") && direccion.isEmpty()) {
                        Toast.makeText(getContext(), "Debe ingresar la dirección de entrega", Toast.LENGTH_LONG).show();
                    }else{
                        pedido.setIdCliente(usuario.getIdCliente());
                        pedido.setNombreCliente(nombres);
                        pedido.setTelefonoCliente(telefono);
                        pedido.setDireccionCliente(direccion);
                        pedido.setTipoEntrega(entrega);
                        pedido.setTipoPago(pago);
                        PedidoDAO pDAO = new PedidoDAO(getContext());
                        pDAO.registrarPedidoConCuenta(pedido, new PedidoDAO.RegistroListener() {
                            @Override
                            public void onRegistroExitoso(String mensaje, Pedido p) {
                                Toast.makeText(getContext(), mensaje, Toast.LENGTH_LONG).show();
                                p.setEstado("Solicitado");
                                if(p.getNotas().isEmpty()){
                                    p.setNotas("");
                                }
                                p.setCalificacion("");
                                p.setComentario("");
                                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                Date fechaActual = new Date();
                                p.setFecha(formato.format(fechaActual));
                                PedidoSQLite pSQL = new PedidoSQLite(getContext());
                                String rpta = pSQL.registrarPedido(p);
                                if(rpta!=""){
                                    Toast.makeText(getContext(), rpta, Toast.LENGTH_LONG).show();
                                }
                                Fragment mostrarPedidoFinalizado = MostrarPedidoFinalizado.newInstance(p.getIdPedido());
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                                fragmentManager.beginTransaction()
                                        .replace(R.id.frame1, mostrarPedidoFinalizado)
                                        .addToBackStack(null)
                                        .commit();
                            }

                            @Override
                            public void onRegistroFallido(String error) {
                                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }else{
                    Toast.makeText(getContext(), "Se deben completar los datos requeridos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void enlazarControles(View view){
        spTipoEntrega = view.findViewById(R.id.spTipoEntrega);
        spTipoPago = view.findViewById(R.id.spTipoPago);
        etNombresCliente = view.findViewById(R.id.etNombresCliente);
        etTelefono = view.findViewById(R.id.etTelefono);
        etDireccion = view.findViewById(R.id.etDireccion);
        btnRealizarPedido = view.findViewById(R.id.btnRealizarPedido);
    }
}