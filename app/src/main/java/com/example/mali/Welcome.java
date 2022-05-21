package com.example.mali;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class Welcome extends AppCompatActivity {

    ListView list;
    adapter adapter;
    public static ArrayList<Task>arraytask=new ArrayList<>();

    String url="https://projects-insane.000webhostapp.com/login/task_view.php";
    Task tareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        list = findViewById(R.id.listview);
        adapter = new adapter(Welcome.this, arraytask);
        list.setAdapter(adapter);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog=new ProgressDialog(view.getContext());

                CharSequence[] dialaogoItem = {"Ver datos", "Editar datos","Eliminar datos"};
                builder.setTitle(arraytask.get(position).getTitle());
                builder.setItems(dialaogoItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){

                            case 0:
                                startActivity(new Intent(getApplicationContext(),Detalles.class)
                                .putExtra("position", position));
                                break;
                            case 1:
                                startActivity(new Intent(getApplicationContext(),Edit_task.class)
                                        .putExtra("position", position));
                                break;
                            case 2:
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        mostrardatos();
    }

    private void mostrardatos() {

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
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Welcome.this, error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);



    }

    public  void btnAdd(View view){
        startActivity(new Intent(getApplicationContext(), create_task.class));
    }


}