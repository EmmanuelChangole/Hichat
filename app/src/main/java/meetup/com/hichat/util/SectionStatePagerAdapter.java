package meetup.com.hichat.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SectionStatePagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> fragmentsList=new ArrayList<>();
    private final HashMap<Fragment,Integer> fragmentIntegerHashMap=new HashMap<>();
    private final HashMap<String,Integer> fragmentNumber=new HashMap<>();
    private final HashMap<Integer,String> fragmentNames=new HashMap<>();


    public SectionStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }
    public void addFragment(Fragment fragment,String fragmentName)
    {
        fragmentsList.add(fragment);
        fragmentIntegerHashMap.put(fragment,fragmentsList.size()-1);
        fragmentNumber.put(fragmentName,fragmentsList.size()-1);
        fragmentNames.put(fragmentsList.size()-1,fragmentName);
    }

    public Integer getFragmentNumber(String fragmentName)
    {
        if(fragmentNumber.containsKey(fragmentName))
            return fragmentNumber.get(fragmentName);
        return null;


    }
    public Integer getFragmentNumber(Fragment fragment)
    {
        if(fragmentNumber.containsKey(fragment))
            return fragmentNumber.get(fragment);
        return null;


    }
    public String getFragmentName(Integer fragment)
    {
        if(fragmentNames.containsKey(fragment))
            return fragmentNames.get(fragment);
        return null;


    }


}
