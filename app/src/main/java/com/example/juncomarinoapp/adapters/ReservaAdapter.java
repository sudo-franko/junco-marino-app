package com.example.juncomarinoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.juncomarinoapp.R;
import com.example.juncomarinoapp.modelo.dto.Pedido;
import com.example.juncomarinoapp.modelo.dto.ReservaMesa;

import java.util.ArrayList;

public class ReservaAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ReservaMesa> listaReservas;

    public ReservaAdapter(Context context, ArrayList<ReservaMesa> listaReservas) {
        this.context = context;
        this.listaReservas = listaReservas;
    }

    public void setLista(ArrayList<ReservaMesa> menuItems){
        this.listaReservas = menuItems;
    }

    @Override
    public int getCount() {
        return listaReservas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaReservas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_pedido, parent, false);
        }

        ReservaMesa item = (ReservaMesa) getItem(position);

        TextView idView = convertView.findViewById(R.id.tvIDReserva);
        TextView nombreView = convertView.findViewById(R.id.tvNombreUsuario);
        TextView infoView = convertView.findViewById(R.id.tvInfoReserva);

        idView.setText("PEDIDO (ID: " + item.getIdReserva() + ")");
        nombreView.setText("Usuario: " + item.getNomCliente());
        infoView.setText("MESA N° "+item.getNumMesa() + "  [" + item.getFecha() + "]");

        return convertView;
    }
}
