package com.myapp.pa2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.pa2.model.Book;

public class BookActivity extends AppCompatActivity {
    private static final int DEFAULT_VALUE = 1;
    // Instance variable to interact with SQLite database
    private final BookSQLiteHelper dbInstance = BookSQLiteHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // Query database based on book ID
        Intent intent = getIntent();
        int bookID = intent.getIntExtra("bookID", DEFAULT_VALUE);
        Book book = dbInstance.searchBook(bookID);

        if (book != null) {
            // Set TextViews based on query result
            TextView titleView = findViewById(R.id.title_display);
            String bookTitle = book.getTitle();
            titleView.setText(getString(R.string.title_display, bookTitle));

            EditText titleInputBox = findViewById(R.id.title_input_box);
            titleInputBox.setText(bookTitle);

            TextView authorView = findViewById(R.id.author_display);
            String bookAuthor = book.getAuthor();
            authorView.setText(getString(R.string.author_display, bookAuthor));

            EditText authorInputBox = findViewById(R.id.author_input_box);
            authorInputBox.setText(bookAuthor);
        }
        else {
            this.finish();
        }

        initDeleteButton(book);
        initUpdateButton(book);
    }

    private void initDeleteButton(Book book) {
        Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> {
            // Delete book then return to the MainActivity
            dbInstance.deleteBook(book);
            Toast.makeText(getApplicationContext(), "Book Deleted Successfully",Toast.LENGTH_SHORT).show();
            returnMainActivity();
        });
    }

    private void initUpdateButton(Book book) {
        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(view -> {
            // Update book based on user inputs
            EditText titleInputBox = findViewById(R.id.title_input_box);
            String newTitle = titleInputBox.getText().toString();
            book.setTitle(newTitle);

            EditText authorInputBox = findViewById(R.id.author_input_box);
            String newAuthor = authorInputBox.getText().toString();
            book.setAuthor(newAuthor);

            dbInstance.updateBook(book);

            Toast.makeText(getApplicationContext(), "Book Updated Successfully",Toast.LENGTH_SHORT).show();
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
