package meetup.com.hichat.Profile;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import meetup.com.hichat.R;

public class SignOutFragment extends Fragment
{
    private static final String TAG="Sign out fragment";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthstateListener;
    private ProgressDialog progressDialog;
    private TextView signOut;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_sign_out,container,false);
        return view;
}
}
