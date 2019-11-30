package com.rar.boozer.Adaptadores;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rar.boozer.Modelos.Bebida;
import com.rar.boozer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DrinksAdapter extends RecyclerView.Adapter<DrinksAdapter.listaHolder> {

    private List<Bebida> lista;
    private Context contexto;
    private int index;

    public DrinksAdapter(Context ctx) {
        lista = new ArrayList<>();
        contexto = ctx;
    }

    public void SetLista(List<Bebida> datos) {
        lista = datos;
        notifyDataSetChanged();
    }

    public int getIndex() {
        return index;
    }

    @Override
    public listaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(contexto)
                .inflate(R.layout.item_layout, parent, false);
        return new listaHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull listaHolder holder, int position) {
        holder.BindHolder(lista.get(position));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class listaHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView nombre;
        private ImageView poster;

        public listaHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.itemName);
            poster = itemView.findViewById(R.id.itemImage);

            itemView.setOnCreateContextMenuListener(this);
        }

        public void BindHolder(final Bebida item) {


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    index = listaHolder.this.getAdapterPosition();
                    return false;
                }
            });
            // Mostramos el título de la bebida     item.getNombre() TODO
            nombre.setText(item.getNombre());

            // mostramos la imagen de la bebida     item.getImagen() TODO
            Picasso.get()
                    .load(item.getImagen())
                    .resize(300, 444)
                    .into(poster);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        }
    }

}
