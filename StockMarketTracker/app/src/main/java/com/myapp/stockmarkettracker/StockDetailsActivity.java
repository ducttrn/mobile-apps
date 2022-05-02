package com.myapp.stockmarkettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class StockDetailsActivity extends AppCompatActivity {
    private final StockSQLiteHelper dbInstance = StockSQLiteHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        Intent intent = getIntent();
        String symbol = intent.getStringExtra("symbol");

        Stock stock;
        StockAsyncTask stockAsyncTask = new StockAsyncTask();
        try {
            // Calling API and Get result
            // Note: this approach might block the UI thread
            stock = stockAsyncTask.execute(symbol).get();
            dbInstance.updateStock(stock);
        } catch(Exception e) {
            e.printStackTrace();
            stock = dbInstance.searchStock(symbol);
        }

        if (stock != null) {
            TextView symbol_display = findViewById(R.id.sda_symbol);
            symbol_display.setText(getString(R.string.sda_symbol, stock.getSymbol()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stock_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Handle action when "Add Stock" is clicked
        if (id == R.id.exit_option) {
            returnMainActivity();
        }

        return super.onOptionsItemSelected(item);
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
