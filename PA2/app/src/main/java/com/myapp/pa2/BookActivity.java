package com.myapp.pa2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myapp.pa2.model.Book;

public class BookActivity extends AppCompatActivity {
    private static final int DEFAULT_VALUE = 1;
    private final BookSQLiteHelper dbInstance = BookSQLiteHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent = getIntent();
        int bookID = intent.getIntExtra("bookID", DEFAULT_VALUE);
        Book book = dbInstance.searchBook(bookID);

        if (book != null) {
            TextView titleView = findViewById(R.id.title_display);
            String bookTitle = book.getTitle();
            titleView.setText(getString(R.string.title_display, bookTitle));

            EditText titleInputBox = findViewById(R.id.title_input_box);
            titleInputBox.setHint(bookTitle);

            TextView authorView = findViewById(R.id.author_display);
            String bookAuthor = book.getAuthor();
            authorView.setText(getString(R.string.author_display, bookAuthor));

            EditText authorInputBox = findViewById(R.id.author_input_box);
            authorInputBox.setHint(bookAuthor);
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
            dbInstance.deleteBook(book);
            returnMainActivity();
        });
    }

    private void initUpdateButton(Book book) {
        Button updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(view -> {
            EditText titleInputBox = findViewById(R.id.title_input_box);
            String newTitle = titleInputBox.getText().toString();
            book.setTitle(newTitle);

            EditText authorInputBox = findViewById(R.id.author_input_box);
            String newAuthor = authorInputBox.getText().toString();
            book.setAuthor(newAuthor);

            dbInstance.updateBook(book);
            returnMainActivity();
        });
    }

    // TODO: return something to MainActivity?
    private void returnMainActivity() {
        // Return to the MainActivity
        Intent returnMainIntent = new Intent();

        //returnMainIntent.putExtra("result",result);
        setResult(Activity.RESULT_OK, returnMainIntent);
        finish();
    }
}
