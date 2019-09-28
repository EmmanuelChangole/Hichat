package meetup.com.hichat.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import meetup.com.hichat.Home.HomeActivity;
import meetup.com.hichat.Likes.LikesActivity;
import meetup.com.hichat.Home.MainActivity;
import meetup.com.hichat.Profile.ProfileActivity;
import meetup.com.hichat.R;
import meetup.com.hichat.Search.SearchActivity;
import meetup.com.hichat.Share.ShareActivity;

public class BottomNavigationHelper {
    private static String Tag = "BottomNavigtionViewHelper";

    public static void setUpNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {

        Log.d(Tag, "setting bottom navigation");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);

    }
    public static void enableNavigation(final Context context,BottomNavigationViewEx viewEx)
    {
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.ic_house:
                        Intent house=new Intent(context, MainActivity.class);
                        context.startActivity(house);

                        break;
                    case R.id.ic_search:
                        Intent search=new Intent(context, SearchActivity.class);
                        context.startActivity(search);
                        break;
                    case R.id.ic_add:
                        Intent add=new Intent(context, ShareActivity.class);
                        context.startActivity(add);

                        break;
                    case R.id.ic_alert:
                        Intent likes=new Intent(context, LikesActivity.class);
                        context.startActivity(likes);

                        break;
                    case R.id.ic_android:
                        Intent profile=new Intent(context, ProfileActivity.class);
                        context.startActivity(profile);

                        break;


                }


                return false;
            }
        });



    }

}
