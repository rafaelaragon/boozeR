package com.rar.boozer.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rar.boozer.Actividades.DrinkActivity;
import com.rar.boozer.Models.Drink;
import com.rar.boozer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.listHolder> {

    private List<Drink> list;
    private Context context;

    public DrinksAdapter(Context ctx) {
        list = new ArrayList<>();
        context = ctx;
    }

    public void SetList(List<Drink> data) {
        list = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public listHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_layout, parent, false);
        return new listHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listHolder holder, int position) {
        holder.BindHolder(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class listHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView name;
        private ImageView poster;

        listHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.itemName);
            poster = itemView.findViewById(R.id.itemImage);

            itemView.setOnCreateContextMenuListener(this);
        }

        void BindHolder(final Drink item) {


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("drinkName", item.getName());

                    Intent intent = new Intent(context, DrinkActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });

            name.setText(item.getName());

            Picasso.get()
                    .load(item.getImage())
                    .resize(400, 500)
                    .into(poster);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        }
    }

}
