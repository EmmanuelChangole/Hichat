package meetup.com.hichat.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import meetup.com.hichat.R;
import meetup.com.hichat.util.BottomNavigationHelper;
import meetup.com.hichat.util.ConnectivityHelper;
import meetup.com.hichat.util.GridImageAdapter;
import meetup.com.hichat.util.UniversaiImageLoader;

public class ProfileActivity extends AppCompatActivity
{
    private Context context=ProfileActivity.this;
    private static final String TAG="Profile Activity";
    private AlertDialog.Builder alertDialog;
    private static final int GRID_COLUMNS=3;
    private static final int ACTIVITY_NUM=4;
    private ProgressBar progressBar;
    private ImageView profilePhoto;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG,"Creating Profile activity");
        alertDialog=new AlertDialog.Builder(context);
        if(ConnectivityHelper.isConnectedToNetwork(context))
        {
            init();
        }
        else
        {
            alertDialog();
        }






  /*      setUpToolBar();
        setUpNavigation();
        setUpActivityWidgets();
        setProfileImage();
        temGridView();
*/


    }

    private void init()
    {
        Log.d(TAG,"Inflating profile fragments");
        ProfileFragments profileFragments=new ProfileFragments();
        FragmentTransaction transaction=ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container,profileFragments);
        transaction.addToBackStack(getString(R.string.profile_fragments));
        transaction.commit();

    }
    public void onStart() {

        super.onStart();
        if(ConnectivityHelper.isConnectedToNetwork(context))
        {
            init();

        }
        else
        {
            alertDialog();
        }





    }
    private void alertDialog()
    {
        alertDialog.setTitle("Network Error");
        alertDialog.setMessage("Something went wrong please ensure your are connected to internet");
        alertDialog.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                onStart();
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                finish();

            }
        });
        alertDialog.show();




    }


 /*   private void temGridView()
    {
        ArrayList<String> imageUri=new ArrayList<>();
        imageUri.add("");
        imageUri.add("");
        imageUri.add("");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5uXnb8MQ4Cmjs5LHPcwjETpcl8lBd8LdGvNDcGFPYYDZ9lJNX");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVwSIQ6AQKK4TMjn4IFq0P2OEfV3or0upF76aj_xnvuyT-kET7");
        imageUri.add("");
        imageUri.add("");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5uXnb8MQ4Cmjs5LHPcwjETpcl8lBd8LdGvNDcGFPYYDZ9lJNX");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVwSIQ6AQKK4TMjn4IFq0P2OEfV3or0upF76aj_xnvuyT-kET7");
        imageUri.add("");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDlAHTup6cMtOYSs0ozJ3U-DZ_5VzPsqQ5YPWZERnwA6b9qvDJ4g");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5uXnb8MQ4Cmjs5LHPcwjETpcl8lBd8LdGvNDcGFPYYDZ9lJNX");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVwSIQ6AQKK4TMjn4IFq0P2OEfV3or0upF76aj_xnvuyT-kET7");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPx2QsaPRAmS-J-pK47WLG59SR_jJGymIpSWgYWp1IAjg_lWuJgA");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDlAHTup6cMtOYSs0ozJ3U-DZ_5VzPsqQ5YPWZERnwA6b9qvDJ4g");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5uXnb8MQ4Cmjs5LHPcwjETpcl8lBd8LdGvNDcGFPYYDZ9lJNX");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVwSIQ6AQKK4TMjn4IFq0P2OEfV3or0upF76aj_xnvuyT-kET7");
        imageUri.add("");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDlAHTup6cMtOYSs0ozJ3U-DZ_5VzPsqQ5YPWZERnwA6b9qvDJ4g");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5uXnb8MQ4Cmjs5LHPcwjETpcl8lBd8LdGvNDcGFPYYDZ9lJNX");
        imageUri.add();
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPx2QsaPRAmS-J-pK47WLG59SR_jJGymIpSWgYWp1IAjg_lWuJgA");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDlAHTup6cMtOYSs0ozJ3U-DZ_5VzPsqQ5YPWZERnwA6b9qvDJ4g");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5uXnb8MQ4Cmjs5LHPcwjETpcl8lBd8LdGvNDcGFPYYDZ9lJNX");
        imageUri.add("AQKK4https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVwSIQ6TMjn4IFq0P2OEfV3or0upF76aj_xnvuyT-kET7");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPx2QsaPRAmS-J-pK47WLG59SR_jJGymIpSWgYWp1IAjg_lWuJgA");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDlAHTup6cMtOYSs0ozJ3U-DZ_5VzPsqQ5YPWZERnwA6b9qvDJ4g");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5uXnb8MQ4Cmjs5LHPcwjETpcl8lBd8LdGvNDcGFPYYDZ9lJNX");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVwSIQ6AQKK4TMjn4IFq0P2OEfV3or0upF76aj_xnvuyT-kET7");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPx2QsaPRAmS-J-pK47WLG59SR_jJGymIpSWgYWp1IAjg_lWuJgA");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDlAHTup6cMtOYSs0ozJ3U-DZ_5VzPsqQ5YPWZERnwA6b9qvDJ4g");
        imageUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5uXnb8MQ4Cmjs5LHPcwjETpcl8lBd8LdGvNDcGFPYYDZ9lJNX");
        imageUri.add("");

        setImageGrid(imageUri);


    }

    private void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileToolBar);
        ImageView profileMenu=(ImageView)findViewById(R.id.more);
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Loading the account settings");
                Intent intent=new Intent(context,AccountSettingActivity.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "Menu item started" + item);
                switch (item.getItemId()) {
                    case R.id.profileToolBar:
                        Log.d(TAG, "on click navigating to profile preferences");


                }

                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return true;
    }

    private void setUpNavigation()
    {
        Log.d(TAG,"Setting up navigation view");
        BottomNavigationViewEx bottomNavigationViewEx=(BottomNavigationViewEx)findViewById(R.id.bnve);
        BottomNavigationHelper.setUpNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(context,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);



    }
    private void setProfileImage()
    {
        Log.d(TAG,"Setting profile photo");
        String imageUrl="vignette.wikia.nocookie.net/megajump/images/2/27/Android_Robot.png/revision/latest?cb=20110822221600";
        UniversaiImageLoader.setImage(imageUrl,profilePhoto,progressBar ,"https://");


    }
    private void setUpActivityWidgets()
    {
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        profilePhoto=(ImageView)findViewById(R.id.profile_image);
    }
    private void setImageGrid(ArrayList<String> imgUrls)
    {
        int gridWidth=getResources().getDisplayMetrics().widthPixels;
        int image_width=gridWidth/GRID_COLUMNS;
        GridView gridView=(GridView)findViewById(R.id.gridView);
        gridView.setColumnWidth(image_width);
        GridImageAdapter adapter=new GridImageAdapter(context,R.layout.grid_image_view,"",imgUrls);
        gridView.setAdapter(adapter);

    }*/
}
