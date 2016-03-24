package com.example.liam.itp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Liam on 06/03/2016.
 */
public class PagerAdaptor extends FragmentPagerAdapter {

    public PagerAdaptor(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:

                return new FragmentOne();
            case 1:

                return new FragmentTwo();
            case 2:

                return new FragmentThree();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
