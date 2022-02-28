package com.myapp.pa2.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.pa2.BookAdapter.OnItemClickedListener;
import com.myapp.pa2.R;

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView bookNameTextView;
    OnItemClickedListener onItemClickedListener;

    public BookViewHolder(@NonNull View itemView, OnItemClickedListener onItemClickedListener) {
        super(itemView);
        this.bookNameTextView = itemView.findViewById(R.id.book_name);
        this.onItemClickedListener = onItemClickedListener;

        itemView.setOnClickListener(this);
    }
    public void setBookName(String bookName) {
        this.bookNameTextView.setText(bookName);
    }

    @Override
    public void onClick(View view) {
        onItemClickedListener.onBookClick(getAdapterPosition());
    }
}
