package com.myapp.stockmarkettracker;

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

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setLatestPrice(double latestPrice) {
        this.latestPrice = latestPrice;
    }

    public void setLatestPriceChange(double latestPriceChange) {
        this.latestPriceChange = latestPriceChange;
    }

    public void setPriceChangePercentage(double priceChangePercentage) {
        this.priceChangePercentage = priceChangePercentage;
    }
}
