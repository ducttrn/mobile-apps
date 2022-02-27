package com.myapp.pa2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.myapp.pa2.model.Book;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<Book> bookList;

    private void loadData() {
        bookList = new ArrayList<>();

        addSampleData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.book_list);
        loadData();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new BookAdapter(this, bookList));

        DividerItemDecoration verticalDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                DividerItemDecoration.HORIZONTAL
        );
        Drawable verticalDivider = ContextCompat.getDrawable(
                MainActivity.this,
                R.drawable.vertical_divider
        );
        verticalDecoration.setDrawable(verticalDivider);
        recyclerView.addItemDecoration(verticalDecoration);

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

    private void addSampleData() {
        bookList.add(new Book(1,"Harry Potter and the Goblet of Fire", "JK Rowling"));
        bookList.add(new Book(2,"The Hobbit", "J R R Tolkien"));
        bookList.add(new Book(3,"The Da Vinci Code", "Dan Brown"));
        bookList.add(new Book(3,"Harry Potter and the Prisoner of Azkaban", "JK Rowling"));
        bookList.add(new Book(4,"The Official Highway Code", "Department for Transport"));
        bookList.add(new Book(5,"The Lion, The Witch and The Wardrobe", "CS Lewis"));
        bookList.add(new Book(6,"Fifty Shades of Grey", "E L James"));
        bookList.add(new Book(7,"To Kill a Mockingbird", "Harper Lee"));
        bookList.add(new Book(8,"Lord of the Rings: Return of the King", "J R R Tolkien"));
    }
}
