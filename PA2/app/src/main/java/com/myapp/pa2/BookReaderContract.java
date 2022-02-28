package com.myapp.pa2;

import android.provider.BaseColumns;

public class BookReaderContract {
    private BookReaderContract() {}

    public static class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
    }
}
