package com.example.juncomarinoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.juncomarinoapp.R;
import com.example.juncomarinoapp.modelo.dto.DetallePedido;

import java.util.List;

public class DetallePedidoAdapter extends ArrayAdapter<DetallePedido> {
    private Context context;
    private List<DetallePedido> detalles;

    public DetallePedidoAdapter(Context context, List<DetallePedido> detalles) {
        super(context, 0, detalles);
        this.context = context;
        this.detalles = detalles;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DetallePedido dp = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_detalle, parent, false);
        }

        TextView tvNombrePlatillo = convertView.findViewById(R.id.tvNombrePlatillo);
        TextView tvCantidadPlatillo = convertView.findViewById(R.id.tvCantidadPlatillo);
        TextView tvPrecioPlatillo = convertView.findViewById(R.id.tvPrecioPlatillo);

        tvNombrePlatillo.setText(dp.getNombrePlatillo());
        tvCantidadPlatillo.setText("Cantidad: " + dp.getCantidad());
        tvPrecioPlatillo.setText("S/. " + dp.getSubtotal());

        return convertView;
    }
}

