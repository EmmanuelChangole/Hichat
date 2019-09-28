package meetup.com.hichat.Profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.sql.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import meetup.com.hichat.R;
import meetup.com.hichat.models.Photo;
import meetup.com.hichat.models.UserSettings;
import meetup.com.hichat.models.Users;
import meetup.com.hichat.models.UsersAccountSettings;
import meetup.com.hichat.util.BottomNavigationHelper;
import meetup.com.hichat.util.FireBaseMethods;
import meetup.com.hichat.util.GridImageAdapter;
import meetup.com.hichat.util.UniversaiImageLoader;

public class ProfileFragments extends Fragment
{
    private static final String TAG="Profile fragments";
    //Constants
    private static final int GRID_NUMBERS_COLUMNS =4 ;
    private static final int ACTIVITY_NUM=4;
    //Widgets
    private TextView edPost,edFollowers,edFollwing,edDisplayName,edUserName,edDescription,editProfile;
    private ProgressBar progressBar;
    private CircleImageView mprofilePhoto;
    private GridView gridView;
    private Toolbar toolbar;
    private ImageView profileMenu;
    private BottomNavigationViewEx bottomNavigationViewEx;

    Context mcontext;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLitener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FireBaseMethods fireBaseMethods;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.d(TAG,"Starting fragments");
        View view=inflater.inflate(R.layout.fragment_profile,container,false);

        fireBaseMethods=new FireBaseMethods(getActivity());
        edPost=view.findViewById(R.id.tvPost);
        edFollowers=view.findViewById(R.id.tvFollower);
        editProfile=view.findViewById(R.id.tvEditProfile);
        edFollwing=view.findViewById(R.id.tvFollowing);
       // edDisplayName=view.findViewById(R.id.displayName);
        edUserName=view.findViewById(R.id.ProfileName);
        edDescription=view.findViewById(R.id.location);
        progressBar=view.findViewById(R.id.progressBar);
        gridView=view.findViewById(R.id.gridView);
        toolbar=view.findViewById(R.id.profileToolBar);
        profileMenu=view.findViewById(R.id.more);
        mprofilePhoto=view.findViewById(R.id.profile_image);
        bottomNavigationViewEx=view.findViewById(R.id.bottomNavigationView);
        mcontext=getActivity();
        setUpNavigation();
        setUpToolBar();
        setFireBaseAuth();
        setUpGrid();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Navigating  to EditProfile Activity");
                Intent intent=new Intent(getActivity(),AccountSettingActivity.class);
                intent.putExtra(getString(R.string.calling_Activity),getString(R.string.profile_Activity));
                startActivity(intent);
            }
        });

        return view;
    }
    private void setUpGrid()
    {
        Log.d(TAG,"Setting grid view");
        final ArrayList<Photo> photos=new ArrayList<>();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        Query query=reference.child(mcontext.getString(R.string.dbName_user_photo))
                             .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot singleDataSnaphot:dataSnapshot.getChildren())
                {
                    photos.add(singleDataSnaphot.getValue(Photo.class));
                }
                //setting up image gird;
                int gridWidth=getResources().getDisplayMetrics().widthPixels;
                int imageWidth=gridWidth/GRID_NUMBERS_COLUMNS;
                gridView.setColumnWidth(imageWidth);

                ArrayList<String> imageUrls=new ArrayList<>();
                for(int i=0;i<photos.size();i++)
                {
                    imageUrls.add(photos.get(i).getImage_path());

                }
                GridImageAdapter adapter=new GridImageAdapter(getActivity(),R.layout.grid_image_view,"",imageUrls);
                gridView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Log.d(TAG,"Query cancelled");

            }
        });


    }



    private void setProfileWidgets(UserSettings userSettings)
    {
        Log.d(TAG,"Setting widgets from data retrieved from the database");
       Users users=userSettings.getUsers();
        UsersAccountSettings usersAccountSettings=userSettings.getUsersAccountSettings();
        UniversaiImageLoader.setImage(usersAccountSettings.getProfile_photo(),mprofilePhoto,null,"");

        edPost.setText(String.valueOf(usersAccountSettings.getPosts()));
        edFollowers.setText(String.valueOf(usersAccountSettings.getFollowers()));
        edFollwing.setText(String.valueOf(usersAccountSettings.getFollowing()));
//        edDisplayName.setText(usersAccountSettings.getDisplay_name());
       edUserName.setText(usersAccountSettings.getUser_name());
       edDescription.setText("Lives in "+usersAccountSettings.getDescription());
        progressBar.setVisibility(View.GONE);





    }

    /*Bottom navigation view*/

    private void setUpNavigation()
    {
        Log.d(TAG,"Setting up navigation view");

        BottomNavigationHelper.setUpNavigationView(bottomNavigationViewEx);
        BottomNavigationHelper.enableNavigation(mcontext,bottomNavigationViewEx);
        Menu menu=bottomNavigationViewEx.getMenu();
        MenuItem menuItem=menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);



    }
    private void setUpToolBar() {
        ((ProfileActivity)getActivity()).setSupportActionBar(toolbar);

        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Loading the account settings");
                Intent intent=new Intent(mcontext,AccountSettingActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthLitener);


    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthLitener!=null)
        {
            mAuth.removeAuthStateListener(mAuthLitener);
        }
    }

    private void setFireBaseAuth()
    {
        Log.d(TAG,"SetupFirebaseAuth");
        mAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        myRef=firebaseDatabase.getReference();
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
                    Log.d(TAG,"user signed out");


                }

            }
        };
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                /*Retrieving user information from the database */
                setProfileWidgets(fireBaseMethods.getAccountSettings(dataSnapshot));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
