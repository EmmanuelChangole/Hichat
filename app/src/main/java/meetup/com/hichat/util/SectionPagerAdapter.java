package meetup.com.hichat.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
           /* class for storing fragments*/

public class SectionPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SectionPager";
    /*Array list storing fragments*/
    private final List<Fragment> mFragmentsList = new ArrayList<>();


    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    /*Method for getting fragment */
    public Fragment getItem(int position) {
        return mFragmentsList.get(position);
    }

    @Override
    /*Method for getting the number of fragments*/
    public int getCount() {
        return mFragmentsList.size();
    }
    /*Method for adding fragments*/

    public void addFragment(Fragment fragment) {
        mFragmentsList.add(fragment);
    }
}

