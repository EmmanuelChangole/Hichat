package meetup.com.hichat.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import meetup.com.hichat.R;
import meetup.com.hichat.util.BottomNavigationHelper;

public class MainFragment extends Fragment
{
    private static final String TAG="Main activity fragments";
    private static final int ACTIVITY_NUM = 0;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_main,container,false);

       setUpNavigation();
       return view;
    }

    private void setUpNavigation() {
        Log.d(TAG, "Setting up navigation view");
        BottomNavigationViewEx bottomNavigationViewEx = view.findViewById(R.id.bottomNavigationView);
        BottomNavigationHelper.setUpNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(getActivity(), bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

    }
}
