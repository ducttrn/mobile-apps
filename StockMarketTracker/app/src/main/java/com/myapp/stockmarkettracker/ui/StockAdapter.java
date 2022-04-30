package com.myapp.stockmarkettracker.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.myapp.stockmarkettracker.R;
import com.myapp.stockmarkettracker.Stock;

public class StockAdapter extends RecyclerView.Adapter<StockViewHolder> {

    private ArrayList<Stock> stockList;
    private OnItemClickedListener onItemClickedListener;

    public StockAdapter(ArrayList<Stock> stockList, OnItemClickedListener onItemClickedListener) {
        this.stockList = stockList;
        this.onItemClickedListener = onItemClickedListener;
    }

    /**
     *  Creating a Holder/Container View for each stock
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_stock, parent, false);
        return new StockViewHolder(view, onItemClickedListener);
    }

    @Override
    public int getItemCount() {
        return stockList == null ? 0 : stockList.size();
    }

    /**
     * The info to be displayed on each row of stock
     *
     * @param stockViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull StockViewHolder stockViewHolder, int position) {
        Stock stock = stockList.get(position);

        // Display stock title
        stockViewHolder.setStockName(stock.getCompanyName());
    }

    /**
     *  Interface for listener when clicking each Stock item
     */
    public interface OnItemClickedListener{
        void onStockClick(int position);
    }
}
