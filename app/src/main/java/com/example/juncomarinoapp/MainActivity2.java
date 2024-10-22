package com.example.juncomarinoapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.juncomarinoapp.objetos.adapters.MenuAdapter;
import com.example.juncomarinoapp.objetos.dto.Platillo;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private ArrayList<Platillo> platillos;
    private MenuAdapter adapter;
    private ListView lvMenu;
    private RequestQueue rq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawerLayout = findViewById(R.id.main);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        lvMenu = findViewById(R.id.lvMenu);
        platillos = new ArrayList<>();
        adapter = new MenuAdapter(this, platillos);
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
                            JSONArray datos = response.getJSONArray("productos");

                            for (int i = 0; i < datos.length(); i++) {
                                JSONObject plato = datos.getJSONObject(i);
                                String nombre = plato.getString("nombre");
                                String descripcion = plato.getString("descripcion");
                                String precio = plato.getString("precio");
                                Platillo p = new Platillo(nombre, descripcion, precio, 1);

                                platillos.add(p);
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
                        Toast.makeText(MainActivity2.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        rq.add(requerimiento);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_home){

        }else if (item.getItemId() == R.id.nav_orders){

        }else if (item.getItemId() == R.id.nav_settings){

        }else{

        }
        drawerLayout.closeDrawers();
        return true;
    }
}