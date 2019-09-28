package meetup.com.hichat.Share;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import meetup.com.hichat.R;
import meetup.com.hichat.util.BottomNavigationHelper;
import meetup.com.hichat.util.Permissions;
import meetup.com.hichat.util.SectionPagerAdapter;

public class ShareActivity extends AppCompatActivity
{
    private static final String TAG="ShareActivity";
            /*Constants*/
    private static final int ACTIVITY_NUM=2;
    private static final int REQUEST_CODE=1;

    private Context context=ShareActivity.this;
    private ViewPager mviewPager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        Log.d(TAG,"Creating share activity");

        if(checkPermissionArray(Permissions.PERMISIONS))
        {
            setViewPager();
        }
        else {
            verifyPermission(Permissions.PERMISIONS);

        }
        //setUpNavigation();



    }

    private void setViewPager()
    {
        SectionPagerAdapter adapter=new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new GalleryFragment());
        adapter.addFragment(new PhotoFragment());
        mviewPager=(ViewPager)findViewById(R.id.container);
        mviewPager.setAdapter(adapter);
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabsBottom);
        tabLayout.setupWithViewPager(mviewPager);
        tabLayout.getTabAt(0).setText(getString(R.string.gallery));
        tabLayout.getTabAt(1).setText(getString(R.string.photo));




    }

    private void verifyPermission(String[] permisions)
    {
      Log.d(TAG,"Verify permissions");
      ActivityCompat.requestPermissions(ShareActivity.this,permisions,REQUEST_CODE);





    }
    public int getCurrentTabNumber()
    {
        return mviewPager.getCurrentItem();
    }


    /*Check array of permission*/

    private boolean checkPermissionArray(String[] permisions)
    {
        Log.d(TAG,"Checking permission");
        for(int i=0;i<permisions.length;i++)
        {
            String  check=permisions[i];
            if (!checkPermission(check))
                return false;


        }
        return true;
    }

    /*Check a single permissions*/

    public boolean checkPermission(String check)
    {
        Log.d(TAG,"Checking permission");
        int permissionRequest= (int) ActivityCompat.checkSelfPermission(ShareActivity.this, String.valueOf(check));
        if(permissionRequest!= PackageManager.PERMISSION_GRANTED)
        {
            Log.d(TAG,"Permission was not granted for"+check);
            return false;
        }
        Log.d(TAG,"Permission was granted for"+check);
        return true;

    }

    private void setUpNavigation()
    {
        Log.d(TAG,"Setting up navigation view");
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx)findViewById(R.id.bottomNavigationView);
        BottomNavigationHelper.setUpNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(context,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);



    }
    public  int getTask()
    {
        Log.d(TAG,"get task"+getIntent().getFlags());
        return getIntent().getFlags();
    }
}
