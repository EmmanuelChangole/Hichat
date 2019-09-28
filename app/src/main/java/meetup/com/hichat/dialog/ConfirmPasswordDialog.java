package meetup.com.hichat.dialog;




import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import meetup.com.hichat.R;


public class ConfirmPasswordDialog extends DialogFragment
{
    private static final String TAG="DialogFragments";
    EditText confirmPassword;
    public interface OnConfirmPassword
    {

        public void ConfrimPasword(String password);

    }
    OnConfirmPassword onConfirmPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.d(TAG,"onCreateView started");
        View view=inflater.inflate(R.layout.dialog_confirm_password,container,false);
        TextView cancel=view.findViewById(R.id.cancel);
        confirmPassword=view.findViewById(R.id.confirm_pass);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"On clicking the cancel button");
                getDialog().dismiss();
            }
        });
        TextView ok=view.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"On clicking ok button");

                String password=confirmPassword.getText().toString();
                if(!password.isEmpty())
                {
                    onConfirmPassword.ConfrimPasword(password);
                    getDialog().dismiss();
                }
                else
                    {
                        Toast.makeText(getActivity(), "You must enter the password", Toast.LENGTH_SHORT).show();
                    }




            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try
        {
            onConfirmPassword=(OnConfirmPassword)getTargetFragment();
        }
        catch (ClassCastException e)
        {
            Log.e(TAG,"ClassCastException "+e.getMessage());

        }
    }
}
