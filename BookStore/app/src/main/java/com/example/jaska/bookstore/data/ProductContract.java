package com.example.jaska.bookstore.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by jaska on 23-Dec-17.
 */

public class ProductContract {
    private ProductContract() {
    }
    // Adding necessary helper data
    public static final class ProductEntry implements BaseColumns{
        public final static String TABLE_NAME = "books";

        public final static String COLUMN_PRODUCT_NAME = "name";
        public final static String COLUMN_PRODUCT_PRICE = "price";
        public final static String COLUMN_PRODUCT_QUANTITY = "quantity";
        public final static String COLUMN_PRODUCT_IMAGE = "image";

        public final static String COLUMN_SUPPLIER_NAME = "supname";
        public final static String COLUMN_SUPPLIER_EMAIL = "supemail";
        public final static String COLUMN_SUPPLIER_PHONENUMBER = "supphone";

        public static final String CONTENT_AUTHORITY = "com.example.android.books";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_BOOKS = "books";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;


    }
}
