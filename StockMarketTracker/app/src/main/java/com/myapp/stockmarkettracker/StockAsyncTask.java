/**
 *
 * BUILT BY JULIAN (PHONG) NGUYEN & BILL TRAN
 *
 */
package com.myapp.stockmarkettracker;

import android.os.AsyncTask;
import io.github.pixee.security.BoundedLineReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class StockAsyncTask extends AsyncTask<String, Void, Stock> {

    // Constants
    private final String stockURL = "https://cloud.iexapis.com/stable/stock/";
    private final String API_KEY = "pk_34c03f413dd44fa48e6d6fa0c7685639";

    /**
     *  Calling API + Retrieve data
     * @param stockSymbol
     * @return
     */
    private Stock retrieveData(String stockSymbol) {
        URL url;
        try {
            url = new URL(stockURL + stockSymbol + "/quote?token=" + API_KEY);
        } catch (MalformedURLException e) {
            //e.printStackTrace();
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        HttpsURLConnection urlConnection = null;

        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            InputStream data = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(data));

            String line;

            while ((line = BoundedLineReader.readLine(reader, 5_000_000)) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            urlConnection.disconnect();
        }

        return parseJSONtoStock(stringBuilder.toString());
    }

    @Override
    protected Stock doInBackground(String... urls) {
        return retrieveData(urls[0]);
    }

    /**
     * Parse Response from String to JSON and convert to a Stock Object
     * @param response
     * @return
     */
    public Stock parseJSONtoStock(String response) {
        Stock stock;

        if (response == null) {
            return null;
        }

        try {
            JSONObject data = new JSONObject(response);

            stock = new Stock(
                    data.getString("symbol"),
                    data.getString("companyName"),
                    data.getDouble("latestPrice"),
                    data.getDouble("change"),
                    data.getDouble("changePercent")
            );

        } catch (JSONException e) {
            e.printStackTrace();

            // Not JSON object (when the Symbol + stock company name is INVALID)
            stock = null;
        }

        return stock;
    }
}
