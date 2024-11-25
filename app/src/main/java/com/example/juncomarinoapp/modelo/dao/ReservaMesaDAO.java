package com.example.juncomarinoapp.modelo.dao;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.juncomarinoapp.interfaces.ConstantesApp;
import com.example.juncomarinoapp.modelo.dto.ReservaMesa;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReservaMesaDAO {
    private RequestQueue rq;
    private Context context;

    public ReservaMesaDAO(Context context){
        this.context = context;
        rq = Volley.newRequestQueue(context);
    }

    public void registrarReservaMesa(ReservaMesa reserva, final RegistroListener listener) {
        String url = ConstantesApp.URL_GENERAL + "registrarReserva";
        JSONObject parametros = new JSONObject();

        try {
            parametros.put("idCliente", reserva.getIdCliente());
            parametros.put("numPersonas", reserva.getNumPersonas());
            parametros.put("numMesa", reserva.getNumMesa());
            parametros.put("fecha", reserva.getFecha());
            parametros.put("estado", reserva.getEstado());
        } catch (JSONException e) {
            listener.onRegistroFallido("Error al crear los parámetros JSON");
            return;
        }

        JsonObjectRequest solicitud = new JsonObjectRequest(
                Request.Method.POST,
                url,
                parametros,
                response -> {
                    try {
                        String status = response.getString("status");
                        if (status.equals("success")) {
                            int idReserva = response.getInt("idReserva");
                            reserva.setIdReserva(idReserva);
                            listener.onRegistroExitoso("Reserva registrada con ID: " + idReserva, reserva);
                        } else {
                            String error = response.optString("error", "Error desconocido");
                            listener.onRegistroFallido("Error: " + error);
                        }
                    } catch (JSONException e) {
                        listener.onRegistroFallido("Error en la respuesta JSON");
                    }
                },
                error -> listener.onRegistroFallido("Error en la solicitud: " + error.toString())
        );
        rq.add(solicitud);
    }

    public void actualizarReservaMesa(ReservaMesa reserva, final ActualizarListener listener) {
        String url = ConstantesApp.URL_GENERAL + "actualizarReserva";
        JSONObject parametros = new JSONObject();

        try {
            parametros.put("idReserva", reserva.getIdReserva());
            parametros.put("numPersonas", reserva.getNumPersonas());
            parametros.put("numMesa", reserva.getNumMesa());
            parametros.put("fecha", reserva.getFecha());
            parametros.put("estado", reserva.getEstado());
        } catch (JSONException e) {
            listener.onActualizarFallido("Error al crear los parámetros JSON");
            return;
        }

        JsonObjectRequest solicitud = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                parametros,
                response -> {
                    try {
                        String status = response.getString("status");
                        if (status.equals("success")) {
                            listener.onActualizarExitoso("Reserva actualizada exitosamente");
                        } else {
                            String error = response.optString("error", "Error desconocido");
                            listener.onActualizarFallido("Error: " + error);
                        }
                    } catch (JSONException e) {
                        listener.onActualizarFallido("Error en la respuesta JSON");
                    }
                },
                error -> listener.onActualizarFallido("Error en la solicitud: " + error.toString())
        );
        rq.add(solicitud);
    }

    public void listarReservas(final ListarListener listener) {
        String url = ConstantesApp.URL_GENERAL + "listarReservas";
        JsonArrayRequest solicitud = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        List<ReservaMesa> reservas = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            ReservaMesa reserva = new ReservaMesa();
                            reserva.setIdReserva(obj.getInt("idReserva"));
                            reserva.setIdCliente(obj.getInt("idCliente"));
                            reserva.setNomCliente(obj.optString("nombreCompleto", ""));
                            reserva.setNumPersonas(obj.getInt("numPersonas"));
                            reserva.setNumMesa(obj.getInt("numMesa"));
                            reserva.setFecha(obj.getString("fecha"));
                            reserva.setEstado(obj.getString("estado"));
                            reservas.add(reserva);
                        }
                        listener.onListarExitoso(reservas);
                    } catch (JSONException e) {
                        listener.onListarFallido("Error en la respuesta JSON");
                    }
                },
                error -> listener.onListarFallido("Error en la solicitud: " + error.toString())
        );
        rq.add(solicitud);
    }

    public void listarReservasPorFecha(String fecha, final ListarListener listener) {
        String url = ConstantesApp.URL_GENERAL + "listarReservasPorFecha?fecha=" + fecha;

        JsonArrayRequest solicitud = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        List<ReservaMesa> reservas = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            ReservaMesa reserva = new ReservaMesa();
                            reserva.setIdReserva(obj.getInt("idReserva"));
                            reserva.setIdCliente(obj.getInt("idCliente"));
                            reserva.setNomCliente(obj.optString("nombreCompleto", ""));
                            reserva.setNumPersonas(obj.getInt("numPersonas"));
                            reserva.setNumMesa(obj.getInt("numMesa"));
                            reserva.setFecha(obj.getString("fecha"));
                            reserva.setEstado(obj.getString("estado"));
                            reservas.add(reserva);
                        }
                        listener.onListarExitoso(reservas);
                    } catch (JSONException e) {
                        listener.onListarFallido("Error en la respuesta JSON");
                    }
                },
                error -> listener.onListarFallido("Error en la solicitud: " + error.toString())
        );
        rq.add(solicitud);
    }

    public interface RegistroListener {
        void onRegistroExitoso(String mensaje, ReservaMesa reserva);
        void onRegistroFallido(String error);
    }

    public interface ActualizarListener {
        void onActualizarExitoso(String mensaje);
        void onActualizarFallido(String error);
    }

    public interface ListarListener {
        void onListarExitoso(List<ReservaMesa> reservas);
        void onListarFallido(String error);
    }
}
