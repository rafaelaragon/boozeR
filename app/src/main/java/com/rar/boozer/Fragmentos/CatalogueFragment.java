package com.rar.boozer.Fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rar.boozer.Adaptadores.DrinksAdapter;
import com.rar.boozer.Modelos.Bebida;
import com.rar.boozer.R;

import java.util.ArrayList;
import java.util.List;

public class CatalogueFragment extends Fragment {

    private List<Bebida> bebidas;

    private RecyclerView recyclerView;

    public CatalogueFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View vista = inflater.inflate(R.layout.fragment_catalogue, container, false);
       recyclerView = vista.findViewById(R.id.drinksCatalogue);

       DrinksAdapter adaptador = new DrinksAdapter(getActivity());

       //recyclerView.setLayoutManager();
        bebidas = new ArrayList<Bebida>() {
            {
                add(new Bebida("Jägermeister", "manolo", (float) 3.5, (float) 45.0, "blblbal", "https://firebasestorage.googleapis.com/v0/b/boozer-f29ce.appspot.com/o/bebidas%2Fjagermeister.png?alt=media&token=87133b60-b3a3-428c-a24f-f6c282c7a203"));
            }
        };

        adaptador.SetLista(bebidas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adaptador);

        registerForContextMenu(recyclerView);

        //TextView nombre = (TextView) getView().findViewById(R.id.itemName);

        //Mostramos todas las bebidas
        //getAllDrinks(); TODO




        return vista;
    }

    public void getAllDrinks() {

    }


}
