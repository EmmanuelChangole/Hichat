package meetup.com.hichat.util;

public class StringManipulation {
    public static String expandUserName(String userName) {
        return userName.replace(".", " ");

    }

    public static String condenseUserName(String userName)
    {
        return userName.replace(" ",".");
    }
    public static String getTags(String tags)
    {
        if(tags.indexOf("#")>0)
        {
            StringBuilder sb=new StringBuilder();
            char[] charArry=tags.toCharArray();
            boolean foundWord=false;
            for(char c:charArry)
            {
                if(c =='#')
                {
                    foundWord=true;
                    sb.append(c);

                }
                else{
                    if(foundWord)
                    {
                        sb.append(c);
                    }
                }
                if(c==' ')
                {
                    foundWord=false;

                }
            }
            String s=sb.toString().replace(" ","").replace("#",",#");
            return s.substring(1,s.length());
        }
        return tags;
    }
}
