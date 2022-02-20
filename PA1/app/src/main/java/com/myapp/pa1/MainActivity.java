package com.myapp.pa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button launchConverter = (Button) findViewById(R.id.launch_converter);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);

        // Clear any checked radio button
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
                    {

                        // Retrieve the ID of selected radio button
                        RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedId);
                    }
                });

        // Add the Listener to the Submit Button
        launchConverter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                int selectedButtonId = radioGroup.getCheckedRadioButtonId();


                RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedButtonId);

                int conversionTypeId;
                int radioButtonId = radioGroup.getCheckedRadioButtonId();

                if (radioButtonId == findViewById(R.id.radio_button_1).getId()) {
                    conversionTypeId = 1;
                } else if (radioButtonId == findViewById(R.id.radio_button_2).getId()) {
                    conversionTypeId = 2;
                } else if (radioButtonId == findViewById(R.id.radio_button_3).getId()) {
                    conversionTypeId = 3;
                } else if (radioButtonId == findViewById(R.id.radio_button_4).getId()) {
                    conversionTypeId = 4;
                } else {
                    conversionTypeId = -1;
                }

                // Start new activity
                if (conversionTypeId != -1) {
                    openNewActivity(conversionTypeId);
                }
            }
        });
    }

    void openNewActivity(int conversionTypeId)
    {
        // Creating an Intent
        Intent i = new Intent(getApplicationContext(), SecondActivity.class);

        i.putExtra("conversionTypeId", conversionTypeId);

        // Start new Activity
        startActivity(i);
    }
}
