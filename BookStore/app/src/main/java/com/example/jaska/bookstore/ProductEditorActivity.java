package com.example.jaska.bookstore;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaska.bookstore.data.ProductContract;

import org.w3c.dom.Text;

import java.io.Serializable;

public class ProductEditorActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<Cursor> {
    private static final Double DEFAULT_PRODUCT_PRICE = -1.2;
    private static final int BOOK_LOADER = 1;
    Uri contentProductUri;
    private EditText editProductPrice;
    private EditText editProductName;
    private TextView textQuantity;
    private EditText editSupplierName;
    private EditText editSupplierEmail;
    private EditText editSupplierPhone;
    private Button mOrder;
    private boolean mProductHasChanged = false;
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_editor);

        Button plus = (Button)findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView quan = (TextView) findViewById(R.id.product_quantity);
                int val = Integer.valueOf(quan.getText().toString());
                val++;
                quan.setText(val + "");
            }
        });

        Button minus = (Button)findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView quan = (TextView) findViewById(R.id.product_quantity);
                int val = Integer.valueOf(quan.getText().toString());
                val--;
                if(val < 0){
                    Toast.makeText(getBaseContext(), R.string.negative_validation,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                quan.setText(val + "");
            }
        });

        editProductPrice = (EditText)findViewById(R.id.product_edit_price);
        editProductName = (EditText)findViewById(R.id.product_edit_name);
        textQuantity = (TextView)findViewById(R.id.product_quantity);
        editSupplierName = (EditText)findViewById(R.id.supplier_edit_name);
        editSupplierEmail = (EditText)findViewById(R.id.supplier_edit_email);
        editSupplierPhone = (EditText)findViewById(R.id.supplier_edit_phone);

        //Checking if touched
        editProductName.setOnTouchListener(mTouchListener);
        editProductPrice.setOnTouchListener(mTouchListener);
        plus.setOnTouchListener(mTouchListener);
        minus.setOnTouchListener(mTouchListener);
        editSupplierName.setOnTouchListener(mTouchListener);
        editSupplierPhone.setOnTouchListener(mTouchListener);
        editSupplierEmail.setOnTouchListener(mTouchListener);

        contentProductUri = getIntent().getData();
        if(contentProductUri == null){
            setTitle(R.string.add_product);
            invalidateOptionsMenu();

        }
        else
        {
            setTitle(R.string.edit_product);
            mOrder = (Button)findViewById(R.id.order);
            mOrder.setVisibility(View.VISIBLE);
            getLoaderManager().initLoader(BOOK_LOADER, null, this);
        }
    }

    private void showUnsavedChangesDialog(
        DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the product.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteWarning(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.warning_delete);
        builder.setPositiveButton(R.string.delete, discardButtonClickListener);
        builder.setNegativeButton(R.string.dont_delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Dont delete" button, so dismiss the dialog
                // and continue editing.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        // If the product hasn't changed, continue with handling back button press
        if (!mProductHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new insertion, hide the "Delete" menu item.
        if (contentProductUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete_data);
            menuItem.setVisible(false);
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.action_insert_data:
                saveData();
               return true;
            case R.id.action_delete_data:
                DialogInterface.OnClickListener deleteWarningButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Delete" button, navigate to parent activity.
                                deleteData();
                                finish();
                            }
                        };

                // Show a dialog that notifies the user that gives delete warning
                deleteWarning(deleteWarningButtonClickListener);
               return true;
            case android.R.id.home:
                if (!mProductHasChanged) {
                    NavUtils.navigateUpFromSameTask(ProductEditorActivity.this);
                    return true;
                }

                // Setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(ProductEditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteData() {
        if (contentProductUri != null) {

            int rowsDeleted = getContentResolver().delete(contentProductUri, null, null);
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveData() {
            try{
                ContentValues ct = new ContentValues();
                editProductPrice = (EditText)findViewById(R.id.product_edit_price);
                Double productPrice = DEFAULT_PRODUCT_PRICE;

                editProductName = (EditText)findViewById(R.id.product_edit_name);
                String productName = editProductName.getText().toString();

                if(!editProductPrice.getText().toString().matches("")) {
                    productPrice = Double.valueOf(editProductPrice.getText().toString());
                }

                textQuantity = (TextView)findViewById(R.id.product_quantity);
                Integer productQuantity = Integer.valueOf(textQuantity.getText().toString());

                editSupplierName = (EditText)findViewById(R.id.supplier_edit_name);
                String supplierName = editSupplierName.getText().toString();

                editSupplierEmail = (EditText)findViewById(R.id.supplier_edit_email);
                String supplierEmail = editSupplierEmail.getText().toString();

                editSupplierPhone = (EditText)findViewById(R.id.supplier_edit_phone);
                String supplierPhone = editSupplierPhone.getText().toString();

                ct.put(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME , productName);
                ct.put(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE, productPrice);
                ct.put(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);
                ct.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME , supplierName);
                ct.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL , supplierEmail);
                ct.put(ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONENUMBER, supplierPhone);

                if (contentProductUri == null) {

                    Uri newUri = getContentResolver().insert(ProductContract.ProductEntry.CONTENT_URI, ct);

                    // Show a toast message depending on whether or not the insertion was successful.
                    if (newUri == null) {
                        // If the new content URI is null, then there was an error with insertion.
                        Toast.makeText(this, getString(R.string.enter_unsuccess),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // Otherwise, the insertion was successful and we can display a toast.
                        Toast.makeText(this, getString(R.string.enter_success),
                                Toast.LENGTH_SHORT).show();
                    }
                } else {

                    int rowsAffected = getContentResolver().update(contentProductUri, ct, null, null);

                    // Show a toast message depending on whether or not the update was successful.
                    if (rowsAffected == 0) {
                        // If no rows were affected, then there was an error with the update.
                        Toast.makeText(this, getString(R.string.editor_update_failed),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // Otherwise, the update was successful and we can display a toast.
                        Toast.makeText(this, getString(R.string.editor_update_successful),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }catch (Exception e){
                Toast.makeText(this, R.string.error_check, Toast.LENGTH_LONG).show();
            }



        }



    @Override
    public Loader onCreateLoader(int i, Bundle bundle) {

        String selection = ProductContract.ProductEntry._ID + "=?";
        String []selectionArgs = new String[] { String.valueOf(ContentUris.parseId(contentProductUri))};
        String[] projections = {
                ProductContract.ProductEntry.COLUMN_PRODUCT_NAME,
                ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME,
                ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL,
                ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONENUMBER,
        };
        return new CursorLoader(this,   // Parent activity context
                contentProductUri,         // Query the content URI for the current product
                projections,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor queryCursor) {
        final StringBuilder myorderData = new StringBuilder();
        if (queryCursor.moveToFirst()) {
            editProductName = (EditText)findViewById(R.id.product_edit_name);
            String productname = queryCursor.getString(queryCursor.getColumnIndex
                    (ProductContract.ProductEntry.COLUMN_PRODUCT_NAME));
            editProductName.setText(productname);
            myorderData.append(productname + "\n");

            textQuantity = (TextView)findViewById(R.id.product_quantity);
            String quantity = queryCursor.getInt(queryCursor.getColumnIndex
                    (ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY))+"";
            textQuantity.setText(quantity);
            myorderData.append(quantity + "\n");

            editProductPrice = (EditText)findViewById(R.id.product_edit_price);
            String productPrice = queryCursor.getDouble(queryCursor.getColumnIndex
                    (ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE))+"";
            editProductPrice.setText(productPrice);
            myorderData.append(productPrice + "\n");

            editSupplierName = (EditText)findViewById(R.id.supplier_edit_name);
            String supplierName = queryCursor.getString(queryCursor.getColumnIndex
                    (ProductContract.ProductEntry.COLUMN_SUPPLIER_NAME));
            editSupplierName.setText(supplierName);
            myorderData.append(supplierName + "\n");

            editSupplierEmail = (EditText)findViewById(R.id.supplier_edit_email);
            editSupplierEmail.setText(queryCursor.getString(queryCursor.getColumnIndex
                    (ProductContract.ProductEntry.COLUMN_SUPPLIER_EMAIL)));


            editSupplierPhone = (EditText)findViewById(R.id.supplier_edit_phone);
            String supplierPhone = queryCursor.getString(queryCursor.getColumnIndex
                    (ProductContract.ProductEntry.COLUMN_SUPPLIER_PHONENUMBER));
            editSupplierPhone.setText(supplierPhone);
            myorderData.append(supplierPhone);

        }
        mOrder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"));
                i.putExtra(Intent.EXTRA_SUBJECT, "Hope you like the Product");
                i.putExtra(Intent.EXTRA_TEXT   , (Serializable) myorderData);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getBaseContext(), R.string.no_email, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    @Override
    public void onLoaderReset(Loader loader) {
        editProductName = (EditText)findViewById(R.id.product_edit_name);
        editProductName.setText("");

        textQuantity = (TextView)findViewById(R.id.product_quantity);
        textQuantity.setText("0");

        editProductPrice = (EditText)findViewById(R.id.product_edit_price);
        editProductPrice.setText("");

        editSupplierName = (EditText)findViewById(R.id.supplier_edit_name);
        editSupplierName.setText("");

        editSupplierEmail = (EditText)findViewById(R.id.supplier_edit_email);
        editSupplierEmail.setText("");

        editSupplierPhone = (EditText)findViewById(R.id.supplier_edit_phone);
        editSupplierPhone.setText("");
    }
}
