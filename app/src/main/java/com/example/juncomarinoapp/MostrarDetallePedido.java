package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juncomarinoapp.adapters.DetallePedidoAdapter;
import com.example.juncomarinoapp.modelo.dto.DetallePedido;
import com.example.juncomarinoapp.modelo.dto.Pedido;
import com.example.juncomarinoapp.modelo.dto.Usuario;
import com.example.juncomarinoapp.modelo.sqlite.UsuarioSQLite;

import java.util.ArrayList;

public class MostrarDetallePedido extends Fragment {

    private ListView lvPedidos;
    private TextView tvMontoTotal;
    private EditText txtNota;
    private DetallePedidoAdapter adapter;
    private ArrayList<DetallePedido> listaPedidos;
    private Button btnContinuarPedido;
    private Button btnEliminarPedido;

    public MostrarDetallePedido() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_detalle_pedido, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        if (getActivity() instanceof MainActivity2) {
            listaPedidos = ((MainActivity2) getActivity()).getPedidos();
            adapter = new DetallePedidoAdapter(getContext(), listaPedidos);
            lvPedidos.setAdapter(adapter);
            double montoTotal = 0;
            for(DetallePedido dp: listaPedidos){
                montoTotal += dp.getSubtotal();
            }
            tvMontoTotal.setText("Monto total: S/. " + montoTotal);
        }

        btnContinuarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!listaPedidos.isEmpty()) {
                    Pedido pedido = new Pedido();
                    pedido.setIdCliente(0);
                    pedido.setNotas(txtNota.getText().toString());
                    double montoTotal = 0;
                    for(DetallePedido dp: listaPedidos){
                        montoTotal += dp.getSubtotal();
                    }
                    pedido.setMonto(montoTotal);
                    pedido.setDetalles(listaPedidos);
                    UsuarioSQLite uSQL = new UsuarioSQLite(getContext());
                    Usuario u = uSQL.obtenerUsuario();
                    if(u == null){
                        SolicitarCuenta fragmento = SolicitarCuenta.newInstance(pedido);
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.frame1, fragmento)
                                .addToBackStack(null)
                                .commit();
                    }else{

                    }
                }else{
                    Toast.makeText(getContext(),
                            "Primero selecciona los platillos que desees en la carta",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnEliminarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof MainActivity2) {
                    ((MainActivity2) getActivity()).setPedidos(new ArrayList<DetallePedido>());
                    getParentFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            }
        });
    }

    private void enlazarControles(View view){
        lvPedidos = view.findViewById(R.id.lvPedidos);
        tvMontoTotal = view.findViewById(R.id.tvMontoTotal);
        txtNota = view.findViewById(R.id.txtNota);
        btnContinuarPedido = view.findViewById(R.id.btnContinuarPedido);
        btnEliminarPedido = view.findViewById(R.id.btnEliminarPedido);
    }

}