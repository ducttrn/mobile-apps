package com.myapp.stockmarkettracker;

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

public class StockAPI {
    String stockURL = "https://cloud.iexapis.com/stable/stock/";
    String API_KEY = "pk_34c03f413dd44fa48e6d6fa0c7685639";

    public Stock fetch(String stockSymbol) {
        URL url = null;
        try {
            url = new URL(stockURL + stockSymbol + "/quote?token="+API_KEY);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilder = new StringBuilder();
        HttpsURLConnection urlConnection = null;

        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream data = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(data));

            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }

        Stock stock;

        try {
            JSONObject obj = new JSONObject(stringBuilder.toString());
            JSONObject data = obj.getJSONObject("data");

            stock = new Stock(
                    data.getString("symbol"),
                    data.getString("companyName"),
                    data.getDouble("latestPrice"),
                    data.getDouble("change"),
                    data.getDouble("changePercentage")
            );

        } catch (JSONException e) {
            e.printStackTrace();

            // Not JSON object (when the Symbol + stock company name is INVALID)
            stock = null;
        }

        return stock;
    }
}
