package com.example.mali;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Detalles extends AppCompatActivity {
    TextView id, name_t, descrip,start_t, end_t, responsable, userid, proyecto,status, subject;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

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

        id.setText(Welcome.arraytask.get(position).getId());
        name_t.setText(Welcome.arraytask.get(position).getTitle());
        descrip.setText(Welcome.arraytask.get(position).getDesciption());
        start_t.setText("start_task: "+Welcome.arraytask.get(position).getStart_task());
        end_t.setText("end_task: "+Welcome.arraytask.get(position).getStart_task());
        responsable.setText(Welcome.arraytask.get(position).getResponsible());
        userid.setText(Welcome.arraytask.get(position).getUsername());
        proyecto.setText("project: "+Welcome.arraytask.get(position).getProject());
        status.setText("Status: "+Welcome.arraytask.get(position).getStatus_id());
        subject.setText("subject: "+Welcome.arraytask.get(position).getSubject());


    }
}