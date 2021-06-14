package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PickingPP extends AppCompatActivity{

    ConstraintLayout layoutList;
    Button scanBtn;
    Button submitListBtn;
    Button addBtn;
    public static EditText resultsET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_pp);

        layoutList = findViewById(R.id.layout_list);
        submitListBtn = findViewById(R.id.submitListPP);
        addBtn = findViewById(R.id.addButton);

        resultsET = findViewById(R.id.barcodeET);
        scanBtn = findViewById(R.id.scanButton);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addView();

            }
        });

        submitListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCode.class));
            }
        });


    }
}