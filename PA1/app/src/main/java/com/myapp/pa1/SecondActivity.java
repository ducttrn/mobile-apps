package com.myapp.pa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private static final String kilogram = "Kilograms (Kg)";
    private static final String pound = "Pounds (lb)";
    private static final String kilometer = "Kilometers (Km)";
    private static final String mile = "Miles (mi)";
    private static final int DEFAULT_VALUE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        int conversion_type_id = intent.getIntExtra("conversion_type_id", DEFAULT_VALUE);

        TextView title = findViewById(R.id.conversion_title);
        EditText input_box = findViewById(R.id.input_box);
        String in;
        String out;
        if (conversion_type_id == 1) {
            in = kilogram;
            out = pound;
        }
        else if (conversion_type_id == 2) {
            in = pound;
            out = kilogram;
        }
        else if (conversion_type_id == 3) {
            in = kilometer;
            out = mile;
        }
        else {
            in = mile;
            out = kilometer;
        }

        title.setText(getString(R.string.title, in, out));
        input_box.setHint(getString(R.string.input_hint, in));

        initConvertButton(conversion_type_id, out);
        initStartOverButton();
    }

    private void initConvertButton(int conversion_type_id, String out) {
        Button convert_button = findViewById(R.id.convert_button);
        TextView output = findViewById(R.id.output);
        convert_button.setOnClickListener(view -> {
            try {
                double result;
                EditText input_box = findViewById(R.id.input_box);
                double input = Double.parseDouble(input_box.getText().toString());
                if (conversion_type_id == 1) {
                    result = input * 2.2;
                }
                else if (conversion_type_id == 2) {
                    result = input / 2.2;
                }
                else if (conversion_type_id == 3) {
                    result = input * 0.62137;
                }
                else {
                    result = input / 0.62137;
                }
                output.setText(getString(R.string.output, out, result));
            }
            catch (Exception e) {
                output.setText(getString(R.string.invalid_input));
            }
        });
    }

    private void initStartOverButton() {
        Button start_over_button = findViewById(R.id.start_over_button);
        start_over_button.setOnClickListener(view -> {
            this.finish();
        });
    }
}
