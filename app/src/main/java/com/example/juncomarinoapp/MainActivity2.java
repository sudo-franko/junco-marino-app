package com.example.juncomarinoapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

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
import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity {

    private DrawerLayout drawerLayout1;
    private NavigationView nv1;
    private Toolbar tb1;
    private MenuAdapter adapter;
    private ListView lvMenu;


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
                else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame1, new MostrarMenu()).commit();
                }
                drawerLayout1.closeDrawers();
                return true;
            }
        });
    }

}