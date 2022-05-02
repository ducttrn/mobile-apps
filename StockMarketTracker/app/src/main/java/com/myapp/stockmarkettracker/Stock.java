/**
 *
 * BUILT BY JULIAN (PHONG) NGUYEN & BILL TRAN
 *
 */
package com.myapp.stockmarkettracker;

/**
 * Class to represent stocks as entities
 */
public class Stock {
    private String symbol;
    private String companyName;
    private double latestPrice;
    private double latestPriceChange;
    private double priceChangePercentage;

    public Stock(String symbol, String companyName, double latestPrice, double latestPriceChange, double priceChangePercentage) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.latestPrice = latestPrice;
        this.latestPriceChange = latestPriceChange;
        this.priceChangePercentage = priceChangePercentage;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getLatestPrice() {
        return latestPrice;
    }

    public double getLatestPriceChange() {
        return latestPriceChange;
    }

    public double getPriceChangePercentage() {
        return priceChangePercentage;
    }
}
