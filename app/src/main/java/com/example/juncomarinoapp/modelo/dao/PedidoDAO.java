package com.example.juncomarinoapp.modelo.dao;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.juncomarinoapp.modelo.dto.Pedido;

import org.json.JSONException;
import org.json.JSONObject;

public class PedidoDAO {
    private RequestQueue rq;
    private Context context;

    public PedidoDAO(Context context){
        this.context = context;
        rq = Volley.newRequestQueue(context);
    }

    public void registrarPedidoSinCuenta(Pedido pedido, final RegistroListener listener) {
        String url = "http://192.168.1.37:4000/registrarPlatillo";
        JSONObject parametros = new JSONObject();
        try {
            parametros.put("notas", pedido.getNotas());
            //parametros.put("nombre", platillo.getNombre());
            //parametros.put("descripcion", platillo.getDescripcion());
            //parametros.put("precio", platillo.getPrecio());
            //parametros.put("idCategoria", platillo.getIdCategoria());
        } catch (JSONException e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            listener.onRegistroFallido("Error al crear los parámetros JSON");
            return;
        }
        JsonObjectRequest solicitud = new JsonObjectRequest(
                Request.Method.POST,
                url,
                parametros,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String mensaje = response.getString("mensaje");
                            listener.onRegistroExitoso(mensaje);
                        } catch (JSONException e) {
                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                            listener.onRegistroFallido("Error en la respuesta JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onRegistroFallido("Error en la solicitud: " + error.toString());
                    }
                }
        );
        rq.add(solicitud);
    }

    // Interfaces
    public interface RegistroListener {
        void onRegistroExitoso(String mensaje);
        void onRegistroFallido(String error);
    }
}
