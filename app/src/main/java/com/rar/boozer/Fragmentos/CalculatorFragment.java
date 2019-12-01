package com.rar.boozer.Fragmentos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rar.boozer.R;

import org.w3c.dom.Text;

public class CalculatorFragment extends Fragment {

    ListView listView;
    FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
    DatabaseReference dbref;


    public CalculatorFragment() {

    }

    private Button button;
    private TextView height, weight, vol, quantity, result;
    private RadioButton man, woman;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        man = (RadioButton) view.findViewById(R.id.radio_masculino);
        woman = (RadioButton) view.findViewById(R.id.radio_femenino);
        weight = (TextView) view.findViewById(R.id.fragmentWeight);
        vol = (TextView) view.findViewById(R.id.fragmentVol);
        quantity = (TextView) view.findViewById(R.id.fragmentQuantity);
        result = (TextView) view.findViewById(R.id.fragmentResultNumber);
        button = (Button) view.findViewById(R.id.btnCalculate);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Float vo = Float.parseFloat(vol.getText().toString());
                Float qu = Float.parseFloat(quantity.getText().toString());
                double A = qu * vo /100 * 0.8;

                double r = 0;
                if (man.isChecked()) {
                     r = 0.70;
                } else if (woman.isChecked()) {
                     r = 0.60;
                }
                Float m = Float.parseFloat(weight.getText().toString());

                double res  = Double.parseDouble(result.getText().toString().substring(0, result.getText().length() - 1));
                Log.i("alcohol", "Resultado: "+ res);
                res += Math.ceil(A / (r * m) * 100) / 100; //TODO arreglar Math.ceil.
                result.setText(res + "%");
            }
        });

        return view;
    }

}
