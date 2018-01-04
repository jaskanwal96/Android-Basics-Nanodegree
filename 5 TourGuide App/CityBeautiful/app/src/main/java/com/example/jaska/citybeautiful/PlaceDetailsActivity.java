package com.example.jaska.citybeautiful;

import android.graphics.Typeface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PlaceDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ScrollView lt = (ScrollView) findViewById(R.id.detailscreen);
        Bundle extras = getIntent().getExtras();

        lt.setBackgroundResource(extras.getInt("Background"));

        TextView detailsName = (TextView)findViewById(R.id.detailname);
        detailsName.setText(extras.getString("Name"));
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Bungee-Regular.ttf");
        detailsName.setTypeface(typeface);

        TextView details = (TextView)findViewById(R.id.details);
        details.setText(extras.getString("Details"));

        TextView detailsLocation = (TextView)findViewById(R.id.detailslocation);
        detailsLocation.setText("Location: "+extras.getString("Location"));
        detailsLocation.setTypeface(typeface);

        ImageView detailsImage = (ImageView)findViewById(R.id.detailsimage);
        detailsImage.setBackgroundResource(extras.getInt("Image"));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
