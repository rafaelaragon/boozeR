package com.rar.boozer.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.rar.boozer.R;

public class CalculatorFragment extends Fragment {

    private TextView weight, vol, quantity, result, warning;
    private RadioButton man, woman;
    private double res;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        man = view.findViewById(R.id.radio_masculino);
        woman = view.findViewById(R.id.radio_femenino);
        weight = view.findViewById(R.id.fragmentWeight);
        vol = view.findViewById(R.id.fragmentVol);
        quantity = view.findViewById(R.id.fragmentQuantity);
        result = view.findViewById(R.id.fragmentResultNumber);
        warning = view.findViewById(R.id.fragmentWarning);

        Button button = view.findViewById(R.id.btnCalculate);
        Button reset = view.findViewById(R.id.btnReset);

        //Calcula cantidad de alcohol ingerida
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Float vo = Float.parseFloat(vol.getText().toString());
                Float qu = Float.parseFloat(quantity.getText().toString());
                double A = qu * vo / 100 * 0.8;

                double r = 0.0;
                if (man.isChecked()) {
                    r = 0.70;
                } else if (woman.isChecked()) {
                    r = 0.60;
                }
                float m = Float.parseFloat(weight.getText().toString());

                res = Double.parseDouble(result.getText().toString().substring(0, result.getText().length() - 1));
                res += A / (r * m);
                res = Math.round(res * 100.0) / 100.0;
                result.setText(res + "%");

                //Comprobación de advertencias
                if (res >= 0.3) warning.setText(R.string.warning_drive_new);
                if (res >= 0.5) warning.setText(R.string.warning_drive);
                if (res >= 1.0) warning.setText(R.string.warning_nausea);
                if (res >= 2.0) warning.setText(R.string.warning_blackout);
                if (res >= 3.0) warning.setText(R.string.warning_stupor);
                if (res >= 4.0) warning.setText(R.string.warning_coma);
                if (res >= 5.0) warning.setText(R.string.warning_rip);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                res = 0;
                result.setText(res + "%");
                warning.setText("");
            }
        });

        return view;
    }

}
