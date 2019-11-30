package com.rar.boozer.Actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.rar.boozer.Modelos.Usuario;
import com.rar.boozer.R;

public class ProfileActivity extends AppCompatActivity {

    private TextView nick, email, preferencias;
    private Button btnDelAcc;

    private Usuario usuario;

    private FirebaseAuth fbauth;
    private FirebaseDatabase fbdb;
    private FirebaseStorage fbstor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nick = findViewById(R.id.profileUser);
        email = findViewById(R.id.profileMail);
        preferencias = findViewById(R.id.profilePreferences);

        fbauth = FirebaseAuth.getInstance();
        final FirebaseUser fbuser = fbauth.getCurrentUser();

        Bundle bundle = getIntent().getExtras();
        usuario = (Usuario) bundle.getSerializable("userData");//TODO mostrar datos del usuario

        nick.setText(getString(R.string.user) + " " + usuario.getUsuario());
        email.setText(getString(R.string.email) + " " + usuario.getEmail());
        preferencias.setText(getString(R.string.preferences) + " " + usuario.getPreferencias());

        btnDelAcc = findViewById(R.id.btnDeleteAccount);

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