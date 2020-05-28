package com.rar.boozer.Activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rar.boozer.R;
import com.rar.boozer.Services.ApiService;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        final Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        final String drink = (String) bundle.getSerializable("drinkName");

        //Glide
        final ImageView loading = findViewById(R.id.loadingDrink);
        Glide.with(this).load(R.drawable.loading).into(loading);

        final TextView nameTextView = findViewById(R.id.drinkName);
        final TextView detailsTextView = findViewById(R.id.drinkDetails);
        final TextView priceTextView = findViewById(R.id.drinkPrice);
        final ImageView imageView = findViewById(R.id.drinkImage);
        final TextView typeTextView = findViewById(R.id.drinkType);
        final TextView graduationTextView = findViewById(R.id.drinkGraduation);
        final Button addFavBtn = findViewById(R.id.btnToggleFav);
        final Button delFavBtn = findViewById(R.id.btnToggleDis);

        //Get data from DynamoDB
        OkHttpClient client = new OkHttpClient();
        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerDrink?name=" + drink;
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            //Get the Drink
            public void onResponse(@NonNull Call call, @NonNull final Response response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            loading.setVisibility(View.INVISIBLE);
                            addFavBtn.setVisibility(View.VISIBLE);
                            delFavBtn.setVisibility(View.VISIBLE);

                            assert response.body() != null;
                            String result = null;
                            try {
                                result = response.body().string().replaceAll("\"", "");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //Get details
                            assert result != null;
                            String detAux = result.substring(result.indexOf("details: {S: ") + 13);
                            String details = detAux.substring(0, detAux.indexOf("}"));
                            //If details equals "none", hide them
                            details = fixString(details);
                            if (!details.equals("none")) detailsTextView.setText(details);
                            //Get price
                            String priceAux = result.substring(result.indexOf("price: {N: ") + 11);
                            String price = priceAux.substring(0, priceAux.indexOf("}"));
                            priceTextView.setText(price + " €");
                            //Get name
                            String nameAux = result.substring(result.indexOf("name: {S: ") + 10);
                            String name = nameAux.substring(0, nameAux.indexOf("}"));
                            name = fixString(name);
                            nameTextView.setText(name);
                            //Get image
                            String imgAux = result.substring(result.indexOf("image: {S: ") + 11);
                            String image = imgAux.substring(0, imgAux.indexOf("}"));
                            //If the image is not valid use a generic one instead
                            String defImage = "https://boozerdrinks.s3.amazonaws.com/generic.png";
                            if (!image.contains("https://boozerdrinks.s3.amazonaws.com/"))
                                image = defImage;

                            Picasso.get()
                                    .load(image)
                                    .resize(800, 1200)
                                    .into(imageView);
                            imageView.setContentDescription(name);
                            //Get type
                            String typeAux = result.substring(result.indexOf("type: {S: ") + 10);
                            String type = typeAux.substring(0, typeAux.indexOf("}"));
                            typeTextView.setText(type);
                            //Get graduation
                            String gradAux = result.substring(result.indexOf("graduation: {N: ") + 16);
                            String graduation = gradAux.substring(0, gradAux.indexOf("}"));
                            graduationTextView.setText(graduation + "%");
                        }
                    });
                }
            }
        });

        //Check favorites and blacklisted drinks of the user
        final String[] favorites = {""};
        final String[] blackList = {""};
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        assert fbUser != null;
        final String uid = fbUser.getUid();
        OkHttpClient userClient = new OkHttpClient();
        String userUrl = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerUser" +
                "?uid=" + uid;
        Request userRequest = new Request.Builder()
                .url(userUrl)
                .build();

        userClient.newCall(userRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            //Get User
            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            assert response.body() != null;
                            String userResult = null;
                            try {
                                userResult = response.body().string().replaceAll("\"", "");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            assert userResult != null;
                            String blackListAux = userResult.substring(userResult.indexOf("Blacklisted: {L: [") + 18);
                            blackList[0] = blackListAux.substring(0, blackListAux.indexOf("]"));
                            //If this drink is blacklisted, tint the button and prevent favorites
                            assert drink != null;
                            if (blackList[0].contains(drink)) {
                                delFavBtn.setAlpha(1);
                                addFavBtn.setEnabled(false);
                            }
                            String favoritesAux = userResult.substring(userResult.indexOf("Favorites: {L: [") + 16);
                            favorites[0] = favoritesAux.substring(0, favoritesAux.indexOf("]"));
                            //If this drink is favorited, tint the button and prevent blacklist
                            if (favorites[0].contains(drink)) {
                                addFavBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getResources()
                                        .getColor(R.color.favorite)));
                                delFavBtn.setEnabled(false);
                            }
                        }
                    });
                }
            }
        });

        addFavBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //if drink was not favorite
                assert drink != null;
                if (!favorites[0].contains(drink)) {
                    Toast.makeText(getApplicationContext(), drink + " añadido a favoritos", Toast.LENGTH_LONG).show();
                    addFavBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.favorite)));
                    ApiService.addDrinkToFavorites(uid, drink);
                } /*if drink was favorite*/ else {
                    Toast.makeText(getApplicationContext(), drink + " eliminado de favoritos", Toast.LENGTH_LONG).show();
                    addFavBtn.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(R.color.disabled)));
                    ApiService.deleteDrinkFromFavorites(uid, drink);
                }
                //Block both buttons
                addFavBtn.setEnabled(false);
                delFavBtn.setEnabled(false);

                final Intent myIntent = new Intent(DrinkActivity.this, MainActivity.class);
                String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerDrinks" +
                        "?uid=" + uid + "&type=Cualquiera&price=50.0&vol=100.0&blacklist=False";
                myIntent.putExtra("request", url);
                finish();
                startActivity(myIntent);
            }
        });

        delFavBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert drink != null;
                if (!blackList[0].contains(drink)) {
                    Toast.makeText(getApplicationContext(), drink + " eliminado de la lista", Toast.LENGTH_LONG).show();
                    delFavBtn.setAlpha(1);
                    ApiService.addDrinkToBlacklist(uid, drink);
                } /*if drink was blacklisted*/ else {
                    Toast.makeText(getApplicationContext(), drink + " devuelto a la lista", Toast.LENGTH_LONG).show();
                    delFavBtn.setAlpha((float) .25);
                    ApiService.deleteDrinkFromBlacklist(uid, drink);
                }
                //Block both buttons
                addFavBtn.setEnabled(false);
                delFavBtn.setEnabled(false);

                final Intent myIntent = new Intent(DrinkActivity.this, MainActivity.class);
                finish();
                startActivity(myIntent);
            }
        });
    }

    public static String fixString(String unicodeString) {
        int length = unicodeString.length();
        //Remove unicode prefix
        unicodeString = unicodeString.replaceAll("\\\\u00", "");
        //Replace unicode chars with UTF-8
        unicodeString = unicodeString.replaceAll("f1", "ñ");
        unicodeString = unicodeString.replaceAll("c1", "Á");
        unicodeString = unicodeString.replaceAll("e1", "á");
        unicodeString = unicodeString.replaceAll("e4", "ä");
        unicodeString = unicodeString.replaceAll("c9", "É");
        unicodeString = unicodeString.replaceAll("e9", "é");
        unicodeString = unicodeString.replaceAll("cd", "Í");
        unicodeString = unicodeString.replaceAll("ed", "í");
        unicodeString = unicodeString.replaceAll("d3", "Ó");
        unicodeString = unicodeString.replaceAll("f3", "ó");
        unicodeString = unicodeString.replaceAll("da", "Ú");
        unicodeString = unicodeString.replaceAll("fa", "ú");
        return unicodeString;
    }
}

