package com.myapp.stockmarkettracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class StockPageActivity extends AppCompatActivity {
    private final String baseURL = "https://www.marketwatch.com/investing/stock/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_page);
        WebView browser = (WebView) findViewById(R.id.stock_webview);

        Intent intent = getIntent();
        String symbol = intent.getStringExtra("symbol");
        browser.loadUrl(baseURL + symbol);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stock_page_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Handle action when "Add Stock" is clicked
        if (id == R.id.exit_option_stock_page) {
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
