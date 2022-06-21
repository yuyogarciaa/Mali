package com.example.mali;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

public class Record extends AppCompatActivity {

    ImageView back;
    TextView btn_txt;
    ListView list;
    adapter adapter;
    public static ArrayList<Task> arraytask=new ArrayList<>();

    String url="https://projects-insane.000webhostapp.com/login/task_record.php";
    Task tareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        list = findViewById(R.id.listview);
        adapter = new adapter(Record.this, arraytask);
        list.setAdapter(adapter);
        btn_txt = findViewById(R.id.btn_text);
        back = findViewById(R.id.history);

        btn_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = getIntent().getStringExtra("userid");
                SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("MyUser", user);
                editor.commit();
                String userid = preferences.getString("MyUser", "");
                Toast.makeText(Record.this, userid, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), Record.class));
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
                Toast.makeText(Record.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(Record.this);
        requestQueue.add(request);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Home.class));
            }
        });

    }
}