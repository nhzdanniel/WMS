package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class IndividualPickingList extends AppCompatActivity {
    private static final String TAG = "individualpickinglist";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_picking_list);

        if (getIntent().hasExtra("selectedPickingList")) {
            PickingList pickingList = getIntent().getParcelableExtra("selectedPickingList");
            Log.d(TAG, "onCreate: " + pickingList.toString());
        }

    }
}