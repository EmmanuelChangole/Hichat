package meetup.com.hichat.util;

import java.io.File;
import java.util.ArrayList;

public class FileSearch
{

    /*Search for directories and return list of directories*/
    public static ArrayList<String> getDirectoryPaths(String directory)
    {
        ArrayList<String> pathArray=new ArrayList<>();
        File file=new File(directory);
        File[] listFils=file.listFiles();
        for(int i=0;i<listFils.length;i++)
        {
            if(listFils[i].isDirectory())
            {
                pathArray.add(listFils[i].getAbsolutePath());
            }
        }
        return pathArray;
    }

                  /*Search for file and return list of files*/
    public static ArrayList<String> getFilePaths(String directory)
    {
        ArrayList<String> pathArray=new ArrayList<>();
        File file=new File(directory);
        File[] listFils=file.listFiles();
        for(int i=0;i<listFils.length;i++)
        {
            if(listFils[i].isFile())
            {
                pathArray.add(listFils[i].getAbsolutePath());
            }
        }
        return pathArray;


    }
}
