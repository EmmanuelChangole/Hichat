package meetup.com.hichat.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import meetup.com.hichat.R;

public class GridImageAdapter extends ArrayAdapter<String>
{
    private Context mContext;
    private LayoutInflater inflater;
    private int layoutResources;
    private String mappend;
    private ArrayList<String> imageUrls;

    public GridImageAdapter(Context context, int layoutResources, String mappend, ArrayList<String> imgUrl)
    {
        super(context,layoutResources,imgUrl);
        inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mContext = context;
        this.layoutResources = layoutResources;
        this.mappend = mappend;
        this.imageUrls = imgUrl;
    }
    private static class ViewHolder
    {
        SquareImageView image;
        ProgressBar progressBar;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final ViewHolder viewHolder;
       /*View holder pattern*/
       if(convertView==null)
       {
           convertView=inflater.inflate(layoutResources,parent,false);
           viewHolder=new ViewHolder();
           viewHolder.progressBar=(ProgressBar)convertView.findViewById(R.id.gridImageViewProgressBar);
           viewHolder.image =(SquareImageView) convertView.findViewById(R.id.gridImageView);
           convertView.setTag(viewHolder);


       }
       else {
           viewHolder=(ViewHolder)convertView.getTag();
       }
     String imgUrl=getItem(position);
        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage(mappend + imgUrl, viewHolder.image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(viewHolder.progressBar!=null)
                { viewHolder.progressBar.setVisibility(View.VISIBLE);}
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(viewHolder.progressBar!=null)
                { viewHolder.progressBar.setVisibility(View.GONE);}
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(viewHolder.progressBar!=null)
                { viewHolder.progressBar.setVisibility(View.GONE);}
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(viewHolder.progressBar!=null)
                { viewHolder.progressBar.setVisibility(View.GONE);}
            }
        });

        return convertView;
    }
}
