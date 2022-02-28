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
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 2);
        initDatabase();

        recyclerView = findViewById(R.id.book_list);

        loadData();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);


        // Add horizontal + vertical dividers for each book row
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

    private void initDatabase() {
        ArrayList<Book> bookListSample = new ArrayList<>();

        bookListSample.add(new Book("The Hobbit", "J R R Tolkien"));
        bookListSample.add(new Book("The Da Vinci Code", "Dan Brown"));
        bookListSample.add(new Book("The Official Highway Code", "Department for Transport"));
        bookListSample.add(new Book("Fifty Shades of Grey", "E L James"));
        bookListSample.add(new Book("To Kill a Mockingbird", "Harper Lee"));
        bookListSample.add(new Book("Jamie’s 15 minute meals", "Jamie Oliver"));
        bookListSample.add(new Book("The BFG", "Roald Dahl"));
        bookListSample.add(new Book("Great Expectations", "Charles Dickens"));
        bookListSample.add(new Book("Animal Farm", "George Orwell"));
        bookListSample.add(new Book("1984", "George Orwell"));

        for (Book book : bookListSample) {
            dbHelper.addBook(book);
        }

    }

    @Override
    public void onBookClick(int position) {

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
        dbHelper.close();
        super.onDestroy();
    }
}
