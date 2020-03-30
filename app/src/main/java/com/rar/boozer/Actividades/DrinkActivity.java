package com.rar.boozer.Actividades;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.rar.boozer.R;
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
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        String drink = (String) bundle.getSerializable("drinkName");

        final TextView nameTextView = findViewById(R.id.drinkName);
        final TextView detailsTextView = findViewById(R.id.drinkDetails);
        final TextView priceTextView = findViewById(R.id.drinkPrice);
        final ImageView imageView = findViewById(R.id.drinkImage);
        final TextView typeTextView = findViewById(R.id.drinkType);
        final TextView graduationTextView = findViewById(R.id.drinkGraduation);

        //Get data from DynamoDB
        OkHttpClient client = new OkHttpClient();
        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerDrink?name=" + drink;
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            //Get the Drink
            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (response.isSuccessful()) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            assert response.body() != null;
                            String result = null;
                            try {
                                result = response.body().string().replaceAll("\"", "");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //Get details
                            String details = result.substring(result.indexOf("S: ") + 3, result.indexOf("}"));
                            if (!details.equals("none"))  detailsTextView.setText(details);
                            Log.i("ApiResult", "details: " + details);
                            result = result.substring(result.indexOf("}") + 3);
                            //Get price
                            String price = result.substring(result.indexOf("N: ") + 3, result.indexOf("}"));
                            priceTextView.setText(price + " €");
                            Log.i("ApiResult", "price: " + price);
                            result = result.substring(result.indexOf("}") + 3);
                            //Get name
                            String name = result.substring(result.indexOf("S: ") + 3, result.indexOf("}"));
                            nameTextView.setText(name);
                            Log.i("ApiResult", "name: " + name);
                            result = result.substring(result.indexOf("}") + 3);
                            //Get image
                            String image = result.substring(result.indexOf("S: ") + 3, result.indexOf("}"));
                            Picasso.get()
                                    .load(image)
                                    .resize(800, 1200)
                                    .into(imageView);
                            imageView.setContentDescription(name);
                            Log.i("ApiResult", "image: " + image);
                            result = result.substring(result.indexOf("}") + 3);
                            //Get type
                            String type = result.substring(result.indexOf("S: ") + 3, result.indexOf("}"));
                            typeTextView.setText(type);
                            Log.i("ApiResult", "type: " + type);
                            result = result.substring(result.indexOf("}") + 3);
                            //Get graduation
                            String graduation = result.substring(result.indexOf("N: ") + 3, result.indexOf("}"));
                            graduationTextView.setText(graduation + "%");
                            Log.i("ApiResult", "graduation: " + graduation);
                        }
                    });
                }
            }
        });
    }
}
