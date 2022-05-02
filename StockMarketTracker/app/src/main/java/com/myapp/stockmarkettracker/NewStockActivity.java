/**
 *
 * BUILT BY JULIAN (PHONG) NGUYEN & BILL TRAN
 *
 */
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
    // private final StockSQLiteHelper dbInstance = StockSQLiteHelper.getInstance(this);
    Button addButton;
    private final StockSQLiteHelper dbInstance = StockSQLiteHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_stock);

        EditText symbolInputBox = findViewById(R.id.new_stock_symbol_input_box);
        String DEFAULT_STOCK_SYMBOL = "SYM";
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

            EditText symbolInputBox = findViewById(R.id.new_stock_symbol_input_box);
            String newStockSymbol = symbolInputBox.getText().toString();

            // Executing AsyncTask to query for specific stock via API
            StockAsyncTask stockAsyncTask = new StockAsyncTask();
            try {
                // Calling API and Get result
                newStock = stockAsyncTask.execute(newStockSymbol).get();
                dbInstance.insertStock(newStock);

                // Notify user that book added successfully
                Toast.makeText(getApplicationContext(), "Stock Added Successfully",Toast.LENGTH_SHORT).show();
                returnMainActivity();

            } catch(Exception e) {
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "Invalid Stock Symbol",Toast.LENGTH_SHORT).show();
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
