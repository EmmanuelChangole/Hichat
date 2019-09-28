package meetup.com.hichat.messages;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import meetup.com.hichat.R;

public class MessagesActivity extends AppCompatActivity
{
    TextView tvSender;
    CircleImageView imgProfile;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        initWidgets();
        getIncomingIntent();
    }



    private void initWidgets()
    {
        tvSender=(TextView)findViewById(R.id.message_sender);
        imgProfile=(CircleImageView)findViewById(R.id.message_profile);


    }

    private void getIncomingIntent()
    {
       String imageUri=getIntent().getStringExtra(getString(R.string.message_profile));
       String recipent=getIntent().getStringExtra(getString(R.string.message_recipient));
       tvSender.setText(recipent);
        Glide.with(this)
              .load(imageUri)
              .into(imgProfile);



    }


}
