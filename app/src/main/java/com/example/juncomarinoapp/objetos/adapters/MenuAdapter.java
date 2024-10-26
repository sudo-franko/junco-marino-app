package com.example.juncomarinoapp.objetos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juncomarinoapp.R;
import com.example.juncomarinoapp.objetos.dto.Platillo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Platillo> menuItems;

    public MenuAdapter(Context context, ArrayList<Platillo> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_menu, parent, false);
        }

        Platillo item = (Platillo) getItem(position);

        ImageView imageView = convertView.findViewById(R.id.item_image);
        TextView nameView = convertView.findViewById(R.id.item_name);
        TextView descriptionView = convertView.findViewById(R.id.item_description);
        TextView priceView = convertView.findViewById(R.id.item_price);

        String imageUrl = "http://192.168.1.37:4000/imagenPlatillo/"+ item.getImageResId();


        Picasso.get().load(imageUrl).into(imageView);

        //imageView.setImageResource(item.getImageResId());
        nameView.setText(item.getName());
        descriptionView.setText(item.getDescription());
        priceView.setText("S/." + item.getPrice());

        return convertView;
    }
}
