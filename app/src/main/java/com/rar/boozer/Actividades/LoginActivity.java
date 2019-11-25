package com.rar.boozer.Actividades;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rar.boozer.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth fbauth;
    private FirebaseDatabase fbdatabase;

    private EditText email, pass;
    private Button btnLog, btnCancelLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fbauth = FirebaseAuth.getInstance();

        email = findViewById(R.id.userName);
        pass = findViewById(R.id.password);
        btnLog = findViewById(R.id.btnLogin);
        btnCancelLog = findViewById(R.id.logCancel);

        // TEMPORAL TODO
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
                                    String uid = fbauth.getCurrentUser().getUid();

                                    fbdatabase = FirebaseDatabase.getInstance();

                                    DatabaseReference userRef = fbdatabase.getReference().child("usuarios/" + uid);
                                    Toast.makeText(getApplicationContext(), "Hola, " + uid, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);

                                    /*userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.hasChildren()) {
                                                // obtenemos datos del usuario logueado
                                                Usuario usuario = dataSnapshot.getValue(Usuario.class);

                                                // creamos un diccionario
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable("_usuario", usuario);

                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                // utilizando putExtra puedo almacenar información en la intención
                                                intent.putExtras(bundle);
                                                intent.putExtra("usuario", usuario.getUsuario()) ;
                                                //startActivity(intent);
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });*/

                                }

                            }

                        });
            }
        });

    }
}
