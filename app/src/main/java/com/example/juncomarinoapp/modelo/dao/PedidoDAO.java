package com.example.juncomarinoapp.modelo.dao;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.juncomarinoapp.interfaces.ConstantesApp;
import com.example.juncomarinoapp.modelo.dto.DetallePedido;
import com.example.juncomarinoapp.modelo.dto.Pedido;

import org.json.JSONArray;
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
        String url = ConstantesApp.URL_GENERAL + "registrarPedidoAnonimo"; // Asegúrate de que esta URL sea la correcta
        JSONObject parametros = new JSONObject();

        try {
            // Asignar los valores del objeto pedido a un objeto JSON
            parametros.put("nombreCliente", pedido.getNombreCliente());
            parametros.put("telefonoCliente", pedido.getTelefonoCliente());
            parametros.put("direccionCliente", pedido.getDireccionCliente());
            parametros.put("notas", pedido.getNotas());
            parametros.put("tipoEntrega", pedido.getTipoEntrega());
            parametros.put("tipoPago", pedido.getTipoPago());
            parametros.put("monto", pedido.getMonto());

            JSONArray detallePedidoJsonArray = new JSONArray();
            for (DetallePedido detalle : pedido.getDetalles()) {
                JSONObject detalleJson = new JSONObject();
                // Asegúrate de que la clase DetallePedido tenga los métodos getters necesarios
                detalleJson.put("idPlatillo", detalle.getIdPlatillo()); // Cambia esto según tus atributos
                detalleJson.put("cantidad", detalle.getCantidad()); // Cambia esto según tus atributos
                detallePedidoJsonArray.put(detalleJson);
            }
            parametros.put("detallePedido", detallePedidoJsonArray);

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
                            String status = response.getString("status");
                            if (status.equals("success")) {
                                String idPedido = response.getString("idPedido");
                                listener.onRegistroExitoso("Pedido registrado con ID: " + idPedido);
                            } else {
                                String error = response.optString("error", "Error desconocido");
                                listener.onRegistroFallido("Error: " + error);
                            }
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

    public interface RegistroListener {
        void onRegistroExitoso(String mensaje);
        void onRegistroFallido(String error);
    }
}
