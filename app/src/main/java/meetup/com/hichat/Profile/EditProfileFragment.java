package meetup.com.hichat.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import meetup.com.hichat.R;
import meetup.com.hichat.Share.ShareActivity;
import meetup.com.hichat.dialog.ConfirmPasswordDialog;
import meetup.com.hichat.models.UserSettings;
import meetup.com.hichat.models.UsersAccountSettings;
import meetup.com.hichat.util.FireBaseMethods;
import meetup.com.hichat.util.UniversaiImageLoader;

public class EditProfileFragment extends Fragment implements ConfirmPasswordDialog.OnConfirmPassword
{
    private static final String TAG="Edit profile fragment";

    private Context context;
    private ImageView profileImage;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLitener;
    private ImageView saveChanges;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef;
    private FireBaseMethods fireBaseMethods;
    private EditText edUserName;
    private View view;
    private EditText edEmail;
    private String email;
    private EditText edlocation;
    private EditText edphoneNumber;
    private EditText Edpassword;
    private String password;
    ConfirmPasswordDialog dialog;
    private String userId;
    private UserSettings mUserSettings;
    private ProgressDialog progressDialog;



    @Override
    public void ConfrimPasword(String password)
    {
        Log.d(TAG,"On confirm password"+password);
        progressDialog.show();
        FirebaseUser user=mAuth.getCurrentUser();
        AuthCredential credential= EmailAuthProvider.getCredential(mAuth.getCurrentUser().getEmail(),password);
        mAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            Log.d(TAG,"user re authenticated");
                            mAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    if(task.isSuccessful())
                                    {

                                        try{
                                        if(task.getResult().getSignInMethods().size()==1)
                                        {
                                            progressDialog.dismiss();
                                            Log.d(TAG,"This email it has been used");
                                            Toast.makeText(getActivity(), "This email it has been used", Toast.LENGTH_SHORT).show();

                                        }
                                        else
                                        {
                                            Log.d(TAG,"That email is available");
                                            mAuth.getCurrentUser().updateEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        progressDialog.dismiss();
                                                        Log.d(TAG,"Email address updated successfuly");
                                                        Toast.makeText(getActivity(), "Email updated successfuly", Toast.LENGTH_LONG).show();
                                                        fireBaseMethods.updateEmail(email);

                                                    }else
                                                        {
                                                            progressDialog.dismiss();

                                                        }
                                                }
                                            });
                                        }
                                    } catch (NullPointerException e)
                                        {
                                            Log.d(TAG,"NullPointerException"+e.getMessage());
                                        }


                                    }
                                    else
                                        {

                                        }
                                }
                            });


                        }
                        else {
                            Toast.makeText(context, "Failed to re authenticate user", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_edit_profile,container,false);
        profileImage=(ImageView)view.findViewById(R.id.profile_photo);
        context=getActivity();
        fireBaseMethods=new FireBaseMethods(context);
        profileImage=(ImageView)view.findViewById(R.id.profile_photo);
        progressDialog=new ProgressDialog(context);
        progressDialog.setTitle("Loading..");
        progressDialog.setMessage("updating profile");
        setFireBaseAuth();
        edUserName=(EditText)view.findViewById(R.id.userName);
        edEmail=(EditText)view.findViewById(R.id.email);
        edlocation =(EditText)view.findViewById(R.id.location);
        edphoneNumber =(EditText)view.findViewById(R.id.phoneNumber);
        saveChanges=(ImageView)view.findViewById(R.id.saveChange);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                setSaveChanges();
            }
        });
         profileImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                Log.d(TAG,"On changing profile photo");
                 Intent intent= new Intent(getActivity(), ShareActivity.class);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 startActivity(intent);
                 getActivity().finish();

             }
         });


       /* setProfileImage();*/
        ImageView backImage=(ImageView)view.findViewById(R.id.blackArrow);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Log.d(TAG,"Navigting back to profile activity");
                getActivity().finish();
            }
        });
        return view;
}

private void setProfileImage()
{
    Log.d(TAG,"set profile image:setting profile image");
    String imageUrl="vignette.wikia.nocookie.net/megajump/images/2/27/Android_Robot.png/revision/latest?cb=20110822221600";
    UniversaiImageLoader.setImage(imageUrl,profileImage,null,"https://");

}
    private void setSaveChanges()
    {
        final String username=edUserName.getText().toString().trim();
        email=edEmail.getText().toString().trim();
        final String location=edlocation.getText().toString().trim();
        final long phoneNumber=Long.parseLong(edphoneNumber.getText().toString());
        progressDialog.show();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if(!mUserSettings.getUsers().getUser_name().equals(username))
                {
                    fireBaseMethods.updateUserName(username);
                    progressDialog.dismiss();

                }
                else{ progressDialog.dismiss();
                    }
                if(!mUserSettings.getUsers().getEmail().equals(email))
                {

                    dialog=new ConfirmPasswordDialog();
                    dialog.show(getFragmentManager(),getString(R.string.confirm_password));
                    dialog.setTargetFragment(EditProfileFragment.this,1);


                }
                else{ progressDialog.dismiss();
                    }

                if(!(mUserSettings.getUsers().getPhone_number()==phoneNumber))
                {

                    fireBaseMethods.updateUserSettings(null,phoneNumber);
                    progressDialog.dismiss();


                }
                else{ progressDialog.dismiss();
                    }

                if(!mUserSettings.getUsersAccountSettings().getDescription().equals(location))
                {
                    fireBaseMethods.updateUserSettings(location,0);
                    progressDialog.dismiss();

                }
                else{ progressDialog.dismiss();
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



}
