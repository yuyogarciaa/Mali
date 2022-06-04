package com.example.mali;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

public class Edit_task extends AppCompatActivity {
    TextView st,su;
    EditText name_t, descrip,start_t, end_t, responsable, userid, proyecto,tvid;
    Spinner status, subject;
    Button update_task;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        tvid  = findViewById(R.id.textID);
        name_t  = findViewById(R.id.task);
        descrip = findViewById(R.id.des);
        start_t = findViewById(R.id.start_task);
        end_t = findViewById(R.id.end_task);
        responsable = findViewById(R.id.name);
        userid = findViewById(R.id.username);
        proyecto = findViewById(R.id.proyect);
        status = findViewById(R.id.s_status);
        subject = findViewById(R.id.s_subject);
        st = findViewById(R.id.t_status);
        su = findViewById(R.id.t_subject);
        update_task = findViewById(R.id.button_ct);

        update_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updates();
                startActivity(new Intent(getApplicationContext(),Welcome.class));
            }
        });

        String [] estatus = {"1","2", "3", "4"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estatus);
        status.setAdapter(adapter1);
        String s_estatus = status.getSelectedItem().toString().trim();

        String [] asunto = {"1","2","3"};
        ArrayAdapter <String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, asunto);
        subject.setAdapter(adapter2);
        String s_subject = subject.getSelectedItem().toString().trim();

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        tvid.setText(Welcome.arraytask.get(position).getId());
        name_t.setText(Welcome.arraytask.get(position).getTitle());
        descrip.setText(Welcome.arraytask.get(position).getDesciption());
        start_t.setText(Welcome.arraytask.get(position).getStart_task());
        end_t.setText(Welcome.arraytask.get(position).getStart_task());
        responsable.setText(Welcome.arraytask.get(position).getResponsible());
        userid.setText(Welcome.arraytask.get(position).getUsername());
        proyecto.setText(Welcome.arraytask.get(position).getProject());
        st.setText(Welcome.arraytask.get(position).getStatus_id()+">>");
        su.setText(Welcome.arraytask.get(position).getSubject()+ ">>");
    }

    private void updates() {

        String str_id = tvid.getText().toString().trim();
        String str_task = name_t.getText().toString().trim();
        String str_description = descrip.getText().toString().trim();
        String str_st = start_t.getText().toString().trim();
        String str_et = end_t.getText().toString().trim();
        String str_empleado = responsable.getText().toString().trim();
        String str_userid = userid.getText().toString().trim();
        String str_project = proyecto.getText().toString().trim();
        String s_estatus = status.getSelectedItem().toString().trim();
        String s_asunto = subject.getSelectedItem().toString().trim();
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
        }else if(s_estatus.isEmpty()){
            proyecto.setError("Seleccione un campo");
        }else if(s_asunto.isEmpty()){
            proyecto.setError("Seleccione el campo");
        }else{
            progressDialog.show();
            String finalS_estatus = s_estatus;
            String finalS_asunto = s_asunto;
            StringRequest request = new StringRequest(Request.Method.POST, "https://projects-insane.000webhostapp.com/login/update.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equalsIgnoreCase("Update task")) {
                        Toast.makeText(Edit_task.this, "task created", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();

                    } else {
                        Toast.makeText(Edit_task.this, response, Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Edit_task.this,error.getMessage(),Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String>params=new HashMap<String, String>();

                    params.put("id", str_id);
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
            RequestQueue requestQueue = Volley.newRequestQueue(Edit_task.this);
            requestQueue.add(request);
        }
    }
}