package com.myapp.pa2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.myapp.pa2.model.Book;

public class NewBookActivity extends AppCompatActivity {

    // Instance variable to interact with SQLite database
    private final BookSQLiteHelper dbInstance = BookSQLiteHelper.getInstance(this);
    Button addButton;

    String DEFAULT_BOOK_TITLE = "Sample Book";
    String DEFAULT_BOOK_AUTHOR = "John Doe";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        EditText titleInputBox = findViewById(R.id.new_title_input_box);
        titleInputBox.setText(DEFAULT_BOOK_TITLE);

        EditText authorInputBox = findViewById(R.id.new_author_input_box);
        authorInputBox.setText(DEFAULT_BOOK_AUTHOR);

        // Adding new book
        initAddButton();
    }

    private void initAddButton() {
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(view -> {
            Book newBook;

            EditText titleInputBox = findViewById(R.id.new_title_input_box);
            String newBookTitle = titleInputBox.getText().toString();

            EditText authorInputBox = findViewById(R.id.new_author_input_box);
            String newBookAuthor = authorInputBox.getText().toString();

            newBook = new Book(newBookTitle, newBookAuthor);

            dbInstance.addBook(newBook);

            returnMainActivity();
        });
    }

    private void returnMainActivity() {
        // Return to the MainActivity
        Intent returnMainIntent = new Intent();
        setResult(Activity.RESULT_OK, returnMainIntent);
        finish();
    }
}
