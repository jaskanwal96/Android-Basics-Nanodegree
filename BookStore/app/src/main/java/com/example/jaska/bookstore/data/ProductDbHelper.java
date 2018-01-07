package com.example.jaska.bookstore.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jaska on 24-Dec-17.
 */

public class ProductDbHelper extends SQLiteOpenHelper {
    //Specifying database name and version
    private static final String DATABASE_NAME = "bookstore";
    private static final int DATABASE_VERSION = 1;
    // creating new table with given attributes
    private static String SQL_QUERY = "CREATE TABLE "+ ProductContract.ProductEntry.TABLE_NAME + "(" +
            ProductContract.ProductEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            ProductContract.ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL," +
            ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE + " REAL NOT NULL,"+
            ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0," +
            ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL,"+
            ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL + " TEXT,"+
            ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONENUMBER + " TEXT);";

    private static String DEL_QUERY = "DROP TABLE IF EXISTS";

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
