package com.myapp.pa1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button launchConverter = findViewById(R.id.launch_converter);
        RadioGroup radioGroup = findViewById(R.id.radio_group);

        // Clear any checked radio button. Default: no button is checked
        radioGroup.clearCheck();

        // Listener func for the RadioGroup buttons
        radioGroup.setOnCheckedChangeListener((radioGroup1, checkedId) -> {
            // Retrieve the ID of selected radio button
            RadioButton radioButton = radioGroup1.findViewById(checkedId);
        });

        // Add the Listener to the Launch converter Button
        launchConverter.setOnClickListener(view -> {
            // Get the ID of the selected radio Button
            int selectedButtonId = radioGroup.getCheckedRadioButtonId();

            RadioButton radioButton = radioGroup.findViewById(selectedButtonId);

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

            // Start new activity if user has selected an option
            // Won't switch screen if no RadioButton is choosen
            if (conversionTypeId != -1) {
                openNewActivity(conversionTypeId);
            }
        });
    }
    /**
     *  Navigating to new Activity
     * 
     * @param conversionTypeId
     */
    void openNewActivity(int conversionTypeId)
    {
        // Creating an Intent
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        intent.putExtra("conversionTypeId", conversionTypeId);

        // Start new Activity
        startActivity(intent);
    }
}
