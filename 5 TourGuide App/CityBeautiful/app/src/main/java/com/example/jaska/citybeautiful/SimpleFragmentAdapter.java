package com.example.jaska.citybeautiful;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jaska on 19-Dec-17.
 */

class SimpleFragmentAdapter extends FragmentPagerAdapter{
    private Context mContext;
    public SimpleFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TouristFragment();
        } else if (position == 1) {
            return new RestaurantsFragment();
        } else if (position == 2) {
            return new CollegeFragment();
        } else {
            return new EventsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        if (position == 0) {
            return mContext.getResources().getString(R.string.category_tourist);
        } else if (position == 1) {
            return mContext.getResources().getString(R.string.category_restaurant);
        } else if (position == 2) {
            return mContext.getResources().getString(R.string.category_college);
        } else {
            return mContext.getResources().getString(R.string.category_event);
        }
    }
}
