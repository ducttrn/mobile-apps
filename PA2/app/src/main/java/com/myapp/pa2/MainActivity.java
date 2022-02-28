package com.myapp.pa2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.myapp.pa2.model.Book;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookAdapter.OnItemClickedListener {

    int LAUNCH_BOOK_ACTIVITY = 1;

    RecyclerView recyclerView;
    private ArrayList<Book> bookList;
    BookSQLiteHelper dbHelper;
    BookAdapter bookAdapter;

    private void loadData() {
        bookList = dbHelper.getAllBooks();
        bookAdapter = new BookAdapter(bookList, this);
        recyclerView.setAdapter(bookAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init DB instance
        dbHelper = BookSQLiteHelper.getInstance(this);
        recyclerView = findViewById(R.id.book_list);
        loadData();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        addDividers();
    }

    private void addDividers() {
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                DividerItemDecoration.VERTICAL
        );
        Drawable horizontalDivider = ContextCompat.getDrawable(
                MainActivity.this,
                R.drawable.horizontal_divider
        );
        horizontalDecoration.setDrawable(horizontalDivider);
        recyclerView.addItemDecoration(horizontalDecoration);
    }

    @Override
    public void onBookClick(int position) {
        // Launch BookActivity when a book is clicked
        Intent intent = new Intent(this, BookActivity.class);
        intent.putExtra("bookID", bookList.get(position).getId());
        startActivityForResult(intent, LAUNCH_BOOK_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LAUNCH_BOOK_ACTIVITY) {
            if(resultCode == Activity.RESULT_OK){
                loadData();
            }
        }
    }

    @Override
    protected void onDestroy() {
        // Close database connection when the app is destroyed
        dbHelper.close();
        super.onDestroy();
    }
}
