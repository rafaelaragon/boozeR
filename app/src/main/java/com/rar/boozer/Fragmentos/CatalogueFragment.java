package com.rar.boozer.Fragmentos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.rar.boozer.R;

public class CatalogueFragment extends Fragment {

    private View recyclerView;

    public CatalogueFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        recyclerView = inflater.inflate(R.layout.fragment_catalogue, container, false);
        //Mostramos todas las bebidas
        //getAllDrinks(); TODO
        return recyclerView;
    }

    /*public void getAllDrinks() {

    }*/


}
