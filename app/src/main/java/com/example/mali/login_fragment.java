package com.example.mali;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;

public class login_fragment extends Fragment {

    int chace = 2;
    String str_userid,str_password;
    String url="https://projects-insane.000webhostapp.com/login/validar.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment,container,false);

        EditText userid = root.findViewById(R.id.email);
        EditText pass = root.findViewById(R.id.pass);
        TextView forget = root.findViewById(R.id.textviewforget);
        Button login = root.findViewById(R.id.btnlogin);

        float v = 0;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userid.getText().toString().equals("")){
                    Toast.makeText(getContext().getApplicationContext(), "enter your email",Toast.LENGTH_SHORT).show();
                }
                else if (pass.getText().toString().equals("")){
                    Toast.makeText(getContext().getApplicationContext(), "enter your password",Toast.LENGTH_SHORT).show();
                }else{
                    str_userid = userid.getText().toString().trim();
                    str_password = pass.getText().toString().trim();

                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("you entered correctly")) {
                                Intent i = new Intent(getContext().getApplicationContext(),Home.class);
                                i.putExtra("userid", userid.getText().toString());
                                startActivity(i);

                                chace=2;
                                Toast.makeText(getContext().getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }else if(chace==0){
                                Toast.makeText(getContext().getApplicationContext(),"Reset Password",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), forget_pass.class);
                                startActivity(intent);
                                login.setEnabled(false);
                            }else if(response.equalsIgnoreCase("wrong password or email")){
                                Toast.makeText(getContext().getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                chace=chace-1;
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Toast.makeText(getContext().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    ) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("password", str_password);
                            params.put("userid", str_userid);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getContext().getApplicationContext());
                    requestQueue.add(request);
                }
            }
        });

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext().getApplicationContext(),forget_pass.class));
            }
        });


        userid.setTranslationX(800);
        pass.setTranslationX(800);
        forget.setTranslationX(800);
        login.setTranslationX(800);

        userid.setAlpha(v);
        pass.setAlpha(v);
        forget.setAlpha(v);
        login.setAlpha(v);

        userid.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        pass.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        forget.animate().translationX(0).alpha(1).setDuration(800).setDuration(500).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setDuration(800).start();
        return root;
    }
}
