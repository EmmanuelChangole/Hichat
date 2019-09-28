package meetup.com.hichat.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import id.zelory.compressor.Compressor;
public class ImageManager
{
    private static final String TAG="ImageManager";

    public static Bitmap getBitmap(Context context,String imgUrl)
    {
        File imageFile=new File(imgUrl);
        Bitmap bitmap=null;
        try {
                bitmap= new Compressor(context)
                        .setMaxWidth(200)
                        .setMaxHeight(200)
                        .setQuality(70)
                        .compressToBitmap(imageFile);
            } catch (IOException e) {
                e.printStackTrace();
              Log.e(TAG,"FileNotFoundException "+e.getMessage());
            }

        return bitmap;

    }



       /*Return  byte arrays from bitmap*/
    public static byte[] getByteFromBitmap(Bitmap bitmap,int quality)
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,stream);
        return stream.toByteArray();

    }
}
