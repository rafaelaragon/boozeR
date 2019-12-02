package com.rar.boozer.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.rar.boozer.Fragmentos.CalculatorFragment;
import com.rar.boozer.Fragmentos.CatalogueFragment;
import com.rar.boozer.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth fbauth;

    private BottomNavigationView bnv;
    private Fragment catalogueFragment = new CatalogueFragment();
    private Fragment calculatorFragment = new CalculatorFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnv = findViewById(R.id.bottom_menu);

        fbauth = FirebaseAuth.getInstance();

        //Por defecto, carga el fragmento del catálogo
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutMain, catalogueFragment).commit();

        //Activación de los fragmentos
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottomCatalogue:
                        if (!isLoaded(catalogueFragment.getClass().getSimpleName(), R.id.bottomCatalogue)) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.layoutMain, catalogueFragment).commit();
                        }
                        break;
                    case R.id.bottomCalculator:
                        if (!isLoaded(calculatorFragment.getClass().getSimpleName(), R.id.bottomCalculator)) {
                            getSupportFragmentManager().beginTransaction().replace(R.id.layoutMain, calculatorFragment).commit();
                        }
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuProfile:

                Bundle bundle = getIntent().getExtras();
                Intent intentProfile = new Intent(MainActivity.this, ProfileActivity.class);
                intentProfile.putExtras(bundle);
                startActivity(intentProfile);
                return true;

            case R.id.mnuLogout:

                fbauth.signOut();
                setResult(0);
                Intent intentLogout = new Intent(MainActivity.this, IndexActivity.class);
                startActivity(intentLogout);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isLoaded(String fClass, int id) {
        Fragment f = getSupportFragmentManager().findFragmentById(id);
        return (f != null) && (f.getClass().getSimpleName().equals(fClass));
    }

}
