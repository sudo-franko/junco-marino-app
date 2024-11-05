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
import com.example.juncomarinoapp.modelo.dto.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

public class UsuarioDAO {
    private RequestQueue rq;
    private Context context;

    public UsuarioDAO(Context context) {
        this.context = context;
        rq = Volley.newRequestQueue(context);
    }

    public void registrarCliente(Usuario usuario, final RegistroListener listener) {
        String url = ConstantesApp.URL_GENERAL + "registrarCliente";  // Ajusta la URL base en ConstantesApp
        JSONObject parametros = new JSONObject();

        try {
            // Asignar los valores del objeto cliente a un objeto JSON
            parametros.put("nombres", usuario.getNombres());
            parametros.put("apellidos", usuario.getApellidos());
            parametros.put("correo", usuario.getCorreo());
            parametros.put("telefono", usuario.getTelefono());
            parametros.put("direccion", usuario.getDireccion());
            parametros.put("usuario", usuario.getUsuario());
            parametros.put("contrasena", usuario.getContrasena());

        } catch (JSONException e) {
            Toast.makeText(context, "Error al crear los parámetros JSON: " + e.toString(), Toast.LENGTH_SHORT).show();
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
                                int idCliente = response.getInt("idCliente");
                                usuario.setIdCliente(idCliente);
                                listener.onRegistroExitoso("Cliente registrado con ID: " + idCliente, usuario);
                            } else {
                                String error = response.optString("error", "Error desconocido");
                                listener.onRegistroFallido("Error: " + error);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(context, "Error en la respuesta JSON: " + e.toString(), Toast.LENGTH_SHORT).show();
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

        // Añadir la solicitud a la cola de Volley
        rq.add(solicitud);
    }

    public interface RegistroListener {
        void onRegistroExitoso(String mensaje, Usuario usuario);
        void onRegistroFallido(String mensaje);
    }
}
