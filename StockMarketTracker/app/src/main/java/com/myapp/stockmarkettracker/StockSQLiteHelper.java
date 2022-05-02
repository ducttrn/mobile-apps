package com.myapp.stockmarkettracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myapp.stockmarkettracker.StockReaderContract.StockEntry;

import java.util.ArrayList;

public class StockSQLiteHelper extends SQLiteOpenHelper {
    private static StockSQLiteHelper dbInstance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "stock.db";

    // Query to create tables
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StockEntry.TABLE_NAME + " (" +
                    StockEntry.COLUMN_NAME_SYMBOL + " TEXT PRIMARY KEY," +
                    StockEntry.COLUMN_NAME_COMPANY_NAME + " TEXT," +
                    StockEntry.COLUMN_NAME_LATEST_STOCK_PRICE + " REAL," +
                    StockEntry.COLUMN_NAME_LATEST_PRICE_CHANGE + " REAL," +
                    StockEntry.COLUMN_NAME_PRICE_CHANGE_PERCENTAGE + " REAL)";

    // Query to delete all tables
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StockEntry.TABLE_NAME;

    /**
     * Use Singleton pattern to make sure
     * only one database instance exists at one time
     * and can be shared among activities
     */
    public static synchronized StockSQLiteHelper getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new StockSQLiteHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    // Private constructor to avoid calling directly (use getInstance() instead)
    private StockSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertStock(Stock stock) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues stockValues = new ContentValues();
        stockValues.put(StockEntry.COLUMN_NAME_SYMBOL, stock.getSymbol());
        stockValues.put(StockEntry.COLUMN_NAME_COMPANY_NAME, stock.getCompanyName());
        stockValues.put(StockEntry.COLUMN_NAME_LATEST_STOCK_PRICE, stock.getLatestPrice());
        stockValues.put(StockEntry.COLUMN_NAME_LATEST_PRICE_CHANGE, stock.getLatestPriceChange());
        stockValues.put(StockEntry.COLUMN_NAME_PRICE_CHANGE_PERCENTAGE, stock.getPriceChangePercentage());

        db.insert(StockEntry.TABLE_NAME, null, stockValues);
        db.close();
    }

    // Search for a stock by symbol
    public Stock searchStock(String symbol) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = StockEntry.COLUMN_NAME_SYMBOL + " = " + symbol;

        String[] projection = {
                StockEntry.COLUMN_NAME_SYMBOL,
                StockEntry.COLUMN_NAME_COMPANY_NAME,
                StockEntry.COLUMN_NAME_LATEST_STOCK_PRICE,
                StockEntry.COLUMN_NAME_LATEST_PRICE_CHANGE,
                StockEntry.COLUMN_NAME_PRICE_CHANGE_PERCENTAGE
        };
        Cursor cursor = db.query(
                StockEntry.TABLE_NAME, projection, selection, null,null, null, null
        );
        Stock stock = null;
        try {
            if (cursor.moveToFirst()) {
                String companyName = cursor.getString(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_NAME_COMPANY_NAME));
                double latestStockPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_NAME_LATEST_STOCK_PRICE));
                double latestPriceChange = cursor.getDouble(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_NAME_LATEST_PRICE_CHANGE));
                double priceChangePercentage = cursor.getDouble(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_NAME_PRICE_CHANGE_PERCENTAGE));
                stock = new Stock(symbol, companyName, latestStockPrice, latestPriceChange, priceChangePercentage);
            }
        } finally {
            cursor.close();
        }
        db.close();
        return stock;
    }

    public ArrayList<Stock> getAllStocks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                StockEntry.TABLE_NAME, null, null, null, null, null, null, null
        );
        ArrayList<Stock> Stocks = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String symbol = cursor.getString(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_NAME_SYMBOL));
                    String companyName = cursor.getString(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_NAME_COMPANY_NAME));
                    double latestStockPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_NAME_LATEST_STOCK_PRICE));
                    double latestPriceChange = cursor.getDouble(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_NAME_LATEST_PRICE_CHANGE));
                    double priceChangePercentage = cursor.getDouble(cursor.getColumnIndexOrThrow(StockEntry.COLUMN_NAME_PRICE_CHANGE_PERCENTAGE));

                    Stock Stock = new Stock(symbol, companyName, latestStockPrice, latestPriceChange, priceChangePercentage);
                    Stocks.add(Stock);
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }
        db.close();
        return Stocks;
    }

    // Update a Stock using Stock entity
    public void updateStock(Stock stock) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues stockValues = new ContentValues();
        stockValues.put(StockEntry.COLUMN_NAME_COMPANY_NAME, stock.getCompanyName());
        stockValues.put(StockEntry.COLUMN_NAME_LATEST_STOCK_PRICE, stock.getLatestPrice());
        stockValues.put(StockEntry.COLUMN_NAME_LATEST_PRICE_CHANGE, stock.getLatestPriceChange());
        stockValues.put(StockEntry.COLUMN_NAME_PRICE_CHANGE_PERCENTAGE, stock.getPriceChangePercentage());

        String selection = "_id = " + stock.getSymbol();
        db.update(StockEntry.TABLE_NAME, stockValues, selection, null);
        db.close();
    }

    // Delete a Stock using Stock entity
    public void deleteStock(Stock stock) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = "_id = " + stock.getSymbol();
        db.delete(StockEntry.TABLE_NAME, selection, null);
        db.close();
    }
}
