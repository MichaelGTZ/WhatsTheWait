package com.example.whatsthewait;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsthewait.fragments.ReserveFragment;
import com.example.whatsthewait.fragments.InfoFragment;
import com.example.whatsthewait.fragments.ReviewsFragment;

public class RestaurantDetailFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "Info", "Reserve", "Reviews" };
    private Context context;

    public RestaurantDetailFragmentPagerAdapter (FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return InfoFragment.newInstance(position + 1);
            case 1:
                return ReserveFragment.newInstance(position + 1);
            case 2:
                return ReviewsFragment.newInstance(position + 1);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
