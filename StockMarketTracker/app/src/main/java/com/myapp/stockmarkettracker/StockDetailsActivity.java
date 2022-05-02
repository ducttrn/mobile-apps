package com.myapp.stockmarkettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class StockDetailsActivity extends AppCompatActivity {
    private final StockSQLiteHelper dbInstance = StockSQLiteHelper.getInstance(this);
    private String symbol;

    int LAUNCH_STOCK_PAGE_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        Intent intent = getIntent();
        symbol = intent.getStringExtra("symbol");
        updateStockData();
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
        switch (id) {
            case R.id.exit_option: returnMainActivity();
            case R.id.delete_stock_option:
                Stock stock =  dbInstance.searchStock(symbol);
                if (stock != null){
                    dbInstance.deleteStock(stock);
                }
                returnMainActivity();
            case R.id.refresh_data_option: updateStockData();
            case R.id.visit_stock_page_option:
                Intent intent = new Intent(this, StockPageActivity.class);
                intent.putExtra("symbol", symbol);
                startActivityForResult(intent, LAUNCH_STOCK_PAGE_ACTIVITY);
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateStockData() {
        Stock stock;
        StockAsyncTask stockAsyncTask = new StockAsyncTask();
        try {
            // Calling API and Get result
            stock = stockAsyncTask.execute(symbol).get();
            dbInstance.updateStock(stock);
        } catch(Exception e) {
            e.printStackTrace();
            stock = dbInstance.searchStock(symbol);
        }

        if (stock != null) {
            TextView symbolDisplay = findViewById(R.id.sda_symbol);
            symbolDisplay.setText(getString(R.string.sda_symbol, stock.getSymbol()));

            TextView nameDisplay = findViewById(R.id.sda_company_name);
            nameDisplay.setText(getString(R.string.sda_company_name, stock.getCompanyName()));

            TextView priceDisplay = findViewById(R.id.sda_latest_stock_price);
            priceDisplay.setText(getString(R.string.sda_latest_stock_price, stock.getLatestPrice()));

            TextView changeDisplay = findViewById(R.id.sda_latest_price_change);
            changeDisplay.setText(getString(R.string.sda_latest_price_change, stock.getLatestPriceChange()));

            TextView pctDisplay = findViewById(R.id.sda_price_change_percentage);
            pctDisplay.setText(getString(R.string.sda_price_change_percentage, stock.getPriceChangePercentage()));
        }
        else {
            returnMainActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
