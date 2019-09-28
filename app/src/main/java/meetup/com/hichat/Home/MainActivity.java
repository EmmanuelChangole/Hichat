package meetup.com.hichat.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.nostra13.universalimageloader.core.ImageLoader;

import meetup.com.hichat.R;
import meetup.com.hichat.messages.MessaagesFragment;
import meetup.com.hichat.util.BottomNavigationHelper;
import meetup.com.hichat.util.FireBaseMethods;
import meetup.com.hichat.util.SectionPagerAdapter;
import meetup.com.hichat.util.UniversaiImageLoader;

public class MainActivity extends AppCompatActivity {
    /*Log*/
    private static final String TAG = "MainActivity";
    /*Constants*/
    private static final int ACTIVITY_NUM = 0;
    /*Fire base declaration*/
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLitener;
    private FireBaseMethods fireBaseMethods;
    /*Widgets declaration*/
    BottomNavigationViewEx above;
    private Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "On create starting");

        setFireBaseAuth();
        initializeImageLoader();
        setUpViewPager();





    }


    /*loading the three tabs */
    private void setUpViewPager() {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment());
        adapter.addFragment(new MessaagesFragment());
        adapter.addFragment(new CameraFragment());
        ViewPager viewPager1 = (ViewPager) findViewById(R.id.container);

        viewPager1.setAdapter(adapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager1);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_feeds);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_chat_logo);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_camera);


    }

    /*setting the navigation bottom view*/
    private void setUpNavigation() {
        Log.d(TAG, "Setting up navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavigationView);
        BottomNavigationHelper.setUpNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(context, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }


                     /*Loading the images*/
    private void initializeImageLoader() {
        UniversaiImageLoader imageLoader = new UniversaiImageLoader(context);
        ImageLoader.getInstance().init(imageLoader.getConfig());
    }

    /*.......................FireBase.................*/
    @Override
    public void onStart() {
        super.onStart();
        fireBaseMethods.onChangeState();


    }

    @Override
    protected void onStop() {
        super.onStop();
        fireBaseMethods.clearState();

    }

    private void setFireBaseAuth()
    {
        Log.d(TAG, "SetupFirebaseAuth");
       fireBaseMethods=new FireBaseMethods(context);
       fireBaseMethods.initFirbase();
    }



}





