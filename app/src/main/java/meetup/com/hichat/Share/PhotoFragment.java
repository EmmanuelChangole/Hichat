package meetup.com.hichat.Share;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import meetup.com.hichat.R;
import meetup.com.hichat.util.Permissions;

public class PhotoFragment extends Fragment
{
    private static final String TAG="PhotoFragment";
    private static final int PHOTO_FRAGMENT_NUMBER=1;
    private static final int GALLERY_FRAGMENT_NUMBER=0;
    private static final int CAMERA_REQUEST_CODE = 12;


    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_photo,container,false);
        Log.d(TAG,"On Create started");
        Log.d(TAG,"on click launch camera button");




        Button button=view.findViewById(R.id.launchCamera);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"on click launch camera button");
                if(((ShareActivity)getActivity()).getCurrentTabNumber()==PHOTO_FRAGMENT_NUMBER)
                {
                    if(((ShareActivity)getActivity()).checkPermission(Permissions.CAMERA_PERMISIONS[0]))
                    {
                        Log.d(TAG,"Starting camera");
                        Intent cameraIntenet=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntenet,CAMERA_REQUEST_CODE);

                    }
                    else {
                        Intent intent=new Intent(getActivity(),ShareActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                }

            }
        });

       return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE)
        {
            Log.d(TAG,"Done taking the photo");
            Log.d(TAG,"Attempting to navigating to final screen");
            //Navigating to final share screen


        }

    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
