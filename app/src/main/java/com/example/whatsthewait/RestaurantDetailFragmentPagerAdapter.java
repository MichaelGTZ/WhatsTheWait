package com.example.whatsthewait;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsthewait.fragments.DineInFragment;
import com.example.whatsthewait.fragments.InfoFragment;
import com.example.whatsthewait.fragments.MenuFragment;
import com.example.whatsthewait.fragments.ReviewsFragment;
import com.example.whatsthewait.fragments.ToGoFragment;

public class RestaurantDetailFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Info", "Menu", "Dine In", "To Go", "Reviews" };
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
                return MenuFragment.newInstance(position + 1);
            case 2:
                return DineInFragment.newInstance(position + 1);
            case 3:
                return ToGoFragment.newInstance(position + 1);
            case 4:
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
