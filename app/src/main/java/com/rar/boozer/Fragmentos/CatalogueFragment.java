package com.rar.boozer.Fragmentos;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rar.boozer.Actividades.MainActivity;
import com.rar.boozer.Adaptadores.DrinksAdapter;
import com.rar.boozer.Modelos.Bebida;
import com.rar.boozer.Modelos.Usuario;
import com.rar.boozer.R;

import java.util.zip.Inflater;

public class CatalogueFragment extends Fragment {

    private View recyclerView;

    public CatalogueFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = inflater.inflate(R.layout.item_layout, container, false);
        //Mostramos todas las bebidas
        //getAllDrinks(); TODO
        return recyclerView;
    }

    /*public void getAllDrinks() {

    }*/


}
