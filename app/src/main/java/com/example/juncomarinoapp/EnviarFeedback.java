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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juncomarinoapp.interfaces.ConstantesApp;
import com.example.juncomarinoapp.modelo.dao.PedidoDAO;
import com.example.juncomarinoapp.modelo.dto.Pedido;
import com.squareup.picasso.Picasso;

public class EnviarFeedback extends Fragment {

    private TextView tvIDPedido;
    private EditText etComentario;
    private RatingBar ratingBar;
    private Button btnEnviarFeedback;
    private int idPedido;

    public EnviarFeedback() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enviar_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        if (getArguments() != null) {
            idPedido = getArguments().getInt("ID_PEDIDO");
            tvIDPedido.setText("CALIFICANDO EL PEDIDO #00" + idPedido);
            btnEnviarFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    float calificacion = ratingBar.getRating();
                    int calificacionEntera = Math.round(calificacion);
                    String comentario = etComentario.getText().toString();
                    Pedido p = new Pedido();
                    p.setIdPedido(idPedido);
                    p.setCalificacion(String.valueOf(calificacionEntera));
                    p.setComentario(comentario);
                    PedidoDAO pDAO = new PedidoDAO(requireContext());
                    pDAO.enviarFeedbackPedido(p, new PedidoDAO.ActualizarListener() {
                        @Override
                        public void onActualizarExitoso(String mensaje) {
                            Toast.makeText(requireContext(), mensaje, Toast.LENGTH_LONG).show();
                            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            fragmentManager.beginTransaction()
                                    .replace(R.id.frame1, new GestionarPedidos())
                                    .commit();
                        }

                        @Override
                        public void onActualizarFallido(String error) {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }
    }

    private void enlazarControles(View view){
        tvIDPedido = view.findViewById(R.id.tvIDPedido);
        etComentario = view.findViewById(R.id.etComentario);
        ratingBar = view.findViewById(R.id.ratingBar);
        btnEnviarFeedback = view.findViewById(R.id.btnEnviarFeedback);
    }
}