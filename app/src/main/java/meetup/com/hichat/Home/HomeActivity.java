package meetup.com.hichat.Home;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import meetup.com.hichat.R;
import meetup.com.hichat.util.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity
{
    private String TAG="HomeActivity";
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout=(TabLayout)findViewById(R.id.homeTab);
        appBarLayout=(AppBarLayout)findViewById(R.id.homeAppBar);
        viewPager=(ViewPager)findViewById(R.id.homeViewPager);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.AddFragment(new LoginFragments(),"Login");
        viewPagerAdapter.AddFragment(new SignUpFragment(),"Sign up");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}


