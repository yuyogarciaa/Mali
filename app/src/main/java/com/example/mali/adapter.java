package com.example.mali;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class adapter extends ArrayAdapter<Task> {


    Context context;
    List<Task>arrayalisttask;


    public adapter(@NonNull Context context, List<Task>arrayalisttask) {
        super(context, R.layout.my_list_item, arrayalisttask);

        this.context=context;
        this.arrayalisttask=arrayalisttask;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_list_item, null, true);

        TextView txtID = view.findViewById(R.id.identificador);
        TextView txtTask = view.findViewById(R.id.task);
        TextView txtS_task = view.findViewById(R.id.tv_st);
        TextView txtE_task = view.findViewById(R.id.tv_et);
        TextView txt_nombre = view.findViewById(R.id.tv_nombre);

        txtID.setText(arrayalisttask.get(position).getId());
        txtTask.setText(" "+arrayalisttask.get(position).getTitle());
        txtS_task.setText(arrayalisttask.get(position).getStart_task());
        txtE_task.setText(arrayalisttask.get(position).getEnd_task());
        txt_nombre.setText("Responsible: "+arrayalisttask.get(position).getResponsible());

        return view;
    }
}
