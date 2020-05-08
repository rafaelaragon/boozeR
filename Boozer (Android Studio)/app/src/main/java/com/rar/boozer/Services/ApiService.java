package com.rar.boozer.Services;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiService {

    //Create or edit user from DynamoDB
    public static void createBoozerUser(String uid, String email, String user) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/createBoozerUser" +
                "?uid=" + uid + "&email=" + email + "&user=" + user;
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) {
            }
        });
    }

    //Delete user from DynamoDB
    public static void deleteBoozerUser(String uid) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/deleteBoozerUser" +
                "?uid=" + uid;
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) {
            }
        });
    }

    //Add drink to the user's favorites list
    public static void addDrinkToFavorites(String uid, String drink) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/addBoozerDrinkToFavorites" +
                "?uid=" + uid + "&drink=" + drink;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) {
            }
        });
    }

    //Remove drink from the user's favorites list
    public static void deleteDrinkFromFavorites(String uid, String drink) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/deleteBoozerDrinkFromFavorites" +
                "?uid=" + uid + "&drink=" + drink;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) {
            }
        });
    }

    //Add drink to the user's blacklist
    public static void addDrinkToBlacklist(String uid, String drink) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/addBoozerDrinkToBlacklist" +
                "?uid=" + uid + "&drink=" + drink;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) {
            }
        });
    }

    //Remove drink to the user's blacklist
    public static void deleteDrinkFromBlacklist(String uid, String drink) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/deleteBoozerDrinkFromBlacklist" +
                "?uid=" + uid + "&drink=" + drink;
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) {
            }
        });
    }
}
