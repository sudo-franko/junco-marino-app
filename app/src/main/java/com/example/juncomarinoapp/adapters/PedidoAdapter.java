package com.example.juncomarinoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juncomarinoapp.R;
import com.example.juncomarinoapp.modelo.dto.Pedido;

import java.util.ArrayList;

public class PedidoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Pedido> listaPedidos;

    public PedidoAdapter(Context context, ArrayList<Pedido> listaPedidos) {
        this.context = context;
        this.listaPedidos = listaPedidos;
    }

    public void setLista(ArrayList<Pedido> menuItems){
        this.listaPedidos = menuItems;
    }

    @Override
    public int getCount() {
        return listaPedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPedidos.get(position);
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

        Pedido item = (Pedido) getItem(position);

        TextView idView = convertView.findViewById(R.id.tvIDPedido);
        TextView nombreView = convertView.findViewById(R.id.tvNombreUsuario);
        TextView montoView = convertView.findViewById(R.id.tvMontoPedido);

        idView.setText("PEDIDO (ID: " + item.getIdPedido() + ")");
        nombreView.setText("Usuario: " + item.getNombreCliente());
        montoView.setText("S/." + item.getMonto());

        return convertView;
    }
}
