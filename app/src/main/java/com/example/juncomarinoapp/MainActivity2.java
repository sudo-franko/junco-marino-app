package com.example.juncomarinoapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.juncomarinoapp.adapters.MenuAdapter;
import com.example.juncomarinoapp.modelo.dto.DetallePedido;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private DrawerLayout drawerLayout1;
    private NavigationView nv1;
    private Toolbar tb1;
    private MenuAdapter adapter;
    private ListView lvMenu;
    private ArrayList<DetallePedido> pedidosRealizados = new ArrayList<>();

    public ArrayList<DetallePedido> getPedidos(){
        return pedidosRealizados;
    }

    public void setPedidos(ArrayList<DetallePedido> pedidos){
        pedidosRealizados.clear();
        pedidosRealizados.addAll(pedidos);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_button) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame1, new MostrarDetallePedido())
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);/*
        MenuItem menuItem = menu.findItem(R.id.action_button);
        menuItem.setActionView(R.layout.menu_icono_compras);
        RelativeLayout actionView = (RelativeLayout) menuItem.getActionView();
        ImageView iconShoppingBag = actionView.findViewById(R.id.icon_shopping_bag);
        TextView badgeCounter = actionView.findViewById(R.id.badge_counter);
        updateBadgeCounter(badgeCounter, 0);
        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));*/
        return true;
    }
/*
    private void updateBadgeCounter(TextView badgeCounter, int count) {
        if (count > 0) {
            badgeCounter.setText(String.valueOf(count));
            badgeCounter.setVisibility(View.VISIBLE);
        } else {
            badgeCounter.setVisibility(View.GONE);
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerlayout1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        drawerLayout1 = findViewById(R.id.drawerlayout1);
        nv1 = findViewById(R.id.nav_view);
        tb1 = findViewById(R.id.toolbar1);
        setSupportActionBar(tb1);
        ActionBarDrawerToggle actionBarDrawerToggle1 = new ActionBarDrawerToggle(this, drawerLayout1, tb1, 0, 0);
        drawerLayout1.addDrawerListener(actionBarDrawerToggle1);
        actionBarDrawerToggle1.syncState();
        getSupportFragmentManager().beginTransaction().add(R.id.frame1, new MostrarMenu()).commit();

        nv1.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_menu){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new MostrarMenu()).commit();
                }
                else if(item.getItemId() == R.id.nav_pedidos){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new GestionarPedidos()).commit();
                }
                else if(item.getItemId() == R.id.nav_cuenta){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new GuardarCuenta()).commit();
                }
                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new MostrarMenu()).commit();
                }
                drawerLayout1.closeDrawers();
                return true;
            }
        });
    }

}