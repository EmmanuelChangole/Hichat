package meetup.com.hichat.Share;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import meetup.com.hichat.Home.MainActivity;
import meetup.com.hichat.R;
import meetup.com.hichat.util.FireBaseMethods;
import meetup.com.hichat.util.UniversaiImageLoader;

public class NextActivity extends AppCompatActivity
{
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthLitener;
    private FirebaseDatabase myRefDatabase;
    private DatabaseReference myRef;
    private FireBaseMethods mfireBaseMethods;
  // private ImageView image;

    private final static String TAG="NextActivity";

    Context mcontext;
    // widgets
    private EditText mCaption;
    //variables
    private int imageCount=0;
    private String selectedImageUri;
    private String mAppend = "file:/";
    private String imgUri;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        mcontext=NextActivity.this;
        mfireBaseMethods=new FireBaseMethods(mcontext);

        setFireBaseAuth();
        mCaption=(EditText)findViewById(R.id.imageDescription);
        selectedImageUri=getIntent().getStringExtra(getString(R.string.selectedImage));
        Log.d(TAG,"Selected image uri="+ selectedImageUri);
        ImageView backArrow=(ImageView)findViewById(R.id.shareArrow);
       //image=(ImageView)findViewById(R.id.share);
        setImage();
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"On clicking: closing the next activity");
                finish();


            }
        });
        TextView share=(TextView)findViewById(R.id.shareImage);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Upload image
                Log.d(TAG,"On click try to upload image");
                Toast.makeText(mcontext, "Attempting to upload new image", Toast.LENGTH_SHORT).show();
                String caption=mCaption.getText().toString();
                mfireBaseMethods.uploadPhoto(getString(R.string.new_photo),caption,imageCount,imgUri);

            }
        });



    }
             /*setting image from incoming activity*/

    private void setImage()
    {
        ImageView image=(ImageView)findViewById(R.id.share);
        imgUri=getIntent().getStringExtra(getString(R.string.selectedImage));
        UniversaiImageLoader.setImage(imgUri,image,null,mAppend);
    }
    private void uploadMethod()
    {
        //create data model for photos;
        //adding properties for the photos objects(captions,date,imageuuri,photo_id,tags,user_id)
        //count the number of photos that the user has already
        //upload photo to firebase
        // insert to photo node
        //insert into user photo node



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
        myRefDatabase=FirebaseDatabase.getInstance();
        myRef=myRefDatabase.getReference();
        Log.d(TAG,"Image count="+imageCount);
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
                    Intent intent=new Intent(mcontext, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);


                }

            }
        };
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
               imageCount=mfireBaseMethods.getImageCount(dataSnapshot);
               Log.d(TAG,"Image count="+imageCount);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
