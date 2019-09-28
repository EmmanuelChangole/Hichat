package meetup.com.hichat.messages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import meetup.com.hichat.R;
import meetup.com.hichat.util.MessagesRecyclerView;

public class MessaagesFragment extends Fragment
{
    private static final String TAG="Main activity fragments";
    ArrayList<String> sender=new ArrayList<>();
    ArrayList<String> messages=new ArrayList<>();
    ArrayList<String>date=new ArrayList<>();
    ArrayList<String>imgUri=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_messages,container,false);
        initRecyclerContent();
        initRecycler(view);

       return view;
    }

    private void initRecyclerContent()
    {
        sender.add("070273189");
        messages.add("See you soon");
        date.add("10:24");
        imgUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVwSIQ6AQKK4TMjn4IFq0P2OEfV3or0upF76aj_xnvuyT-kET7");

        sender.add("070273159");
        messages.add("Hello");
        date.add("Today");
        imgUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPx2QsaPRAmS-J-pK47WLG59SR_jJGymIpSWgYWp1IAjg_lWuJgA");

        sender.add("07023239");
        messages.add("GoodNight");
        date.add("Yesterday");
        imgUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDlAHTup6cMtOYSs0ozJ3U-DZ_5VzPsqQ5YPWZERnwA6b9qvDJ4g");

        sender.add("073073189");
        messages.add("See you soon");
        date.add("10:24");
        imgUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVwSIQ6AQKK4TMjn4IFq0P2OEfV3or0upF76aj_xnvuyT-kET7");

        sender.add("072073189");
        messages.add("Hello");
        date.add("Today");
        imgUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPx2QsaPRAmS-J-pK47WLG59SR_jJGymIpSWgYWp1IAjg_lWuJgA");

        sender.add("07233189");
        messages.add("GoodNight");
        date.add("Yesterday");
        imgUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDlAHTup6cMtOYSs0ozJ3U-DZ_5VzPsqQ5YPWZERnwA6b9qvDJ4g");
        sender.add("070273189");
        messages.add("See you soon");
        date.add("10:24");
        imgUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSVwSIQ6AQKK4TMjn4IFq0P2OEfV3or0upF76aj_xnvuyT-kET7");

        sender.add("0702073180");
        messages.add("Hello");
        date.add("Today");
        imgUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQPx2QsaPRAmS-J-pK47WLG59SR_jJGymIpSWgYWp1IAjg_lWuJgA");

        sender.add("0734423189");
        messages.add("GoodNight");
        date.add("Yesterday");
        imgUri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQDlAHTup6cMtOYSs0ozJ3U-DZ_5VzPsqQ5YPWZERnwA6b9qvDJ4g");
    }
    public void initRecycler(View view)
    {
        RecyclerView recyclerView=view.findViewById(R.id.messagesRecyclerView);
        MessagesRecyclerView messagesRecyclerView=new MessagesRecyclerView(sender,messages,date,imgUri,getActivity());
        recyclerView.setAdapter(messagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

}
