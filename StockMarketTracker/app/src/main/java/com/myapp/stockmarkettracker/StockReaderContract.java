/**
 *
 * BUILT BY JULIAN (PHONG) NGUYEN & BILL TRAN
 *
 */
package com.myapp.stockmarkettracker;

import android.provider.BaseColumns;

public class StockReaderContract {
    private StockReaderContract() {}

    public static class StockEntry implements BaseColumns {
        // Database name and columns
        public static final String TABLE_NAME = "stocks";
        public static final String COLUMN_NAME_SYMBOL = "symbol";
        public static final String COLUMN_NAME_COMPANY_NAME = "companyName";
        public static final String COLUMN_NAME_LATEST_STOCK_PRICE = "latestStockPrice";
        public static final String COLUMN_NAME_LATEST_PRICE_CHANGE = "latestPriceChange";
        public static final String COLUMN_NAME_PRICE_CHANGE_PERCENTAGE = "priceChangePercentage";
    }
}
