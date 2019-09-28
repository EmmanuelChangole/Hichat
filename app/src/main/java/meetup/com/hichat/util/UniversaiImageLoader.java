package meetup.com.hichat.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import meetup.com.hichat.R;

public class UniversaiImageLoader
{
    private static final int defaultImage= R.drawable.profile;
    private Context context;

    public UniversaiImageLoader(Context context) {
        this.context = context;
    }
    public ImageLoaderConfiguration getConfig()
    {
        DisplayImageOptions defaultOptions=new DisplayImageOptions.Builder()
                .showImageOnLoading(defaultImage)
                .showImageForEmptyUri(defaultImage)
                .showImageOnFail(defaultImage)
                .cacheOnDisk(true).cacheInMemory(true)
                .cacheOnDisk(true).resetViewBeforeLoading(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(100*1024*1024)
                 .build();
        return config;

    }
    /*This method is used  to set image   that  are  static */
    /*Are being  changed in fragment activity or if  they are being set  on list*/
    public static void setImage(String url, ImageView image, final ProgressBar progressBar,String append)
    {
        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage(append + url, image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if(progressBar!=null)
                { progressBar.setVisibility(View.INVISIBLE);}
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(progressBar!=null)
                { progressBar.setVisibility(View.GONE);}
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(progressBar!=null)
                { progressBar.setVisibility(View.GONE);}
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(progressBar!=null)
                { progressBar.setVisibility(View.GONE);}

            }
        });


    }
}
