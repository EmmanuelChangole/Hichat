package meetup.com.hichat.Home;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import meetup.com.hichat.R;
import meetup.com.hichat.util.FireBaseMethods;

public class LoginFragments extends Fragment
{
    /*Widgets */
    private View view;
    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginBut;

    private FireBaseMethods fireBaseMethods;
        @Nullable
        @Override
   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragments_login,container,false);
       initWidgets(view);
       loginBut.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               userCheck();
           }
       });
       return view;
   }
            /*Validate user inputs*/
    private void userCheck() {
        String email = loginEmail.getText().toString().trim();
        String password = loginPassword.getText().toString();
        if (check(email)) {
            loginEmail.setError(getActivity().getString(R.string.error_email));
            loginEmail.requestFocus();
            return;
        }
        if (!validEmail(email))
        {
            loginEmail.setError("Enter a valid email");
            loginEmail.requestFocus();
            return;
        }
        if (check(password)) {
            loginPassword.setError(getActivity().getString(R.string.error_password));
            loginPassword.requestFocus();
            return;
        }
        if (checkLength(password)) {
            loginPassword.setError(getActivity().getString(R.string.error_password_length));
            loginPassword.requestFocus();
            return;

        }

        login(email,password);

   }
          /*Check if valid email is provided*/
    private boolean validEmail(String email)
    {
        if(email.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return false;
        return true;
    }
            /*Call login method from  fireBase Methods*/
    private void login(String email, String password)
    {
     fireBaseMethods=new FireBaseMethods(getActivity());
     fireBaseMethods.loggedIn(email,password);



    }

             /*Check password length*/
    private boolean checkLength(String password)
    {
        if(password.length()<6)
            return true;
        return  false;
    }

              /*Method that check if input is empty*/
    private boolean check(String string)
    {
        if(string.isEmpty())
            return true;

        return false;
    }
              /*Initialize the widgets`*/
    private void initWidgets(View view)
    {
        loginEmail=view.findViewById(R.id.inputEmail);
        loginPassword=view.findViewById(R.id.inputPassword);
        loginBut=view.findViewById(R.id.butlogin);

    }


}





