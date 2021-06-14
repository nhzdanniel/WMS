package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class PickedListPP extends AppCompatActivity {

    RecyclerView recyclerPickedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picked_list_pp);

        recyclerPickedList = findViewById(R.id.recyclerPickedList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerPickedList.setLayoutManager(linearLayoutManager);
    }
}