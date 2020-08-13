package com.example.whatsthewait;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsthewait.fragments.InfoFragment;
import com.example.whatsthewait.fragments.ReviewsFragment;
import com.example.whatsthewait.models.BusinessDetailed;

public class RestaurantDetailFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Info", "Reviews" };
    private Context context;
    private BusinessDetailed businessDetailed;

    public RestaurantDetailFragmentPagerAdapter (FragmentManager fm, Context context, BusinessDetailed businessDetailed) {
        super(fm);
        this.context = context;
        this.businessDetailed = businessDetailed;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return InfoFragment.newInstance(position + 1, businessDetailed);
            case 1:
                return ReviewsFragment.newInstance(position + 1, businessDetailed);
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
