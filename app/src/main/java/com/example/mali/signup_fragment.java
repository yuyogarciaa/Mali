package com.example.mali;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;


public class signup_fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment,container,false);


        EditText email = root.findViewById(R.id.email);
        EditText pass = root.findViewById(R.id.pass);
        EditText name = root.findViewById(R.id.fullname);
        EditText user = root.findViewById(R.id.username);
        Button signup = root.findViewById(R.id.buttonsignup);
        float v = 0;

        email.setTranslationX(800);
        pass.setTranslationX(800);
        name.setTranslationX(800);
        user.setTranslationX(800);
        signup.setTranslationX(800);

        email.setAlpha(v);
        pass.setAlpha(v);
        name.setAlpha(v);
        user.setAlpha(v);
        signup.setAlpha(v);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        user.animate().translationX(0).alpha(1).setDuration(800).setDuration(500).start();
        signup.animate().translationX(0).alpha(1).setDuration(800).setDuration(800).start();

        return root;
    }
}