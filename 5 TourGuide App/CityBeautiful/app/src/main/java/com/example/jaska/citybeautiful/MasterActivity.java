package com.example.jaska.citybeautiful;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

public class MasterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SimpleFragmentAdapter sf = new SimpleFragmentAdapter(getSupportFragmentManager(), MasterActivity.this);
        viewPager.setAdapter(sf);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
