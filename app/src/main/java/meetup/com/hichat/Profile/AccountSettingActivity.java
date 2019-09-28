package meetup.com.hichat.Profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import meetup.com.hichat.Home.MainActivity;
import meetup.com.hichat.R;
import meetup.com.hichat.util.BottomNavigationHelper;
import meetup.com.hichat.util.FireBaseMethods;
import meetup.com.hichat.util.SectionStatePagerAdapter;

public class AccountSettingActivity extends AppCompatActivity {
    private static final String TAG = "Account Settings";
    private static final int ACTIVITY_NUM=4;
    private Context context;
    public SectionStatePagerAdapter sectionPagerAdapter;
    private AlertDialog.Builder alertDialog;
    private ViewPager mviewPager;
    private RelativeLayout relativeLayout1;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLitener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);
        context = AccountSettingActivity.this;
        Log.d(TAG, "Creating account settings");
        ImageView backArrow = (ImageView) findViewById(R.id.blackArrow);
        mviewPager=(ViewPager)findViewById(R.id.container);
        relativeLayout1=(RelativeLayout)findViewById(R.id.relativeLayout1);
        alertDialog=new AlertDialog.Builder(context);
        setFireBaseAuth();
        setUpNavigation();
        setUpSettingList();
        setUpFragment();
        getIncomingIntent();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void getIncomingIntent()
    {
        Intent intent=getIntent();
        if(intent.hasExtra(getString(R.string.selectedImage)))
        {

            Log.d(TAG,"Get incoming intent: New incoming intent from image url");
            if(intent.getStringExtra(getString(R.string.return_to_fragment)).equals(getString(R.string.edit_profile)));
            {
                FireBaseMethods  fireBaseMethods=new FireBaseMethods(context);
                fireBaseMethods.uploadPhoto(getString(R.string.profile_photo),null,0,
                        intent.getStringExtra(getString(R.string.selectedImage)));
                setViewPager(sectionPagerAdapter.getFragmentNumber(getString(R.string.edit_profile)));
            }

        }
        if(intent.hasExtra(getString(R.string.calling_Activity)))
        {
            Log.d(TAG,"Get incoming intent: Receiving calling intent");
            setViewPager(sectionPagerAdapter.getFragmentNumber(getString(R.string.edit_profile)));
        }
    }

    public void setUpFragment()
    {
        sectionPagerAdapter=new SectionStatePagerAdapter(getSupportFragmentManager());
        sectionPagerAdapter.addFragment(new EditProfileFragment(),getString(R.string.edit_profile));
       // sectionPagerAdapter.addFragment(new SignOutFragment(),getString(R.string.sign_out));



    }
    public void setViewPager(int fragmentNumber)
    {
        relativeLayout1.setVisibility(View.GONE);
        Log.d(TAG,"Navigating to fragment number"+fragmentNumber);
        mviewPager.setAdapter(sectionPagerAdapter);
        mviewPager.setCurrentItem(fragmentNumber);


    }

    private void setUpSettingList() {
        Log.d(TAG, "loading the setting");
        ListView listView = (ListView) findViewById(R.id.listViewAccountSettings);
        ArrayList<String> options = new ArrayList<>();
        options.add(getString(R.string.edit_profile));
        options.add(getString(R.string.sign_out));
        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d(TAG,"Navigating to fragment position"+position);

                //setViewPager(position);
                if (position==0)
                {
                   /* Intent intent=new Intent(context,EditProfileActivity.class);
                    startActivity(intent);*/
                   setViewPager(position);

                }
                else
                {
                    alertDialog.setTitle("Logout");
                    alertDialog.setMessage("Are you sure you want to logout");
                    alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            mAuth.signOut();
                            finish();



                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();


                }

            }
        });

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
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthLitener);


    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthLitener!=null)
        {
            mAuth.removeAuthStateListener(mAuthLitener);
        }
    }


    private void setFireBaseAuth()
    {
        Log.d(TAG,"SetupFirebaseAuth");
        mAuth = FirebaseAuth.getInstance();
        mAuthLitener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user=firebaseAuth.getCurrentUser();

                if(user!=null)
                {
                    Log.d(TAG,"User signed in"+user.getUid());

                }
                else
                {
                    Log.d(TAG,"user signed out and navigating to login activity");
                    Intent intent=new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                }

            }
        };

    }


}
