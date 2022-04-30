package com.myapp.pa2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myapp.pa2.model.Book;

public class NewBookActivity extends AppCompatActivity {

    // Instance variable to interact with SQLite database
    private final BookSQLiteHelper dbInstance = BookSQLiteHelper.getInstance(this);
    Button addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book);

        EditText titleInputBox = findViewById(R.id.new_title_input_box);
        // Default Book info
        String DEFAULT_BOOK_TITLE = "Sample Book";
        titleInputBox.setText(DEFAULT_BOOK_TITLE);

        EditText authorInputBox = findViewById(R.id.new_author_input_box);
        String DEFAULT_BOOK_AUTHOR = "John Doe";
        authorInputBox.setText(DEFAULT_BOOK_AUTHOR);

        // Adding new book
        initAddButton();
    }

    /**
     *  Handler function for "Add Book" button
     */
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

            // Notify user that book added successfully
            Toast.makeText(getApplicationContext(), "Book Added Successfully",Toast.LENGTH_SHORT).show();
            returnMainActivity();
        });
    }

    /**
     *  Returning to MainActivity
     */
    private void returnMainActivity() {
        // Return to the MainActivity
        Intent returnMainIntent = new Intent();

        // Adding action completed, sending success code
        setResult(Activity.RESULT_OK, returnMainIntent);
        finish();
    }
}
