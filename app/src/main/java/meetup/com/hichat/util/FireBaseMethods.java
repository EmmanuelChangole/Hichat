package meetup.com.hichat.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import meetup.com.hichat.Home.HomeActivity;
import meetup.com.hichat.Home.MainActivity;
import meetup.com.hichat.Profile.AccountSettingActivity;
import meetup.com.hichat.R;
import meetup.com.hichat.models.Photo;
import meetup.com.hichat.models.UserSettings;
import meetup.com.hichat.models.Users;
import meetup.com.hichat.models.UsersAccountSettings;

public class FireBaseMethods
{
    private  static String Tag="FireBaseMethods";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLitener;
    private ProgressDialog progressDialog;
    private FirebaseDatabase myRefDatabase;
    private FirebaseUser user;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private Context context;
    boolean value=false;
    private String userId;

    private double mphotoUploadProogress=0;

    public FireBaseMethods(Context context) {
        this.context = context;
        progressDialog=new ProgressDialog(context);
        mAuth=FirebaseAuth.getInstance();
        myRefDatabase=FirebaseDatabase.getInstance();
        myRef =myRefDatabase.getReference();
        mStorageRef= FirebaseStorage.getInstance().getReference();
        progressDialog.setTitle("Loading..");
        progressDialog.setMessage("please wait...");

        if(mAuth.getCurrentUser()!=null)
        {
            userId=mAuth.getCurrentUser().getUid();


        }

    }
    /*Registering users using email and password*/
    public void createNewAccount(final String email, String password, final String username)
    {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Account created successfully ", Toast.LENGTH_SHORT).show();
                    sendVerifictionEmail();
                    userId=mAuth.getCurrentUser().getUid();
                    addnewUser(email,username,"","");

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {   progressDialog.dismiss();
                Toast.makeText(context, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    /*Checking if user exists*/
    public boolean checkUser(String userName, DataSnapshot dataSnapshot)
    {
        Log.d(Tag,"Checking if"+userName +"existing");
        Users users=new Users();
        for(DataSnapshot ds:dataSnapshot.child(userId).getChildren())
        {
            Log.d(Tag,"DataSnapShot"+ds);
            users.setUser_name(ds.getValue(Users.class).getUser_name());
            if(StringManipulation.expandUserName(users.getUser_name()).equals(userName))
            {
                Log.d(Tag,"Found a match");
                return true;

            }

        }

     return false;
    }
    public void addnewUser(String email,String userName,String description,String profile_photo_url)
  {
    Users  users=new Users(userId,0,email,userName);
    myRef.child(context.getString(R.string.dbName_users))
            .child(userId)
            .setValue(users);
    UsersAccountSettings accountSettings=new UsersAccountSettings
            (
                    description,
                    userName,
                    0,
                    0,
                    0,
                    profile_photo_url,
                    userName



            );
    myRef.child(context.getString(R.string.dbName_Account_Settings))
            .child(userId)
            .setValue(accountSettings);

  }

public void sendVerifictionEmail()
{
    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
    if(user!=null)
    {
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(context, "Verification link send to your email", Toast.LENGTH_SHORT).show();

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(context, "Fail"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

}
/*Data SnapShot retrieving user  from database*/
 public UserSettings getAccountSettings(DataSnapshot dataSnapshot)
  {
  Log.d(Tag,"Retrieving user Account settings  from the  database ");
  UsersAccountSettings usersAccountSettings=new UsersAccountSettings();
  Users users=new Users();
  for (DataSnapshot ds:dataSnapshot.getChildren()){
      /*User Account settings node*/
      if(ds.getKey().equals(context.getString(R.string.dbName_Account_Settings)))
      {
          Log.d(Tag,"Getting account settings node"+ds.child(userId));
          try{
              usersAccountSettings.setDisplay_name
                      (ds.child(userId)
                              .getValue(UsersAccountSettings.class)
                              .getDisplay_name()
                      );
              usersAccountSettings.setUser_name
                      (ds.child(userId)
                              .getValue(UsersAccountSettings.class)
                              .getUser_name()
                      );
              usersAccountSettings.setDescription(
                      ds.child(userId)
                              .getValue(UsersAccountSettings.class)
                              .getDescription()
              );
              usersAccountSettings.setProfile_photo(
                      ds.child(userId)
                              .getValue(UsersAccountSettings.class)
                              .getProfile_photo()
              );
              usersAccountSettings.setPosts(
                      ds.child(userId)
                              .getValue(UsersAccountSettings.class)
                              .getPosts()
              );
              usersAccountSettings.setFollowing(
                      ds.child(userId)
                              .getValue(UsersAccountSettings.class)
                              .getFollowing()
              );
              usersAccountSettings.setFollowers(
                      ds.child(userId)
                              .getValue(UsersAccountSettings.class)
                              .getFollowers()
              );

          }
          catch (NullPointerException e)
          {
              Log.e(Tag,"NullPointerException  "+e.getMessage());
          }
          /*User node*/
      }
      /*User node*/
      if(ds.getKey().equals(context.getString(R.string.dbName_users)))
      {
          Log.d(Tag,"Getting Users node"+ds.child(userId));
          users.setUser_name(
                  ds.child(userId)
                  .getValue(Users.class)
                  .getUser_name()
          );
          users.setEmail(
                  ds.child(userId)
                  .getValue(Users.class)
                  .getEmail()
          );
          users.setPhone_number(
                  ds.child(userId)
                  .getValue(Users.class)
                  .getPhone_number()

          );

          users.setUser_id(
                  ds.child(userId)
                  .getValue(Users.class)
                  .getUser_id()
          );


          Log.d(Tag,"Retrieving user information"+users.toString());

      }

  }


      return new UserSettings(users,usersAccountSettings);


  }

     public void updateUserName(String userName)
    {
        Log.d(Tag,"Updating username to"+userName);
        myRef.child(context.getString(R.string.dbName_users))
                .child(userId)
                .child(context.getString(R.string.user_name_field))
                .setValue(userName);
        myRef.child(context.getString(R.string.dbName_Account_Settings))
                .child(userId)
                .child(context.getString(R.string.user_name_field))
                .setValue(userName);

    }
    public void updateEmail(String email)
    {
        Log.d(Tag,"Updating email to"+email);
        myRef.child(context.getString(R.string.dbName_users))
                .child(userId)
                .child(context.getString(R.string.user_email_field))
                .setValue(email);
    }
    public void updateUserSettings(String location,long phoneNumber)
    {
        final boolean value=false;
        Log.d(Tag,"Updating location to"+location+"Updating phone number to"+phoneNumber);
        if(phoneNumber!=0)
        {
            myRef.child(context.getString(R.string.dbName_users))
                    .child(userId)
                    .child(context.getString(R.string.phone_number))
                    .setValue(phoneNumber);
        }
        if(location!=null)
        {
            myRef.child(context.getString(R.string.dbName_Account_Settings))
                .child(userId)
                .child(context.getString(R.string.location))
                .setValue(location).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {

                    }
                }
            });

        }





    }
    public int getImageCount(DataSnapshot dataSnapshot)
    {
        int count=0;
        for(DataSnapshot ds:dataSnapshot
                           .child(context.getString(R.string.dbName_user_photo))
                           .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                           .getChildren())
        {
            count++;

        }
        return count;
    }
    public void uploadPhoto(String photoType, final String caption, int imageCount, final String imgUri)
    {
         FilePaths filePaths=new FilePaths();
        final int count=imageCount;
        final FilePaths filePaths2 = filePaths;


        Log.d(Tag,"Uploading image to database");
        if(photoType.equals(context.getString(R.string.new_photo)))
        {
            Log.d(Tag,"Uploading new photo");
            final String user_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
            StorageReference storageReference=mStorageRef
                       .child(filePaths.FIREBASE_IMAGE_STORAGE+"/"+user_id+"/photo"+(imageCount+1));

            //convert image uri to BitMap
          Bitmap bitmap=ImageManager.getBitmap(context,imgUri);

            UploadTask uploadTask=null;
           final byte[] bytes=ImageManager.getByteFromBitmap(bitmap,100);

            uploadTask=storageReference.putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                  mStorageRef.child(filePaths2.FIREBASE_IMAGE_STORAGE+"/"+user_id+"/photo"+(count+1)).getDownloadUrl().
                          addOnCompleteListener(new OnCompleteListener<Uri>() {
                              @Override
                              public void onComplete(@NonNull Task<Uri> task)
                              {
                                  Uri firebaseUri=task.getResult();
                                  Toast.makeText(context, "Photo uploaded successfuly", Toast.LENGTH_SHORT).show();
                                  uploadPhotoToDataBase(caption,firebaseUri);
                                  Intent intent=new Intent(context, MainActivity.class);
                                  context.startActivity(intent);


                              }
                          });



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {
                    Log.d(Tag,"onFailure:Photo upload");
                    Toast.makeText(context, "Failed to upload the image", Toast.LENGTH_SHORT).show();


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                {
                    double progress=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    if(progress-15>mphotoUploadProogress)
                    {
                        Toast.makeText(context,"Photo upload progress"+String.format("%.0f",progress),Toast.LENGTH_LONG).show();
                        mphotoUploadProogress=progress;

                    }
                    Log.d(Tag,"onProgress upload"+progress+"%  done");


                }
            });




        }
        else if(photoType.equals(context.getString(R.string.profile_photo)))
            {
                Log.d(Tag,"Uploading profilePhoto");
                final String user_id=FirebaseAuth.getInstance().getCurrentUser().getUid();
                StorageReference storageReference=mStorageRef
                        .child(filePaths.FIREBASE_IMAGE_STORAGE+"/"+user_id+"/profile_photo");

                //convert image uri to BitMap
                Bitmap bitmap=ImageManager.getBitmap(context,imgUri);

                UploadTask uploadTask=null;
                final byte[] bytes=ImageManager.getByteFromBitmap(bitmap,100);

                uploadTask=storageReference.putBytes(bytes);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        mStorageRef.child(filePaths2.FIREBASE_IMAGE_STORAGE+"/"+user_id+"/profile_photo").getDownloadUrl().
                                addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task)
                                    {
                                        Uri firebaseUri=task.getResult();
                                        Toast.makeText(context, "Photo uploaded successfuly", Toast.LENGTH_SHORT).show();
                                  //inserting to user account settings
                                         setProfilePhoto(firebaseUri.toString());
                                     /*    Intent intent=new Intent(context,AccountSettingActivity.class);
                                         intent.putExtra(context.getString(R.string.selectedImage),context.getString(R.string.selectedImage));
                                         context.startActivity(intent);
*/

/*
                                        ((AccountSettingActivity)context).setViewPager(
                                                ((AccountSettingActivity)context).sectionPagerAdapter.
                                                getFragmentNumber(context.getString(R.string.profile_fragments)));*/


                                    }
                                });



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.d(Tag,"onFailure:Photo upload");
                        Toast.makeText(context, "Failed to upload the image", Toast.LENGTH_SHORT).show();


                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        double progress=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        if(progress-15>mphotoUploadProogress)
                        {
                            Toast.makeText(context,"Photo upload progress"+String.format("%.0f",progress),Toast.LENGTH_LONG).show();
                            mphotoUploadProogress=progress;

                        }
                        Log.d(Tag,"onProgress upload"+progress+"%  done");


                    }
                });






            }

    }
    private void setProfilePhoto(String url)
    {
        Log.d(Tag,"Setting profilePhoto"+url);
        myRef.child(context.getString(R.string.dbName_Account_Settings))
                .child(userId)
                .child(context.getString(R.string.profile_photo))
                .setValue(url);

    }
    private void uploadPhotoToDataBase(String caption, Uri firebaseUri)
    {
        String tags=StringManipulation.getTags(caption);
        String url= String.valueOf(firebaseUri);
        Log.d(Tag,"Uploading Photo to database");
        String newPhotoKey=myRef.child(context.getString(R.string.dbName_photo)).push().getKey();
        Photo photo=new Photo();
        photo.setCaptions(caption);
        photo.setDate_created(getTimeStab());
        photo.setImage_path(url);
        photo.setTags(tags);
        photo.setUser_id(userId);
        photo.setPhoto_id(newPhotoKey);
        /*Insert into database*/
        myRef.child(context.getString(R.string.dbName_user_photo)).child(userId).child(newPhotoKey).setValue(photo);
        myRef.child(context.getString(R.string.dbName_photo)).child(newPhotoKey).setValue(photo);




    }
    private String getTimeStab()
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("Kenya/Nairobi"));
        return sdf.format(new Date());
    }
    public void initFirbase()
    {

        mAuthLitener=new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                checkifLoggedIn(user);

            }
        };


    }
    public void onChangeState()
    {
        mAuth.addAuthStateListener(mAuthLitener);
        checkifLoggedIn(mAuth.getCurrentUser());


    }
    public void checkifLoggedIn(FirebaseUser currentUser)
    {
        if(currentUser==null)
        {
            Intent login=new Intent(context, HomeActivity.class);
            login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(login);

        }


    }
    public void clearState()
    {
        if(mAuthLitener!=null)
        {
            mAuth.removeAuthStateListener(mAuthLitener);
        }

    }
    public void loggedIn(String email, String password)
    {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                user=mAuth.getCurrentUser();
                if(task.isSuccessful())
                {
                    try
                    { if(user.isEmailVerified())
                        {
                            progressDialog.dismiss();
                            Toast.makeText(context, "logged in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(context,MainActivity.class);
                            context.startActivity(intent);
                        }
                        else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(context, "Email is not verified check your mail inbox", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                            }
                    }
                    catch (NullPointerException e)
                    {
                        Log.e(Tag,"On complete null pointer exception"+e);

                    }



                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                progressDialog.dismiss();
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}


