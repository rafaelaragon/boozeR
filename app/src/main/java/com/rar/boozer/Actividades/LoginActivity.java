package com.rar.boozer.Actividades;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rar.boozer.Modelos.Usuario;
import com.rar.boozer.R;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth fbauth;
    private FirebaseDatabase fbdatabase;

    private EditText email, pass;
    private final int LOG_CODE = 100;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fbauth = FirebaseAuth.getInstance();

        email = findViewById(R.id.userName);
        pass = findViewById(R.id.password);
        Button btnLog = findViewById(R.id.btnLogin);
        Button btnCancelLog = findViewById(R.id.logCancel);

        //TODO Temporal
        email.setText("admin@gmail.com");
        pass.setText("123456");

        //Volver al Menú
        btnCancelLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), R.string.toast_login_cancelled, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });

        //Inicio de Sesión
        btnLog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String ema = email.getText().toString();
                String pas = pass.getText().toString();

                fbauth.signInWithEmailAndPassword(ema, pas)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful())
                                    Toast.makeText(getApplicationContext(), R.string.toast_login_error, Toast.LENGTH_LONG).show();
                                else {
                                    String uid = Objects.requireNonNull(fbauth.getCurrentUser()).getUid();

                                    fbdatabase = FirebaseDatabase.getInstance();

                                    DatabaseReference userRef = fbdatabase.getReference().child("usuarios/" + uid);

                                    userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.hasChildren()) {
                                                // obtenemos datos del usuario logueado
                                                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                                                // creamos un diccionario
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("userData", usuario);

                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                // utilizando putExtra puedo almacenar información en la intención
                                                intent.putExtras(bundle);
                                                //intent.putExtra("usuario", usuario.getUsuario());
                                                startActivityForResult(intent, LOG_CODE);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            }

                        });
            }
        });

    }
}
