package com.rar.boozer.Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.Slider;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rar.boozer.Activities.MainActivity;
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


public class CatalogueFragment extends Fragment {

    private List<Drink> drinks;

    private DrinksAdapter adapter;

    private SearchView searchView = null;

    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_catalogue, container, false);

        final RecyclerView recyclerView = view.findViewById(R.id.drinksCatalogue);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new DrinksAdapter(getActivity());
        recyclerView.setAdapter(adapter);

        drinks = new ArrayList<>();

        Intent intent = Objects.requireNonNull(getActivity()).getIntent();
        String value = intent.getStringExtra("request");
        Log.i("boozerApi", "request: " + value);

        //Glide
        final ImageView loading = view.findViewById(R.id.loadingDrink);
        Glide.with(this).load(R.drawable.loading).into(loading);

        //uid
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        assert fbUser != null;
        final String uid = fbUser.getUid();

        final Button btnFilter = view.findViewById(R.id.btnFilter);
        //Adjust dialog
        final Context context = this.getActivity();

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                assert context != null;
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context, R.style.DialogTheme);
                builder.setCancelable(true);
                final LayoutInflater factory = getLayoutInflater();
                final View alertView = factory.inflate(R.layout.adjust_layout, container, false);
                builder.setView(alertView);

                //Spinner
                final Spinner typesSpinner = alertView.findViewById(R.id.typesSpinner);
                final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                        R.array.spinner_types, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                typesSpinner.setAdapter(adapter);
                final String[] type = {""};
                typesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        type[0] = "" + parent.getItemAtPosition(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //Max Price/Litre Slider
                final TextView maxPrice = alertView.findViewById(R.id.priceText);
                final Slider maxPriceSlider = alertView.findViewById(R.id.sliderPrice);
                maxPriceSlider.setOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(Slider slider, float value) {
                        String price = String.format("%.2f", value) + " €/l";
                        if (value != 50) {
                            maxPrice.setText("Max: " + price);
                        } else {
                            maxPrice.setText("Max: +" + price);
                        }
                    }
                });

                //Max Vol Slider
                final TextView maxVol = alertView.findViewById(R.id.maxVolText);
                final Slider maxVolSlider = alertView.findViewById(R.id.sliderVol);
                maxVolSlider.setOnChangeListener(new Slider.OnChangeListener() {
                    @Override
                    public void onValueChange(Slider slider, float value) {
                        maxVol.setText("Max: " + String.format("%d", (long) value) + "%");
                    }
                });

                //Blacklist Switch
                final SwitchMaterial blacklist = alertView.findViewById(R.id.switchMaterial);

                builder.setNegativeButton(R.string.btnCancel, null);
                builder.setPositiveButton(R.string.btnApply, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Restart this fragment with the api request in a bundle
                        String showBlacklist = blacklist.isChecked() ? "True" : "False"; //Python hates java booleans
                        Log.i("wiwowiwo", showBlacklist);
                        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerDrinks" +
                                "?uid=" + uid + "&type=" + type[0] + "&price=" + maxPriceSlider.getValue() +
                                "&vol=" + maxVolSlider.getValue() + "&blacklist=" + showBlacklist;
                        Intent myIntent = new Intent(getActivity(), MainActivity.class);
                        myIntent.putExtra("request", url);
                        startActivity(myIntent);
                    }
                });
                builder.show();
            }
        });

        //Get drinks from DyanmoDB
        OkHttpClient client = new OkHttpClient();
        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerDrinks" +
                "?uid=" + uid + "&type=Cualquiera&price=50.0&vol=100.0&blacklist=False";
        //If bundle is not empty, change the url to the one given from the bundle
        if (value != null) url = value;
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
                        Log.i("boozerApi", "lista: " + drinks.toString());
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
                    //If no drinks were found, wait 3 seconds and restart
                    if (result.length() < 4) {
                        loading.setVisibility(View.INVISIBLE);
                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerDrinks" +
                                        "?uid=" + uid + "&type=Cualquiera&price=50.0&vol=100.0&blacklist=False";
                                Intent myIntent = new Intent(getActivity(), MainActivity.class);
                                myIntent.putExtra("request", url);
                                startActivity(myIntent);
                            }
                        }, 2000);
                    }
                }
            }
        });
        registerForContextMenu(recyclerView);
        return view;
    }

    //The rest of the code is used to prepare the searchbar

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, @NonNull MenuInflater inflater) {
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchManager searchManager = (SearchManager) Objects.requireNonNull
                (getActivity()).getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            assert searchManager != null;
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            //Restart this fragment with the api request in a bundle
            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }

                //Restart this fragment with the api request in a bundle
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);
                    String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerSearchDrinks" +
                            "?search=" + query;
                    Intent myIntent = new Intent(getActivity(), MainActivity.class);
                    myIntent.putExtra("request", url);
                    startActivity(myIntent);
                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

}
