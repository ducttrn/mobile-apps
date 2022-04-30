package com.myapp.stockmarkettracker.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.stockmarkettracker.R;

import com.myapp.stockmarkettracker.ui.StockAdapter.OnItemClickedListener;

public class StockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView stockNameTextView;
    private TextView stockSymbolTextView;

    OnItemClickedListener onItemClickedListener;

    public StockViewHolder(@NonNull View itemView, OnItemClickedListener onItemClickedListener) {
        super(itemView);

        this.stockNameTextView = itemView.findViewById(R.id.stock_name);
        this.stockSymbolTextView = itemView.findViewById(R.id.stock_symbol);

        this.onItemClickedListener = onItemClickedListener;

        itemView.setOnClickListener(this);
    }

    public void setStockName(String stockName) {
        this.stockNameTextView.setText(stockName);
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbolTextView.setText(stockSymbol);
    }

    @Override
    public void onClick(View view) {
        onItemClickedListener.onStockClick(getAdapterPosition());
    }
}
