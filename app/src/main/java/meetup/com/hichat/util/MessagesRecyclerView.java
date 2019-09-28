package meetup.com.hichat.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import meetup.com.hichat.R;
import meetup.com.hichat.messages.MessagesActivity;

public class MessagesRecyclerView extends RecyclerView.Adapter<MessagesRecyclerView.ViewHolder>
{



    ArrayList<String> sender=new ArrayList<>();
    ArrayList<String> message=new ArrayList<>();
    ArrayList<String> date=new ArrayList<>();
    ArrayList<String> image=new ArrayList<>();
    Context context;

    public MessagesRecyclerView(ArrayList<String> sender, ArrayList<String> message, ArrayList<String> date, ArrayList<String> image, Context context)
    {
        this.sender = sender;
        this.message = message;
        this.date = date;
        this.image = image;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items_messages_layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        holder.messages.setText(message.get(position));
        holder.sender.setText(sender.get(position));
        holder.date.setText(date.get(position));
        Glide.with(context)
             .load(image.get(position))
             .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ImageView imageView;
                AlertDialog.Builder myDialog=new AlertDialog.Builder(context);
                LayoutInflater inflater=LayoutInflater.from(context);
                View myview=inflater.inflate(R.layout.image_view_messages_layout,null);
                imageView=myview.findViewById(R.id.message_viewImage);
                Glide.with(context)
                        .load(image.get(position))
                        .into(imageView);
                myDialog.setView(myview);
                AlertDialog dialog=myDialog.create();
                dialog.setCancelable(true);
                dialog.show();


            }
        });
        holder.messagesRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(context, MessagesActivity.class);
                intent.putExtra(context.getString(R.string.message_profile),image.get(position));
                intent.putExtra(context.getString(R.string.message_recipient),sender.get(position));
                context.startActivity(intent);





            }
        });





    }

    @Override
    public int getItemCount() {
        return sender.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView sender,messages,date;
        RelativeLayout messagesRelativeLayout;
        CircleImageView image;



        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            sender=itemView.findViewById(R.id.messageSender);
            messages=itemView.findViewById(R.id.messageContent);
            date=itemView.findViewById(R.id.messageDate);
            image=itemView.findViewById(R.id.messageImage);
            messagesRelativeLayout=itemView.findViewById(R.id.messages_relativeLayout2);
        }
    }
}
