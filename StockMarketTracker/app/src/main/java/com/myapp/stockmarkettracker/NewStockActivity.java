package com.myapp.stockmarkettracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewStockActivity extends AppCompatActivity {

    // Instance variable to interact with SQLite database
    //private final StockSQLiteHelper dbInstance = StockSQLiteHelper.getInstance(this);
    Button addButton;
    private final StockSQLiteHelper dbInstance = StockSQLiteHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_stock);

        EditText companyNameInputBox = findViewById(R.id.new_company_name_input_box);

        // Default Stock info
        String DEFAULT_STOCK_COMPANY_NAME = "Sample Company";
        companyNameInputBox.setText(DEFAULT_STOCK_COMPANY_NAME);

        EditText symbolInputBox = findViewById(R.id.new_stock_symbol_input_box);
        String DEFAULT_STOCK_SYMBOL = "CCC";
        symbolInputBox.setText(DEFAULT_STOCK_SYMBOL);

        initAddButton();
    }

    /**
     *  Handler function for "Add Book" button
     */
    private void initAddButton() {
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> {
            Stock newStock;

            EditText companyNameInputBox = findViewById(R.id.new_company_name_input_box);
            String newStockCompanyName = companyNameInputBox.getText().toString();

            EditText symbolInputBox = findViewById(R.id.new_stock_symbol_input_box);
            String newStockSymbol = symbolInputBox.getText().toString();

            StockAsyncTask stockAsyncTask = new StockAsyncTask();
            try {
                // Calling API and Get result
                // Note: this approach might block the UI thread
                newStock = stockAsyncTask.execute(newStockSymbol).get();
            } catch(Exception e) {
                e.printStackTrace();
                newStock = null;
            }

            // Stock Symbol not found
            // TODO: Notify user when unable to add Stock
            if (newStock == null) {
                Toast.makeText(getBaseContext(), "Failed to Add Stock",Toast.LENGTH_SHORT).show();
            } else {
                dbInstance.insertStock(newStock);

                // Notify user that book added successfully
                Toast.makeText(getApplicationContext(), "Stock Added Successfully",Toast.LENGTH_SHORT).show();
                returnMainActivity();
            }
        });
    }

    /**
     *  Returning to MainActivity
     */
    private void returnMainActivity() {
        // Return to the MainActivity
        Intent returnMainIntent = new Intent();

        // Adding action completed, sending success code
        setResult(Activity.RESULT_OK, returnMainIntent);
        finish();
    }
}
