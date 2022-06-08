package com.example.mali;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class D_user extends AppCompatActivity {

    TextView id, name_t, descrip,start_t, end_t, responsable, userid, proyecto,status, subject;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duser);
        
        id = findViewById(R.id.txtId);
        name_t  = findViewById(R.id.txtTitle);
        descrip = findViewById(R.id.txtDescription);
        start_t = findViewById(R.id.tv_Start_task);
        end_t = findViewById(R.id.tv_End_task);
        responsable = findViewById(R.id.tv_name);
        userid = findViewById(R.id.tv_UID);
        proyecto = findViewById(R.id.txtP);
        status = findViewById(R.id.tv_status);
        subject = findViewById(R.id.tv_Subject);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        id.setText(Home.arraytask.get(position).getId());
        name_t.setText(Home.arraytask.get(position).getTitle());
        descrip.setText(Home.arraytask.get(position).getDesciption());
        start_t.setText("start_task: "+Home.arraytask.get(position).getStart_task());
        end_t.setText("end_task: "+Home.arraytask.get(position).getStart_task());
        responsable.setText(Home.arraytask.get(position).getResponsible());
        userid.setText(Home.arraytask.get(position).getUsername());
        proyecto.setText("project: "+Home.arraytask.get(position).getProject());
        status.setText("Status: "+Home.arraytask.get(position).getStatus_id());
        subject.setText("subject: "+Home.arraytask.get(position).getSubject());


    }
}