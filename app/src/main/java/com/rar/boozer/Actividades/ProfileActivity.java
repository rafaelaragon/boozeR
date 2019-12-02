package com.rar.boozer.Actividades;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rar.boozer.Modelos.Usuario;
import com.rar.boozer.R;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    private TextView nick, email, preferencias;
    private Button btnEditAcc;
    private Button btnConEditAcc;
    private Button btnCanEditAcc;

    private EditText editNick;
    private Spinner editPreferencias;

    private Usuario usuario;

    private FirebaseUser fbuser;

    private FirebaseDatabase fbdb;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nick = findViewById(R.id.profileUser);
        email = findViewById(R.id.profileMail);
        preferencias = findViewById(R.id.profilePreferences);

        editNick = findViewById(R.id.editUser);
        editPreferencias = findViewById(R.id.editPreferences);

        btnConEditAcc = findViewById(R.id.btnConfirmEditAccount);
        btnCanEditAcc = findViewById(R.id.btnCancelEditAccount);

        FirebaseAuth fbauth = FirebaseAuth.getInstance();
        final String uid = Objects.requireNonNull(fbauth.getCurrentUser()).getUid();

        fbuser = fbauth.getCurrentUser();
        fbdb = FirebaseDatabase.getInstance();

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        usuario = (Usuario) bundle.getSerializable("userData");

        nick.setText(getString(R.string.user) + " " + usuario.getUsuario());
        email.setText(getString(R.string.email) + " " + usuario.getEmail());
        preferencias.setText(getString(R.string.preferences) + " " + usuario.getPreferencias());


        btnEditAcc = findViewById(R.id.btnEditAccount);

        btnEditAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nick.setVisibility(View.GONE);
                email.setVisibility(View.GONE);
                preferencias.setVisibility(View.GONE);

                editNick.setVisibility(View.VISIBLE);
                editNick.setEnabled(true);
                editNick.setText(usuario.getUsuario());

                editPreferencias.setVisibility(View.VISIBLE);

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
                usuario.setUsuario(editNick.getText().toString());
                usuario.setPreferencias(editPreferencias.getSelectedItem().toString());

                fbdb.getReference().child("usuarios").child(uid).setValue(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), R.string.toast_account_updated, Toast.LENGTH_SHORT).show();
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), R.string.toast_login_error, Toast.LENGTH_SHORT).show();
                    }
                });

                endEdit();

                finish();
                startActivity(getIntent());
            }
        });

        btnCanEditAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endEdit();

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

                        DatabaseReference dbref = fbdb.getReference("usuarios");
                        dbref.child(uid).removeValue();

                        Snackbar.make(view, getResources().getText(R.string.toast_account_deleted), Snackbar.LENGTH_LONG).show();
                        Intent intent = new Intent(ProfileActivity.this, IndexActivity.class);
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }

    public void endEdit() {
        nick.setVisibility(View.VISIBLE);
        email.setVisibility(View.VISIBLE);
        preferencias.setVisibility(View.VISIBLE);

        editNick.setVisibility(View.GONE);
        editNick.setEnabled(false);

        editPreferencias.setVisibility(View.GONE);

        btnEditAcc.setVisibility(View.VISIBLE);
        btnEditAcc.setEnabled(true);

        btnConEditAcc.setVisibility(View.GONE);
        btnConEditAcc.setEnabled(false);

        btnCanEditAcc.setVisibility(View.GONE);
        btnCanEditAcc.setEnabled(false);
    }
}