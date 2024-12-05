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
import com.example.juncomarinoapp.modelo.dto.ReservaMesa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PedidoDAO {
    private RequestQueue rq;
    private Context context;

    public PedidoDAO(Context context){
        this.context = context;
        rq = Volley.newRequestQueue(context);
    }

    public void registrarPedidoSinCuenta(Pedido pedido, final RegistroListener listener) {
        String url = ConstantesApp.URL_GENERAL + "registrarPedidoAnonimo";
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
                detalleJson.put("subtotal", detalle.getSubtotal());
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
                                int idPedido = response.getInt("idPedido");
                                pedido.setIdPedido(idPedido);
                                listener.onRegistroExitoso("Pedido registrado con ID: " + idPedido, pedido);
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


    public void registrarPedidoConCuenta(Pedido pedido, final RegistroListener listener) {
        String url = ConstantesApp.URL_GENERAL + "registrarPedidoCliente";
        JSONObject parametros = new JSONObject();

        try {
            // Asignar los valores del objeto pedido a un objeto JSON
            parametros.put("idCliente", pedido.getIdCliente());
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
                detalleJson.put("subtotal", detalle.getSubtotal());
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
                                int idPedido = response.getInt("idPedido");
                                pedido.setIdPedido(idPedido);
                                listener.onRegistroExitoso("Pedido registrado con ID: " + idPedido, pedido);
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

    public void obtenerEstadoPedidoPorId(int id, final PedidoDAO.BuscarListener listener) {
        String url = ConstantesApp.URL_GENERAL + "obtenerEstadoPedido/" + id;
        JsonObjectRequest solicitud = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        String status = response.getString("status");
                        if (!"success".equals(status)) {
                            listener.onBuscarFallido("Error en la respuesta: estado no exitoso");
                            return;
                        }
                        JSONArray pedidoArray = response.getJSONArray("pedido");
                        ArrayList<Pedido> pedidos = new ArrayList<>();
                        for (int i = 0; i < pedidoArray.length(); i++) {
                            JSONObject obj = pedidoArray.getJSONObject(i);
                            Pedido pedido = new Pedido();
                            pedido.setIdPedido(id);
                            pedido.setEstado(obj.getString("estado"));
                            pedido.setCalificacion(obj.getString("calificacion"));
                            pedido.setComentario(obj.getString("comentario"));
                            pedidos.add(pedido);
                        }
                        listener.onBuscarExitoso(pedidos.get(0));
                    } catch (JSONException e) {
                        listener.onBuscarFallido("Error en la respuesta JSON");
                    }
                },
                error -> listener.onBuscarFallido(error.toString())
        );
        rq.add(solicitud);
    }

    public interface RegistroListener {
        void onRegistroExitoso(String mensaje, Pedido p);
        void onRegistroFallido(String error);
    }

    public interface BuscarListener {
        void onBuscarExitoso(Pedido pedido);
        void onBuscarFallido(String error);
    }
}
