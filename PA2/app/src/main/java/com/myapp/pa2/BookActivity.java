package com.myapp.pa2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.myapp.pa2.model.Book;

public class BookActivity extends AppCompatActivity {
    private static final int DEFAULT_VALUE = 1;
    private final BookSQLiteHelper db_instance = BookSQLiteHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Intent intent = getIntent();
        int book_id = intent.getIntExtra("bookId", DEFAULT_VALUE);
        Book book = db_instance.searchBook(book_id);

        if (book != null) {
            TextView title_view = findViewById(R.id.title_display);
            String book_title = book.getTitle();
            title_view.setText(getString(R.string.title_display, book_title));

            EditText title_input_box = findViewById(R.id.title_input_box);
            title_input_box.setHint(book_title);

            TextView author_view = findViewById(R.id.author_display);
            String book_author = book.getAuthor();
            author_view.setText(getString(R.string.author_display, book_author));

            EditText author_input_box = findViewById(R.id.author_input_box);
            author_input_box.setHint(book_author);
        }
        else {
            this.finish();
        }

        initDeleteButton(book);
        initUpdateButton(book);
    }

    private void initDeleteButton(Book book) {
        Button delete_button = findViewById(R.id.delete_button);
        delete_button.setOnClickListener(view -> {
            db_instance.deleteBook(book);
        });
    }

    private void initUpdateButton(Book book) {
        Button update_button = findViewById(R.id.update_botton);
        update_button.setOnClickListener(view -> {
            EditText title_input_box = findViewById(R.id.title_input_box);
            String new_title = title_input_box.getText().toString();
            book.setTitle(new_title);

            EditText author_input_box = findViewById(R.id.author_input_box);
            String new_author = author_input_box.getText().toString();
            book.setAuthor(new_author);

            db_instance.updateBook(book);
        });
    }
}
