package com.myapp.pa2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.myapp.pa2.BookReaderContract.BookEntry;
import com.myapp.pa2.model.Book;

import java.util.ArrayList;


public class BookSQLiteHelper extends SQLiteOpenHelper {
    private static BookSQLiteHelper db_instance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "book.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BookEntry.TABLE_NAME + " (" +
                    BookEntry._ID + " INTEGER PRIMARY KEY," +
                    BookEntry.COLUMN_NAME_TITLE + " TEXT," +
                    BookEntry.COLUMN_NAME_AUTHOR + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME;

    public static synchronized BookSQLiteHelper getInstance(Context context) {
        if (db_instance == null) {
            db_instance = new BookSQLiteHelper(context.getApplicationContext());
        }
        return db_instance;
    }

    private BookSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void addBook(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues book_values = new ContentValues();
        book_values.put(BookEntry.COLUMN_NAME_TITLE, book.getTitle());
        book_values.put(BookEntry.COLUMN_NAME_AUTHOR, book.getAuthor());

        db.insert(BookEntry.TABLE_NAME, null, book_values);
    }

    public Book searchBook(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String selection = BookEntry._ID + " = " + id;

        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_NAME_TITLE,
                BookEntry.COLUMN_NAME_AUTHOR
        };
        Cursor cursor = db.query(
                BookEntry.TABLE_NAME, projection, selection, null,null, null, null
        );
        Book book = null;
        try {
            if (cursor.moveToFirst()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(BookEntry.COLUMN_NAME_TITLE));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(BookEntry.COLUMN_NAME_AUTHOR));
                book = new Book(id, title, author);
            }
        } finally {
            cursor.close();
        }
        return book;
    }

    public ArrayList<Book> getAllBooks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                BookEntry.TABLE_NAME, null, null, null, null, null, null, null
        );
        ArrayList<Book> books = new ArrayList<>();
        try {
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(BookEntry.COLUMN_NAME_TITLE));
                    String author = cursor.getString(cursor.getColumnIndexOrThrow(BookEntry.COLUMN_NAME_AUTHOR));
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(BookEntry._ID));

                    Book book = new Book(id, title, author);
                    books.add(book);
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }
        return books;
    }

    public void updateBook(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues new_values = new ContentValues();

        new_values.put(BookEntry.COLUMN_NAME_TITLE, book.getTitle());
        new_values.put(BookEntry.COLUMN_NAME_AUTHOR, book.getAuthor());
        String selection = "_id = " + book.getId();
        db.update(BookEntry.TABLE_NAME, new_values, selection, null);
    }

    public void deleteBook(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = "_id = " + book.getId();
        db.delete(BookEntry.TABLE_NAME, selection, null);
    }
}
