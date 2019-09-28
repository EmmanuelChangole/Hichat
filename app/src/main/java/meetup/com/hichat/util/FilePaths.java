package meetup.com.hichat.util;

import android.os.Environment;

public class FilePaths
{
    //Storage emulated/0
    public String ROOT_DIR= Environment.getExternalStorageDirectory().getPath();
    public String CAMERA = ROOT_DIR+ "/DCIM/camera";
    public String PICTURES = ROOT_DIR+ "/pictures";
    public String FIREBASE_IMAGE_STORAGE="photos/users/";



}
