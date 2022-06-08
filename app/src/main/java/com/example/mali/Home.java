package com.example.mali;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Home extends AppCompatActivity {

    ImageView record, access, logout;
    TextView btn_txt;
    ListView list;
    adapter adapter;
    public static ArrayList<Task>arraytask=new ArrayList<>();

    String url="https://projects-insane.000webhostapp.com/login/task_user.php";
    Task tareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        list = findViewById(R.id.listview);
        adapter = new adapter(Home.this, arraytask);
        list.setAdapter(adapter);
        btn_txt = findViewById(R.id.btn_text);
        record = findViewById(R.id.history);
        access = findViewById(R.id.admin_access);
        logout = findViewById(R.id.logout);

        btn_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = getIntent().getStringExtra("userid");
                SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("MyUser", user);
                editor.commit();
                String userid = preferences.getString("MyUser", "");
                Toast.makeText(Home.this, userid, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });


        String user = getIntent().getStringExtra("userid");
        SharedPreferences preferences = getSharedPreferences("datos",Context.MODE_PRIVATE);
        String userid = preferences.getString("MyUser", "");
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                arraytask.clear();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    String succes = jsonObject.getString("success");

                    JSONArray jsonArray=jsonObject.getJSONArray("datos");

                    if(succes.equals("1")){
                        for(int i=0;i<jsonArray.length();i++){

                            JSONObject object=jsonArray.getJSONObject(i);
                            String str_id = object.getString("id");
                            String str_title = object.getString("title");
                            String str_description = object.getString("Desciption");
                            String str_status = object.getString("Status");
                            String str_subject = object.getString("subject");
                            String str_st = object.getString("start_task");
                            String str_et = object.getString("end_task");
                            String str_responsible = object.getString("responsible");
                            String str_userid = object.getString("username");
                            String str_project = object.getString("project");

                            tareas= new Task(str_id,str_title,str_description,str_status,str_subject,str_st,str_et,str_responsible,str_userid,str_project);
                            arraytask.add(tareas);
                            adapter.notifyDataSetChanged();

                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            }, new Response.ErrorListener() {
            @Override
            //Si el proceso fue erroneo mandara un mensaje negativo
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected java.util.Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                //envia los parametros al webservice
                params.put("userid", userid);
                return params;
            }
        };
        //mensaje donde envia los parametros asociandolos con el webservice
        RequestQueue requestQueue = Volley.newRequestQueue(Home.this);
        requestQueue.add(request);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog=new ProgressDialog(view.getContext());

                CharSequence[] dialaogoItem = {"Ver datos", "IN PROGRESS","IN REVIEW", "DONE"};
                builder.setTitle(arraytask.get(position).getTitle());
                builder.setItems(dialaogoItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){

                            case 0:
                                startActivity(new Intent(Home.this,D_user.class)
                                        .putExtra("position", position));
                                break;
                            case 1:
                                P_Task(arraytask.get(position).getId());
                                break;
                            case 2:
                                R_Task(arraytask.get(position).getId());
                                break;
                            case 3:
                                D_Task(arraytask.get(position).getId());
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
    }



    private void P_Task(final String id){
        String  Prgress = "2";
        StringRequest request = new StringRequest(Request.Method.POST, "https://projects-insane.000webhostapp.com/login/update_task.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Status Update")) {
                    Toast.makeText(Home.this, "Status Update", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else {
                    Toast.makeText(Home.this, "no se pudo actualizar el status", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this,"error",Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("id",id);
                params.put("estatus",Prgress.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void R_Task(final String id){
        String  Prgress = "3";
        StringRequest request = new StringRequest(Request.Method.POST, "https://projects-insane.000webhostapp.com/login/update_task.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Status Update")) {
                    Toast.makeText(Home.this, "Status Update", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else {
                    Toast.makeText(Home.this, "no se pudo actualizar el status", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this,"error",Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("id",id);
                params.put("estatus",Prgress.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void D_Task(final String id){
        String  Prgress = "4";
        StringRequest request = new StringRequest(Request.Method.POST, "https://projects-insane.000webhostapp.com/login/update_task.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("Status Update")) {
                    Toast.makeText(Home.this, "Status Update", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Home.class));
                } else {
                    Toast.makeText(Home.this, "no se pudo actualizar el status", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this,"error",Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>params = new HashMap<>();
                params.put("id",id);
                params.put("estatus",Prgress.trim());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }




}