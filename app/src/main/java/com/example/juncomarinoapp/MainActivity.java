package com.example.juncomarinoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvMenu;
    private ArrayList<String> menuItems;
    private ArrayAdapter<String> adapter;
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerlayout1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
/*
        lvMenu = findViewById(R.id.lvMenu);
        menuItems = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuItems);
        lvMenu.setAdapter(adapter);

        String url = "http://192.168.1.37:4000/listarPlatillos";
        rq = Volley.newRequestQueue(this);

        JsonObjectRequest requerimiento = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray platillos = response.getJSONArray("productos");

                            for (int i = 0; i < platillos.length(); i++) {
                                JSONObject plato = platillos.getJSONObject(i);
                                String nombre = plato.getString("nombre");
                                String descripcion = plato.getString("descripcion");
                                String precio = plato.getString("precio");

                                menuItems.add(nombre + " \n" + descripcion + " \nS/." + precio);
                            }

                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        rq.add(requerimiento);
*/
    }

    public void conectar(View view){
        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
    }

}