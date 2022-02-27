package com.myapp.pa2.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.pa2.R;

public class BookViewHolder extends RecyclerView.ViewHolder {

    private TextView bookNameTextView;

    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        bookNameTextView = itemView.findViewById(R.id.book_name);
    }
    public void setBookName(String bookName) {
        this.bookNameTextView.setText(bookName);
    }
}
