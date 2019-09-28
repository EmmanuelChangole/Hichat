package meetup.com.hichat.Profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DialogTitle;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import meetup.com.hichat.R;
import meetup.com.hichat.dialog.ConfirmPasswordDialog;
import meetup.com.hichat.models.UserSettings;
import meetup.com.hichat.models.Users;
import meetup.com.hichat.models.UsersAccountSettings;
import meetup.com.hichat.util.ConnectivityHelper;
import meetup.com.hichat.util.FireBaseMethods;
import meetup.com.hichat.util.UniversaiImageLoader;

public class EditProfileActivity extends AppCompatActivity
{
    private static final String TAG="Edit profile fragment";
    private Context context;
    private ImageView profileImage;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLitener;
    private ImageView saveChanges;
    private FirebaseDatabase firebaseDatabase;
    private AlertDialog.Builder alertDialog;
    private DatabaseReference myRef;
    private FireBaseMethods fireBaseMethods;
    private EditText edUserName;
    private LayoutInflater inflater;
    private View view;
    private EditText edEmail;
    private EditText edlocation;
    private EditText edphoneNumber;
    private EditText Edpassword;
    private String password;
    ConfirmPasswordDialog dialog;
    private String userId;
    private UserSettings mUserSettings;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=EditProfileActivity.this;
        alertDialog=new AlertDialog.Builder(context);
        setContentView(R.layout.fragment_edit_profile);
        fireBaseMethods=new FireBaseMethods(context);
        profileImage=(ImageView)findViewById(R.id.profile_photo);
        inflater=getLayoutInflater();
        setFireBaseAuth();
        edUserName=(EditText)findViewById(R.id.userName);
        edEmail=(EditText)findViewById(R.id.email);
        edlocation =(EditText)findViewById(R.id.location);
        edphoneNumber =(EditText)findViewById(R.id.phoneNumber);
        saveChanges=(ImageView)findViewById(R.id.saveChange);



        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

             setSaveChanges();
            }
        });





/* setProfileImage();*/

        ImageView backImage=(ImageView)findViewById(R.id.blackArrow);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.d(TAG,"Navigting back to profile activity");
                finish();
            }
        });
    }
    private void setSaveChanges()
    {
        final String username=edUserName.getText().toString().trim();
       final String email=edEmail.getText().toString().trim();
       final String location=edlocation.getText().toString().trim();
       final long phoneNumber=Long.parseLong(edphoneNumber.getText().toString());
       myRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot)
           {

                   if(!mUserSettings.getUsers().getUser_name().equals(username))
                   {
                       fireBaseMethods.updateUserName(username);

                   }
                  if(!mUserSettings.getUsers().getEmail().equals(email))
                  {

                      dialog=new ConfirmPasswordDialog();
                    dialog.show(getSupportFragmentManager(),getString(R.string.confirm_password));
                  }



           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });


    }


    private void setProfileWidgets(UserSettings userSettings)
    {
        Log.d(TAG,"Setting widgets from data retrieved from the database");
        mUserSettings=userSettings;
        UsersAccountSettings usersAccountSettings=userSettings.getUsersAccountSettings();
        UniversaiImageLoader.setImage(usersAccountSettings.getProfile_photo(),profileImage,null,"");

        edUserName.setText(usersAccountSettings.getUser_name());
        edEmail.setText(userSettings.getUsers().getEmail());
        edlocation.setText(usersAccountSettings.getDescription());
        edphoneNumber.setText(String.valueOf(userSettings.getUsers().getPhone_number()));






    }
    public void onStart() {

        super.onStart();
        if(ConnectivityHelper.isConnectedToNetwork(context))
        {

        }
        else
            {
          alertDialog();
        }
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
        userId=mAuth.getCurrentUser().getUid();
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
/*Retrieving userdatabase information from the */

                setProfileWidgets(fireBaseMethods.getAccountSettings(dataSnapshot));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void alertDialog()
    {
        alertDialog.setTitle("Network Error");
        alertDialog.setMessage("Something went wrong please ensure your are connected to internet");
        alertDialog.setPositiveButton("Reload", new DialogInterface.OnClickListener() {
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



}
