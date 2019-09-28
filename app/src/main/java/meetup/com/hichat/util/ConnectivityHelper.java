package meetup.com.hichat.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityHelper {
    public static boolean isConnectedToNetwork(Context context)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isconnected=false;
        if(connectivityManager!=null)
        {
            NetworkInfo activeNetwork=connectivityManager.getActiveNetworkInfo();
            isconnected=(activeNetwork!=null)&&(activeNetwork.isConnectedOrConnecting());

        }
        return isconnected;

    }
}
