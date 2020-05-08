package com.rar.boozer.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rar.boozer.Models.User;
import com.rar.boozer.R;
import com.rar.boozer.Services.ApiService;

import java.io.IOException;

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
            public void onResponse(@NonNull Call call, @NonNull final Response response) {
                if (response.isSuccessful()) {
                    loading.setVisibility(View.INVISIBLE);
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
                            //Get name
                            assert result != null;
                            String nameAux = result.substring(result.indexOf("User: {S: ") + 10);
                            String name = nameAux.substring(0, nameAux.indexOf("}"));
                            nickText.setText(name);
                            editNick.setText(name);
                            user.setUser(name);
                            //Check if it's an admin
                            String adminAux = result.substring(result.indexOf("isAdmin: {BOOL: ") + 16);
                            boolean isAdmin = Boolean.parseBoolean(adminAux.substring(0, adminAux.indexOf("}")));
                            //Change style of nick if the user is an admin
                            if (isAdmin) {
                                nickText.setTextColor(Color.YELLOW);
                                nickText.setTypeface(Typeface.DEFAULT_BOLD);
                            }
                            //Get email
                            String emAux = result.substring(result.indexOf("Email: {S: ") + 11);
                            String email = emAux.substring(0, emAux.indexOf("}"));
                            emailText.setText(email);
                            user.setEmail(email);

                            Log.i("ApiResult", result);
                            Log.i("ApiResult", "name: " + name);
                            Log.i("ApiResult", "isAdmin: " + isAdmin);
                            Log.i("ApiResult", "email: " + email);
                        }
                    });
                }
            }
        });

        btnEditAcc = findViewById(R.id.btnEditAccount);

        //Hide text and show edition inputs
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
                //Edit user from DynamoDB
                ApiService.createBoozerUser(uid, user.getEmail(), String.valueOf(editNick.getText()));

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
                        //Delete user from both firebase and DynamoDB
                        fbuser.delete();
                        ApiService.deleteBoozerUser(uid);

                        Snackbar.make(view, getResources().getText(R.string.toast_account_deleted), Snackbar.LENGTH_LONG).show();
                        Intent intent = new Intent(ProfileActivity.this, IndexActivity.class);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }
}