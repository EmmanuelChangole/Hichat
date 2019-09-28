package meetup.com.hichat.Share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import meetup.com.hichat.Profile.AccountSettingActivity;
import meetup.com.hichat.R;
import meetup.com.hichat.util.FilePaths;
import meetup.com.hichat.util.FileSearch;
import meetup.com.hichat.util.GridImageAdapter;

public class GalleryFragment extends Fragment {
    private static final String TAG = "GalleryFragment";

    private static final int NUM_GRID_COLUMNS = 3;
    //Widgets
    private GridView gridView;
    private ImageView galleryImageView;
    private ProgressBar progressBar;
    private Spinner spinner;
    //var
    private ArrayList<String> directories;
    private String mSelectedImage;
    private String Mappend = "file:/";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        gridView = view.findViewById(R.id.gallerygridView);
        galleryImageView = view.findViewById(R.id.galleryImageView);
        progressBar = view.findViewById(R.id.galleryProgrssBar);
        progressBar.setVisibility(view.GONE);
        spinner = view.findViewById(R.id.spinner);
        ImageView closeShare = view.findViewById(R.id.closedShare);
        directories = new ArrayList<>();
        closeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "On closed started");
                getActivity().finish();
            }
        });
        TextView nextItem = view.findViewById(R.id.nextItem);
        nextItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRootTask())
                {
                    Log.d(TAG, "Navigating to the final share screen");
                    Intent intent=new Intent(getActivity(),NextActivity.class);
                    intent.putExtra(getString(R.string.selectedImage),mSelectedImage);
                    startActivity(intent);
                }
                else
                    {
                        Log.d(TAG, "Navigating to account settings screen");
                        Intent intent=new Intent(getActivity(), AccountSettingActivity.class);
                        intent.putExtra(getString(R.string.selectedImage),mSelectedImage);
                        intent.putExtra(getString(R.string.return_to_fragment),getString(R.string.edit_profile));
                        startActivity(intent);
                    }



            }
        });
        init();
        return view;
    }
    private boolean isRootTask()
    {
        if(((ShareActivity)getActivity()).getTask()==0)
        {
            return true;
        }
        else
            return false;

    }

    private void init() {
        FilePaths filePaths = new FilePaths();
        if (FileSearch.getDirectoryPaths(filePaths.PICTURES) != null) {
            directories = FileSearch.getDirectoryPaths(filePaths.PICTURES);
        }
        directories.add(filePaths.CAMERA);
        ArrayList<String> directoryName = new ArrayList<>();
        for (int i = 0; i < directories.size(); i++) {
            int index = directories.get(i).lastIndexOf("/");
            String string = directories.get(i).substring(index).replace("/", " ");
            directoryName.add(string);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, directoryName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.d(TAG, "On item selected" + directories.get(position));
                setUpGridView(directories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void setUpGridView(String selectedDirectory) {
        Log.d(TAG, "Directory chosen");
        //setting gridColumns width
        final ArrayList<String> imageUrls = FileSearch.getFilePaths(selectedDirectory);
        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imgWidth = gridWidth / NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imgWidth);
        //use the grid adapter to adapter the images to grid view
        GridImageAdapter gridImageAdapter = new GridImageAdapter(getActivity(), R.layout.grid_image_view, Mappend, imageUrls);
        gridView.setAdapter(gridImageAdapter);
        //Setting images to display in when activity fragment view is inflated
        setImage(imageUrls.get(0),galleryImageView,Mappend);
        mSelectedImage=imageUrls.get(0);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
            {
                Log.d(TAG,"on item clicked: selected  image"+imageUrls.get(position));
                setImage(imageUrls.get(position),galleryImageView,Mappend);
                mSelectedImage=imageUrls.get(position);

            }
        });



    }
    private void setImage(String ImgUrl,ImageView image,String append)
    {

        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage(append + ImgUrl, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage)
            {
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view)
            {
                progressBar.setVisibility(View.INVISIBLE);

            }
        });
    }

}
