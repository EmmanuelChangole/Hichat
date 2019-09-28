package meetup.com.hichat.Likes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import meetup.com.hichat.R;
import meetup.com.hichat.util.BottomNavigationHelper;

public class LikesActivity extends AppCompatActivity
{

    private static final String TAG="Likes Activity";
    private Context context=LikesActivity.this;
    private LayoutInflater inflater;
    private View view;
    private static final int ACTIVITY_NUM=3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflater=getLayoutInflater();
        view=inflater.inflate(R.layout.fragment_main,null);
        Log.d(TAG,"Creating Likes activity");
        setUpNavigation();


    }
    private void setUpNavigation()
    {
        Log.d(TAG,"Setting up navigation view");
        BottomNavigationViewEx bottomNavigationViewEx=view.findViewById(R.id.bottomNavigationView);
        BottomNavigationHelper.setUpNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(context,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);



    }
}
