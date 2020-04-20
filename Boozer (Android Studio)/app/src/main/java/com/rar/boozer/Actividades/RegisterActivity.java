package com.rar.boozer.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rar.boozer.Models.User;
import com.rar.boozer.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, pass, confirmPass, user;

    private FirebaseAuth fbauth;
    private FirebaseDatabase fbdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fbauth = FirebaseAuth.getInstance();

        fbdatabase = FirebaseDatabase.getInstance();

        email = findViewById(R.id.regEmail);
        pass = findViewById(R.id.regPassword);
        confirmPass = findViewById(R.id.regConfirm);
        user = findViewById(R.id.regUser);

        Button btnReg = findViewById(R.id.btnRegister);
        Button btnCancelReg = findViewById(R.id.regCancel);

        //Volver al Menú
        btnCancelReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), R.string.toast_register_cancelled, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ema = email.getText().toString();
                final String pas = pass.getText().toString();
                final String conPas = confirmPass.getText().toString();
                final String usr = user.getText().toString();

                //Revisa que el formulario esté relleno
                if (ema.isEmpty() || usr.isEmpty() || pas.isEmpty() ||
                        conPas.isEmpty()) {
                    Snackbar.make(view, getResources().getText(R.string.toast_error_empty), Snackbar.LENGTH_LONG).show();
                    return;
                }
                //Revisa que las contraseñas coincidan
                if (!pas.equals(conPas)) {
                    Snackbar.make(view, getResources().getText(R.string.toast_error_password), Snackbar.LENGTH_LONG).show();
                    return;
                }
                //Revisa que la contraseña mida al menos 6 caracteres
                if (pas.length() < 6) {
                    Snackbar.make(view, getResources().getText(R.string.toast_error_password_length), Snackbar.LENGTH_LONG).show();
                }
                //Revisa que se use una dirección de correo válida
                if (!isEmailValid(ema)) {
                    Snackbar.make(view, getResources().getText(R.string.toast_error_email), Snackbar.LENGTH_LONG).show();
                }

                fbauth.createUserWithEmailAndPassword(ema, pas)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), R.string.toast_login_error, Toast.LENGTH_LONG).show();
                                } else {
                                    // Get user's UID
                                    String uid = fbauth.getUid();

                                    User user = new User(usr, ema, false);
                                    assert uid != null;

                                    //Get data from DynamoDB
                                    OkHttpClient client = new OkHttpClient();
                                    String url = "https://t08nzfqhxk.execute-api.us-east-1.amazonaws.com/default/createBoozerUser" +
                                            "?uid=" + uid + "&email= " + user.getEmail() + "&user=" + user.getUser();
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
                                    fbauth.signOut();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}
