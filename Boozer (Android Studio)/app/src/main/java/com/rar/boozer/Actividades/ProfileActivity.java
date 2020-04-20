package com.rar.boozer.Actividades;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rar.boozer.Models.User;
import com.rar.boozer.R;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    private Button btnEditAcc;
    private Button btnConEditAcc;
    private Button btnCanEditAcc;

    private EditText editNick;

    private String uid;

    private User user;

    private FirebaseUser fbuser;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final TextView nickText, emailText;

        nickText = findViewById(R.id.profileUser);
        emailText = findViewById(R.id.profileMail);

        editNick = findViewById(R.id.editUser);

        user = new User();

        btnConEditAcc = findViewById(R.id.btnConfirmEditAccount);
        btnCanEditAcc = findViewById(R.id.btnCancelEditAccount);

        FirebaseAuth fbauth = FirebaseAuth.getInstance();

        fbuser = fbauth.getCurrentUser();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        uid = bundle.getString("userData");
        Log.i("wiwowiwo", uid);

        //Glide
        final ImageView loading = findViewById(R.id.loadingUser);
        Glide.with(this).load(R.drawable.loading).into(loading);

        //Get data from DynamoDB
        OkHttpClient client = new OkHttpClient();
        String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/getBoozerUser" +
                "?uid=" + uid;
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
            }

            //Get User
            @Override
            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                if (response.isSuccessful()) {
                    loading.setVisibility(View.INVISIBLE);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            assert response.body() != null;
                            String result = null;
                            try {
                                result = response.body().string().replaceAll("\"", "");
                                Log.i("ApiResult", result);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //Get name
                            String name = result.substring(result.indexOf("S: ") + 3, result.indexOf("}"));
                            nickText.setText(name);
                            editNick.setText(name);
                            user.setUser(name);
                            Log.i("ApiResult", "name: " + name);
                            result = result.substring(result.indexOf("}") + 3);
                            //Check if it's an admin
                            String isAdmin = result.substring(result.indexOf("BOOL: ") + 3, result.indexOf("}"));
                            Log.i("ApiResult", "isAdmin: " + isAdmin);
                            //Ignore Uid, as it won't be displayed
                            result = result.substring(result.indexOf("}") + 3);
                            result = result.substring(result.indexOf("}") + 3);
                            //Get email
                            String email = result.substring(result.indexOf("S: ") + 3, result.indexOf("}"));
                            emailText.setText(email);
                            user.setEmail(email);
                            Log.i("ApiResult", "email: " + email);
                        }
                    });
                }
            }
        });

        btnEditAcc = findViewById(R.id.btnEditAccount);

        btnEditAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nickText.setVisibility(View.GONE);
                emailText.setVisibility(View.GONE);

                editNick.setVisibility(View.VISIBLE);
                editNick.setEnabled(true);

                btnEditAcc.setVisibility(View.GONE);
                btnEditAcc.setEnabled(false);

                btnConEditAcc.setVisibility(View.VISIBLE);
                btnConEditAcc.setEnabled(true);

                btnCanEditAcc.setVisibility(View.VISIBLE);
                btnCanEditAcc.setEnabled(true);
            }
        });

        btnConEditAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get data from DynamoDB
                OkHttpClient client = new OkHttpClient();
                String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/createBoozerUser" +
                        "?uid=" + uid + "&email= " + user.getEmail() + "&user=" + editNick.getText();
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    }

                    //Create User
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                        if (response.isSuccessful()) {
                            runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    assert response.body() != null;
                                    try {
                                        Log.i("wiwowiwo", response.body().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                });
                finish();
                Intent intent = new Intent(ProfileActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });

        btnCanEditAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });

        Button btnDelAcc = findViewById(R.id.btnDeleteAccount);

        btnDelAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ProfileActivity.this, R.style.DialogTheme);
                builder.setTitle(R.string.btnDeleteAccount);
                builder.setCancelable(false);
                builder.setMessage(R.string.AlertDeleteAccount);
                builder.setNegativeButton(R.string.btnCancel, null);
                builder.setPositiveButton(R.string.btnConfirmDeleteAccount, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        fbuser.delete();

                        //Get data from DynamoDB
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

                            //Delete User
                            @Override
                            public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
                                if (response.isSuccessful()) {
                                    Snackbar.make(view, getResources().getText(R.string.toast_account_deleted), Snackbar.LENGTH_LONG).show();
                                    Intent intent = new Intent(ProfileActivity.this, IndexActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
                builder.show();
            }
        });
    }
}