package com.rar.boozer.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rar.boozer.Adapters.DrinksAdapter;
import com.rar.boozer.Models.Drink;
import com.rar.boozer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FavoritesFragment extends Fragment {

    private List<Drink> drinks;
    private DrinksAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        final RecyclerView recyclerView = view.findViewById(R.id.drinksFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new DrinksAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        drinks = new ArrayList<>();

        //Glide
        final ImageView loading = view.findViewById(R.id.loadingDrink);
        Glide.with(this).load(R.drawable.loading).into(loading);

        //Uid
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        assert fbUser != null;
        final String uid = fbUser.getUid();

        //Get  user's favorite drinks from DynamoDB
        OkHttpClient client = new OkHttpClient();
        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerFavoriteDrinks" +
                "?uid=" + uid;
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    final String result = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //Get the name of the drink
                            JSONObject name = jsonObject.getJSONObject("name");
                            String nameString = name.getString("S");
                            //Get the image of the drink
                            JSONObject image = jsonObject.getJSONObject("image");
                            String imageString = image.getString("S");
                            //If the image is not valid use a generic one instead
                            String defImage = "https://boozerdrinks.s3.amazonaws.com/generic.png";
                            if (!imageString.contains("https://boozerdrinks.s3.amazonaws.com/"))
                                imageString = defImage;
                            //Add the drink to the fragment
                            Drink d = new Drink(nameString, imageString);
                            drinks.add(d);
                        }
                        //Call DrinksAdapter
                        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.SetList(drinks);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //If the user does not have favorites, inform it
                    if (result.length() < 4) {
                        loading.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
        registerForContextMenu(recyclerView);
        return view;
    }
}
