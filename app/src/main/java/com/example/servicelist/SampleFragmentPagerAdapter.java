package com.example.servicelist;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;

    private int numOfTabs;
    private Context mContext;


    public SampleFragmentPagerAdapter(@NonNull FragmentManager fm, int numOfTabs, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs = numOfTabs;
        this.mContext = context;
    }

    /*public SampleFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        this.mContext=context;
    }*/

@NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ServiceFragment();
            case 1:
                return new RequestFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
