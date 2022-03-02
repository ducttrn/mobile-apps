package com.myapp.pa2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myapp.pa2.model.Book;
import com.myapp.pa2.ui.BookViewHolder;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookViewHolder> {

    private ArrayList<Book> bookList;
    private OnItemClickedListener onItemClickedListener;

    public BookAdapter(ArrayList<Book> bookList, OnItemClickedListener onItemClickedListener) {
        this.bookList = bookList;
        this.onItemClickedListener = onItemClickedListener;
    }

    /**
     *  Creating a Holder/Container View for each book
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_book, parent, false);
        return new BookViewHolder(view, onItemClickedListener);
    }

    @Override
    public int getItemCount() {
        return bookList == null ? 0 : bookList.size();
    }

    /**
     * The info to be displayed on each row of book
     *
     * @param bookViewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder bookViewHolder, int position) {
        Book book = bookList.get(position);
        // Display book title
        bookViewHolder.setBookName(book.getTitle());
    }

    /**
     *  Interface for listener when clicking each book item
     */
    public interface OnItemClickedListener{
        void onBookClick(int position);
    }
}
