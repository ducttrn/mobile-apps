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
    private static BookSQLiteHelper dbInstance;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "book.db";

    // Query to create tables
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BookEntry.TABLE_NAME + " (" +
                    BookEntry._ID + " INTEGER PRIMARY KEY," +
                    BookEntry.COLUMN_NAME_TITLE + " TEXT," +
                    BookEntry.COLUMN_NAME_AUTHOR + " TEXT)";

    // Query to delete all tables
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME;

    /**
     * Use Singleton pattern to make sure
     * only one database instance exists at one time
     * and can be shared among activities
     */
    public static synchronized BookSQLiteHelper getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new BookSQLiteHelper(context.getApplicationContext());
        }
        return dbInstance;
    }

    // Private constructor to avoid calling directly (use getInstance() instead)
    private BookSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        initDatabase(db);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // Add sample data when first creating database 
    protected void initDatabase(SQLiteDatabase db) {
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
            insertBookDB(db, book);
        }
    }

    private void insertBookDB(SQLiteDatabase db, Book book) {
        ContentValues bookValues = new ContentValues();
        bookValues.put(BookEntry.COLUMN_NAME_TITLE, book.getTitle());
        bookValues.put(BookEntry.COLUMN_NAME_AUTHOR, book.getAuthor());

        db.insert(BookEntry.TABLE_NAME, null, bookValues);
    }

    // Add a book using Book entity
    public void addBook(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        insertBookDB(db, book);

        db.close();
    }

    // Search for a book by ID
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
        db.close();
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
        db.close();
        return books;
    }

    // Update a book using Book entity
    public void updateBook(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues newValues = new ContentValues();

        newValues.put(BookEntry.COLUMN_NAME_TITLE, book.getTitle());
        newValues.put(BookEntry.COLUMN_NAME_AUTHOR, book.getAuthor());
        String selection = "_id = " + book.getId();
        db.update(BookEntry.TABLE_NAME, newValues, selection, null);
        db.close();
    }

    // Delete a book using Book entity
    public void deleteBook(Book book) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = "_id = " + book.getId();
        db.delete(BookEntry.TABLE_NAME, selection, null);
        db.close();
    }
}
