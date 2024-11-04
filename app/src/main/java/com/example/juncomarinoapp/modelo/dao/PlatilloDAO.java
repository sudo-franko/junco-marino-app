package com.example.juncomarinoapp.modelo.dao;

import android.app.DownloadManager;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.juncomarinoapp.interfaces.ConstantesApp;
import com.example.juncomarinoapp.modelo.dto.CategoriaPlatillo;
import com.example.juncomarinoapp.modelo.dto.Platillo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PlatilloDAO {

    private RequestQueue rq;
    private Context context;

    public PlatilloDAO(Context context){
        this.context = context;
        rq = Volley.newRequestQueue(context);
    }

    public void listarCategorias(final CategoriasListener listener){
        ArrayList<CategoriaPlatillo> categorias = new ArrayList<>();
        String url = ConstantesApp.URL_GENERAL+"listarCategorias";
        JsonObjectRequest solicitud = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray datos = response.getJSONArray("categorias");
                            for (int i = 0; i < datos.length(); i++) {
                                JSONObject cat = datos.getJSONObject(i);
                                CategoriaPlatillo cp = new CategoriaPlatillo();
                                cp.setIdCategoria(cat.getInt("idCategoria"));
                                cp.setCategoria(cat.getString("categoria"));
                                cp.setUrlImagen("http://192.168.1.37:4000/imagenCategoria/"+cp.getIdCategoria());
                                categorias.add(cp);
                            }
                            listener.onCategoriasRecibidas(categorias);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        rq.add(solicitud);
    }

    public void listarPlatillos(final PlatillosListener listener){
        ArrayList<Platillo> platillos = new ArrayList<>();
        String url = ConstantesApp.URL_GENERAL+"listarPlatillos";
        JsonObjectRequest solicitud = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray datos = response.getJSONArray("platillos");
                            for (int i = 0; i < datos.length(); i++) {
                                JSONObject plato = datos.getJSONObject(i);
                                int idPlatillo = plato.getInt("idPlatillo");
                                String nombre = plato.getString("nombre");
                                String descripcion = plato.getString("descripcion");
                                String precio = plato.getString("precio");
                                int idCategoria = plato.getInt("idCategoria");
                                String categoria = plato.getString("categoria");
                                Platillo p = new Platillo(idPlatillo, nombre, descripcion, precio, idCategoria, categoria);
                                platillos.add(p);
                            }
                            listener.onPlatillosRecibidos(platillos);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        rq.add(solicitud);
    }

    // Interfaces
    public interface CategoriasListener {
        void onCategoriasRecibidas(ArrayList<CategoriaPlatillo> categorias);
    }

    public interface PlatillosListener {
        void onPlatillosRecibidos(ArrayList<Platillo> platillos);
    }
}
