package com.example.jaska.bookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaska.bookstore.data.ProductContract;

import static java.security.AccessController.getContext;

/**
 * Created by jaska on 30-Dec-17.
 */

public class ProductCursorAdapter extends CursorAdapter {


    public ProductCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {

        return LayoutInflater.from(context).inflate(R.layout.book_list_item, viewGroup, false );
    }

    @Override
    public void bindView(final View view, final Context context,final Cursor cursor) {
        TextView productName = (TextView) view.findViewById(R.id.product_name);
        TextView productPrice = (TextView) view.findViewById(R.id.product_price);
        TextView productQuantity = (TextView) view.findViewById(R.id.product_quantity);
        view.setTag(cursor.getPosition());
        productName.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME)));
        productPrice.setText(cursor.getFloat(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE)) + "");
        productQuantity.setText(cursor.getInt(cursor.getColumnIndexOrThrow(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY)) + "");
        final Button sale = (Button)view.findViewById(R.id.sale_button);
        final long id = cursor.getLong(cursor.getColumnIndex(ProductContract.ProductEntry._ID));

        sale.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                TextView tv = (TextView)view.findViewById(R.id.product_quantity);
                int oldQuan = Integer.valueOf(tv.getText().toString());
                oldQuan--;
                if(oldQuan < 0){
                    Toast.makeText(context, R.string.negative_validation, Toast.LENGTH_LONG).show();
                    return;
                }
                else
                    tv.setText(oldQuan + "");

                Uri contentUri = ContentUris.withAppendedId(ProductContract.ProductEntry.CONTENT_URI, id);
                Log.d("jaskanwal", contentUri.toString());
                ContentValues ct = new ContentValues();
                String uProductName = cursor.getString(cursor.getColumnIndex
                        (ProductContract.ProductEntry.COLUMN_PRODUCT_NAME));
                Double uProductPrice = cursor.getDouble(cursor.getColumnIndex
                        (ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE));
                String uSupplierName = cursor.getString(cursor.getColumnIndex
                        (ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME));
                String uSupplierEmail = cursor.getString(cursor.getColumnIndex
                        (ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL));
                String uSupplierPhone = cursor.getString(cursor.getColumnIndex
                        (ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONENUMBER));


                ct.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME , uProductName);
                ct.put(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE, uProductPrice);
                ct.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME , uSupplierName);
                ct.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL , uSupplierEmail);
                ct.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONENUMBER, uSupplierPhone);
                ct.put(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, oldQuan);

                context.getContentResolver().update(contentUri, ct, null, null);


            }
        });

    }

}
