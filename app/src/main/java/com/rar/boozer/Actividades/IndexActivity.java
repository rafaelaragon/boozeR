package com.rar.boozer.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.rar.boozer.R;

public class IndexActivity extends AppCompatActivity {

    private Button btnLog, btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        btnLog = findViewById(R.id.btnGoLogin);
        btnReg = findViewById(R.id.btnGoRegister);

        btnLog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
                startActivity(intent) ;
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IndexActivity.this, RegisterActivity.class);
                startActivity(intent) ;
            }
        });
    }
}
