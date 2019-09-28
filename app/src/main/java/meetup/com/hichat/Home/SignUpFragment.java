package meetup.com.hichat.Home;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;

import meetup.com.hichat.R;
import meetup.com.hichat.util.FireBaseMethods;

public class SignUpFragment extends Fragment
{
    private View view;
    private EditText signUpEmail;
    private EditText signUpPassword;
    private EditText signUpConfirmPassword;
    private EditText signUpUserName;
    private Button butSignup;

    FireBaseMethods fireBaseMethods;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
         view=inflater.inflate(R.layout.fragment_sign_up,container,false);
         initWediget(view);
         butSignup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 checkUser();
             }
         });
        return view;
    }

    private void checkUser()
    {
       String email=signUpEmail.getText().toString().trim();
       String password=signUpPassword.getText().toString().trim();
       String confirmPassword=signUpConfirmPassword.getText().toString().trim();
       String userName=signUpUserName.getText().toString().trim();

        if (check(email))
        {
            signUpEmail.setError(getActivity().getString(R.string.error_email));
            signUpEmail.requestFocus();
            return;
        }
        if (!validEmail(email))
        {
            signUpEmail.setError("Enter a valid email");
            signUpEmail.requestFocus();
            return;
        }
        if(check(userName))
        {
            signUpUserName.setError(getActivity().getString(R.string.error_username));
            signUpUserName.requestFocus();
            return;

        }

        if(userNameLength(userName))
        {
            signUpUserName.setError(getActivity().getString(R.string.error_username_length));
            signUpUserName.requestFocus();
            return;

        }
        if (check(password)) {
            signUpPassword.setError(getActivity().getString(R.string.error_password));
            signUpPassword.requestFocus();
            return;
        }
        if (checkLength(password)) {
            signUpPassword.setError(getActivity().getString(R.string.error_password_length));
            signUpPassword.requestFocus();
            return;

        }
        if(confirmPass(password,confirmPassword))
        {
            signUpConfirmPassword.setError(getActivity().getString(R.string.error_confirm_password));
            signUpConfirmPassword.requestFocus();
            return;
        }

        createAccount(email,password,userName);








    }

    private void createAccount(String email, String password, String userName)
    {
        fireBaseMethods=new FireBaseMethods(getActivity());
        fireBaseMethods.createNewAccount(email,password,userName);


    }

    private boolean confirmPass(String password, String confirmPassword)
    {
        if(!password.equals(confirmPassword))
            return true;

            return false;
    }
    private boolean checkLength(String password)
    {
        if(password.length()<6)
            return true;
        return  false;
    }
    private boolean userNameLength(String userName)
    {
        if(userName.length()<4)
        return true;
        return false;
    }
    private boolean check(String string)
    {
        if(string.isEmpty())
            return true;

        return false;

    }
    private boolean validEmail(String email)
    {
        if(email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false;
        return true;
    }
    private void initWediget(View view)
    {
        signUpEmail=view.findViewById(R.id.inputSignUpEmail);
        signUpPassword=view.findViewById(R.id.inputSignUpPassword);
        signUpConfirmPassword=view.findViewById(R.id.inputSignUpConfrimPassword);
        signUpUserName=view.findViewById(R.id.inputSignUpUserName);
        butSignup=view.findViewById(R.id.butSignup);

    }


}
