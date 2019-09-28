package meetup.com.hichat.Search;

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

public class SearchActivity extends AppCompatActivity
{
    private static String Tag = "Search Activity";
    private static final int ACTIVITY_NUM=1;
    LayoutInflater inflater;
    private Context context=SearchActivity.this;
    private View view;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(Tag,"Creating search activity");
        inflater=getLayoutInflater();
        view=inflater.inflate(R.layout.fragment_main,null);
        setUpNavigation();
    }

    private void setUpNavigation()
    {
        Log.d(Tag,"Setting up navigation view");
        BottomNavigationViewEx bottomNavigationViewEx=view.findViewById(R.id.bottomNavigationView);
        BottomNavigationHelper.setUpNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(context,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);



    }
}
