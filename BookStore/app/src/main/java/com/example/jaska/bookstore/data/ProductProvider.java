package com.example.jaska.bookstore.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.widget.Toast;

import com.example.jaska.bookstore.ProductEditorActivity;

import java.util.regex.Pattern;

/**
 * Created by jaska on 30-Dec-17.
 */

public class ProductProvider extends ContentProvider {
    private static final Double DEFAULT_PRICE = -1.2;
    ProductDbHelper bHelper;
    // URI matcher cde for the content URI for the books table
    private static final int BOOKS = 1000;

    // URI matcher code for the content URI for a single book in the books table
    private static final int BOOK_ID = 1001;

    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * Use NO_MATCH as the input for this case.
     */
    private static final UriMatcher bUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // Adding URI patters which would be matched further
        bUriMatcher.addURI(ProductContract.ProductEntry.CONTENT_AUTHORITY, ProductContract.ProductEntry.PATH_BOOKS, BOOKS );
        bUriMatcher.addURI(ProductContract.ProductEntry.CONTENT_AUTHORITY, ProductContract.ProductEntry.PATH_BOOKS + "/#", BOOK_ID );

    }

    @Override
    public boolean onCreate() {
        bHelper = new ProductDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = bHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = bUriMatcher.match(uri);
        switch(match){
            case BOOKS:
                cursor = db.query(ProductContract.ProductEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ID:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(ProductContract.ProductEntry.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unkown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = bUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return ProductContract.ProductEntry.CONTENT_LIST_TYPE;
            case BOOK_ID:
                return ProductContract.ProductEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = bUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return insertBook(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertBook(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = bHelper.getWritableDatabase();
        //Checking the data is correct
        checkValidations(contentValues);
        long id  = db.insert(ProductContract.ProductEntry.TABLE_NAME, null, contentValues);
        // Insertion failed case
        if(id == -1){
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = bUriMatcher.match(uri);
        SQLiteDatabase database = bHelper.getWritableDatabase();
        switch (match) {
            case BOOKS:
                return database.delete(ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
            case BOOK_ID:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                int deletedRows = database.delete(ProductContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
                if(deletedRows != 0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return deletedRows;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        final int match = bUriMatcher.match(uri);
        switch (match) {
            case BOOKS:
                return updateBook(uri, contentValues, selection, selectionArgs);
            case BOOK_ID:
                selection = ProductContract.ProductEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateBook(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateBook(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase database = bHelper.getWritableDatabase();
        checkValidations(contentValues);
        if(contentValues.size() == 0){
            return 0;
        }
        // Update the new book with the given values
        // return the number of rows updated

        int updatedRows = database.update(ProductContract.ProductEntry.TABLE_NAME, contentValues,selection, selectionArgs);
        if(updatedRows != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updatedRows;
    }

    private void checkValidations(ContentValues values) {
        String name = values.getAsString(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
        // Check if product name is provided
        if (name == null || name.matches("")) {
           // Toast.makeText(getContext(), "Product Name Required", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Product requires a name");
        }
        // Check if suppliers name is provided
        name = values.getAsString(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME);
        if (name == null || name.matches("")) {
            //Toast.makeText(getContext(), "Supplier Name Required", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Supplier requires a name");
        }

        // If the price is provided, check that it's greater than or equal to 0$

        Double price = values.getAsDouble(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE);
        if(price == DEFAULT_PRICE ||(price != null && price < 0)){
           // Toast.makeText(getContext(), "invalid Price", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Product requires valid price");
        }

        // If the quantity is provided, check that it's greater than or equal to 1
        Integer quantity = values.getAsInteger(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);
        if (quantity != null && quantity < 0) {

            throw new IllegalArgumentException("product requires valid quantity");
        }

        // Email is optional but if provided must be valid
        String email = values.getAsString(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL);
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if(!email.matches("") && !pattern.matcher(email).matches()){
            //Toast.makeText(getContext(), "Invalid Email", Toast.LENGTH_LONG).show();
            throw new IllegalArgumentException("Email must be valid");
        }
    }

}
