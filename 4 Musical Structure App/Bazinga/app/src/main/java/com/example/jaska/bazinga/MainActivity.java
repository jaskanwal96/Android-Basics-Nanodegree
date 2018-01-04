package com.example.jaska.bazinga;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/*
Intent with timeout
https://stackoverflow.com/questions/6304035/how-to-display-an-activity-automatically-after-5-seconds
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView)findViewById(R.id.app_label);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Ranchers-Regular.ttf");
        tv.setTypeface(typeface);
        //Displaying another activity in two seconds:
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this, SongsList.class);
                startActivity(mainIntent);
            }
        }, 2000);

    }

}
