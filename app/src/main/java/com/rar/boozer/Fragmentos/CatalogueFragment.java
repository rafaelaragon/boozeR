package com.rar.boozer.Fragmentos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rar.boozer.Adaptadores.DrinksAdapter;
import com.rar.boozer.Modelos.Bebida;
import com.rar.boozer.R;

import java.util.ArrayList;
import java.util.List;

public class CatalogueFragment extends Fragment {

    private List<Bebida> bebidas;

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View vista = inflater.inflate(R.layout.fragment_catalogue, container, false);
       recyclerView = vista.findViewById(R.id.drinksCatalogue);

       DrinksAdapter adaptador = new DrinksAdapter(getActivity());

        bebidas = new ArrayList<Bebida>();
        Bebida obligatoria = new Bebida("Lista de bebidas", "piiiiiipo", (float) 4.0, (float) 2.5, "wiowwio", "https://dqzrr9k4bjpzk.cloudfront.net/images/18481071/1171871437.jpg");
        bebidas.add(obligatoria);
        Log.i("mfirebase", "Id de la imborrable: " + bebidas.indexOf(obligatoria));
        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
        final DatabaseReference dbref = fbdb.getReference("bebidas/");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot dSnap : dataSnapshot.getChildren()) {
                        i++;
                        Log.i("mfirebase", "Número de ciclos: " + i);
                        Bebida b = dSnap.getValue(Bebida.class);
                        bebidas.add(b);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adaptador.SetLista(bebidas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adaptador);
        registerForContextMenu(recyclerView);

        return vista;
    }

}
