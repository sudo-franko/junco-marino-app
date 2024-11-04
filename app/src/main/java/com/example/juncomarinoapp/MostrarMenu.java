package com.example.juncomarinoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juncomarinoapp.adapters.CategoriaAdapter;
import com.example.juncomarinoapp.adapters.MenuAdapter;
import com.example.juncomarinoapp.modelo.dao.PlatilloDAO;
import com.example.juncomarinoapp.modelo.dto.CategoriaPlatillo;
import com.example.juncomarinoapp.modelo.dto.DetallePedido;
import com.example.juncomarinoapp.modelo.dto.Platillo;

import java.util.ArrayList;

public class MostrarMenu extends Fragment {

    private ListView lvMenu;
    private RecyclerView recyclerView;
    private ArrayList<Platillo> menuItems;
    private ArrayList<CategoriaPlatillo> listaCategorias;
    private MenuAdapter adapter;
    private TextView tvCategoria;

    public MostrarMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mostrar_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enlazarControles(view);
        adapter = new MenuAdapter(getContext(), null);
        PlatilloDAO pDAO = new PlatilloDAO(getContext());
        pDAO.listarPlatillos(new PlatilloDAO.PlatillosListener() {
            @Override
            public void onPlatillosRecibidos(ArrayList<Platillo> platillos) {
                menuItems = platillos;
                ArrayList<Platillo> listaDefecto = new ArrayList<>();
                for(Platillo p: menuItems){
                    if(p.getIdCategoria() == 1){
                        listaDefecto.add(p);
                    }
                }
                adapter.setLista(listaDefecto);
                lvMenu.setAdapter(adapter);
            }
        });
        pDAO.listarCategorias(new PlatilloDAO.CategoriasListener() {
            @Override
            public void onCategoriasRecibidas(ArrayList<CategoriaPlatillo> categorias) {
                if(!categorias.isEmpty()) {
                    listaCategorias = categorias;
                    ArrayList<String> imagenes = new ArrayList<>();
                    for(CategoriaPlatillo cp: listaCategorias){
                        imagenes.add(cp.getUrlImagen());
                    }
                    CategoriaAdapter adapter2 = new CategoriaAdapter(view.getContext(), imagenes);
                    adapter2.setOnItemClickListener(new CategoriaAdapter.OnItemClickListener() {
                        @Override
                        public void onClick(ImageView imageView, int position) {
                            ArrayList<Platillo> platillos = new ArrayList<>();
                            for(Platillo p: menuItems){
                                if(p.getIdCategoria() == position + 1){
                                    platillos.add(p);
                                }
                            }
                            adapter.setLista(platillos);
                            lvMenu.setAdapter(adapter);
                            String categoria = "[ " + listaCategorias.get(position).getCategoria() + " ]";
                            tvCategoria.setText(categoria);
                        }
                    });
                    recyclerView.setAdapter(adapter2);
                    String categoria = "[ " + listaCategorias.get(0).getCategoria() + " ]";
                    tvCategoria.setText(categoria);
                }else{
                    Toast.makeText(view.getContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Platillo platilloSeleccionado = (Platillo) parent.getItemAtPosition(position);
                VerDetallePlatillo fragment = new VerDetallePlatillo();
                Bundle b = new Bundle();
                b.putSerializable("PLATILLO", platilloSeleccionado);
                fragment.setArguments(b);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame1, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    private void enlazarControles(View view){
        lvMenu = view.findViewById(R.id.lvMenu);
        recyclerView = view.findViewById(R.id.recycler);
        tvCategoria = view.findViewById(R.id.tvCategoria);
    }
}