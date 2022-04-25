package com.example.mali;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class forget_pass extends AppCompatActivity {

    String str_email, str_num;
    //url para consumir el webservice
    String url="https://projects-insane.000webhostapp.com/login/forgotPass.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        //llamado de los componentes a utilizar
        EditText email = (EditText)findViewById(R.id.emailf);
        Button forget = (Button) findViewById(R.id.btnReset);

        //Accion del boton
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validacion de que se encuentre el correo electronico
                if (email.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "enter your email",Toast.LENGTH_SHORT).show();
                }
                else {

                    //generara un numero de 6 digitos
                    str_email = email.getText().toString().trim();
                    int i = new Random().nextInt(999999) + 100000;
                    //parseo a string
                    str_num = Integer.toString(i).trim();

                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //si el proceso se hace correctamente se limpiara el campo
                            email.setText("");
                            Toast.makeText(getApplicationContext(), "the mail has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    ) {
                        @Override
                        protected java.util.Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("email", str_email);
                            params.put("num", str_num);
                            return params;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(request);
                }
            }
        });
    }
}