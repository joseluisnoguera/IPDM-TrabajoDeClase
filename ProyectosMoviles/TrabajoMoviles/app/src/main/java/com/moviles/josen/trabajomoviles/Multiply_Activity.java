package com.moviles.josen.trabajomoviles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Multiply_Activity extends AppCompatActivity {

    private TextView mult_1 = null;
    private TextView mult_2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiply_layout);
        Button multiply = findViewById(R.id.multiplyBtn);
        mult_1 = findViewById(R.id.mult_1);
        mult_2 = findViewById(R.id.mult_2);
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_value_1 = mult_1.getText().toString();
                String str_value_2 = mult_2.getText().toString();
                double value_1 = (str_value_1.length()>0)? Double.parseDouble(str_value_1) : 0;
                double value_2 = (str_value_2.length()>0)? Double.parseDouble(str_value_2) : 0;
                Intent responseIntent = new Intent();
                responseIntent.putExtra("result", (value_1 * value_2));
                setResult(RESULT_OK, responseIntent);
                finish();
            }
        });
    }
}
