package com.example.mali;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class New_Task extends AppCompatActivity {

    EditText name_t, descrip,start_t, end_t, responsable, userid, proyecto;
    Spinner status, subject;
    Button create_task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        name_t  = findViewById(R.id.task);
        descrip = findViewById(R.id.des);
        start_t = findViewById(R.id.start_task);
        end_t = findViewById(R.id.end_task);
        responsable = findViewById(R.id.name);
        userid = findViewById(R.id.username);
        proyecto = findViewById(R.id.proyect);
        status = findViewById(R.id.s_status);
        subject = findViewById(R.id.s_subject);
        create_task = findViewById(R.id.button_ct);

        String [] estatus = {"TO DO","IN PROGRESS", "IN REVIEW", "DONE"};
        ArrayAdapter <String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estatus);
        status.setAdapter(adapter1);


        String [] asunto = {"RELEASE","FIX","BUG"};
        ArrayAdapter <String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, asunto);
        subject.setAdapter(adapter2);

        //long ahora = System.currentTimeMillis();
        //Date fecha = new Date(ahora);

        //DateFormat df = new SimpleDateFormat("dd/MM/yy");
        //String salida = df.format(fecha).trim();

        create_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertar();

            }
        });
    }

    private void insertar() {

        String str_task = name_t.getText().toString().trim();
        String str_description = descrip.getText().toString().trim();
        String str_st = start_t.getText().toString().trim();
        String str_et = end_t.getText().toString().trim();
        String str_empleado = responsable.getText().toString().trim();
        String str_userid = userid.getText().toString().trim();
        String str_project = proyecto.getText().toString().trim();
        String s_estatus = status.getSelectedItem().toString().trim();
        String s_asunto = subject.getSelectedItem().toString();
        if(s_estatus.equals("TO DO")){   s_estatus= "1".trim(); }
        else if(s_estatus.equals("IN PROGRESS")){  s_estatus= "2".trim(); }
        else if(s_estatus.equals("IN REVIEW")){  s_estatus= "3".trim(); }
        else if(s_estatus.equals("DONE")){  s_estatus= "4".trim(); }
        else{ Toast.makeText(this,"seleccionar un campo",Toast.LENGTH_LONG).show(); }

        if(s_asunto.equals("RELEASE")){  s_asunto= "1".trim(); }
        else if(s_asunto.equals("FIX")){  s_asunto= "2".trim(); }
        else if(s_asunto.equals("BUG")){  s_asunto= "3".trim(); }
        else{ Toast.makeText(this,"seleccionar un campo",Toast.LENGTH_LONG).show(); }


        ProgressDialog progressDialog=new ProgressDialog(this);
        if(str_task.isEmpty()){
            name_t.setError("Complete el campo");
        }else if(str_description.isEmpty()){
            descrip.setError("Complete el campo");
        }else if(str_st.isEmpty()){
            start_t.setError("Complete el campo");
        }else if(str_et.isEmpty()){
            end_t.setError("Complete el campo");
        }else if(str_empleado.isEmpty()){
            responsable.setError("Complete el campo");
        }else if(str_userid.isEmpty()){
            userid.setError("Complete el campo");
        }else if(str_project.isEmpty()){
            proyecto.setError("Complete el campo");
        }else{
            progressDialog.show();
            String finalS_estatus = s_estatus;
            String finalS_asunto = s_asunto;
            StringRequest request = new StringRequest(Request.Method.POST, "https://projects-insane.000webhostapp.com/login/create_task.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("assigned task")) {
                        Toast.makeText(New_Task.this, "task created", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                        startActivity(new Intent(getApplicationContext(), Welcome.class));
                        finish();

                    } else {
                        Toast.makeText(New_Task.this, response, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(New_Task.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String>params=new HashMap<String, String>();

                    params.put("title", str_task);
                    params.put("Desciption", str_description);
                    params.put("status_id", finalS_estatus);
                    params.put("subject", finalS_asunto);
                    params.put("start_task", str_st);
                    params.put("end_task", str_et);
                    params.put("responsible", str_empleado);
                    params.put("username", str_userid);
                    params.put("project", str_project);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(New_Task.this);
            requestQueue.add(request);
        }
    }
}