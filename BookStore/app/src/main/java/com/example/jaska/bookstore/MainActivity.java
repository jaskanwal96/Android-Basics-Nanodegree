package com.example.jaska.bookstore;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaska.bookstore.data.ProductContract.ProductEntry;
import com.example.jaska.bookstore.data.ProductDbHelper;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    ProductDbHelper mDbHelper;
    Cursor cursor;
    private static final int BOOK_LOADER = 0;
    ProductCursorAdapter bookCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new ProductDbHelper(this);

        ListView lv = (ListView)findViewById(R.id.book_list_view);
        Cursor cursor = null;
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductEditorActivity.class);
                startActivity(intent);
            }
        });
        bookCursorAdapter = new ProductCursorAdapter(this, cursor,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(bookCursorAdapter);
        View emptyView = (View) findViewById(R.id.empty_view);
        lv.setEmptyView(emptyView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                Intent it = new Intent(MainActivity.this, ProductEditorActivity.class);
                //Appending Uri with current item id
                Uri contentUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, id);
                // Adding Uri data to intent
                it.setData(contentUri);
                startActivity(it);
            }
        });



        getLoaderManager().initLoader(BOOK_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projections = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_SUPPLIER_NAME,
                ProductEntry.COLUMN_SUPPLIER_EMAIL,
                ProductEntry.COLUMN_SUPPLIER_PHONENUMBER,
        };
        return new CursorLoader(this, ProductEntry.CONTENT_URI, projections, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        bookCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        bookCursorAdapter.swapCursor(null);
    }
}
