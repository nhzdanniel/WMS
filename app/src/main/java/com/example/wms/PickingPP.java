package com.example.wms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PickingPP extends AppCompatActivity{

    DrawerLayout drawerLayout;
    Button scanBtn;
    Button submitListBtn;
    Button addBtn;
    ListView listView;
    public static EditText resultsET;
    EditText quantityET;

    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_pp);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        resultsET = findViewById(R.id.barcodeET);
        //quantityET = findViewById(R.id.quantityET);
        submitListBtn = findViewById(R.id.submitListPP);
        addBtn = findViewById(R.id.addButton);
        scanBtn = findViewById(R.id.scanButton);
        listView = findViewById(R.id.listviewPickedList);
        resultsET = findViewById(R.id.barcodeET);

        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this,R.layout.picked_list_layout, R.id.upcCode, arrayList);
        listView.setAdapter(adapter);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //addView();
                if (resultsET.getText().toString().equals("")){
                    Toast.makeText(PickingPP.this, "Enter UPC code", Toast.LENGTH_SHORT).show();
                }
                else {
                    arrayList.add(resultsET.getText().toString());
                    adapter.notifyDataSetChanged();

                    resultsET.setText("");
                }
            }
        });

        submitListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PickingPP.this, "List Submitted", Toast.LENGTH_SHORT).show();
                arrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ScanCode.class));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int which_item = position;
                new AlertDialog.Builder(PickingPP.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Remove item?")
                        .setMessage("Are you sure you want to remove item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                arrayList.remove(which_item);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            }
        });

    }
    //drawer settings
    public void ClickMenu (View view){
        //HomePageActivityWm.openDrawer(drawerLayout);
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo (View view){
        HomePageActivityPp.closeDrawer(drawerLayout);
    }

    public void ClickHome (View view){
        HomePageActivityPp.redirectActivity(this, HomePageActivityPp.class);
    }

    public void ClickAboutUs (View view){
        HomePageActivityPp.redirectActivity(this, AboutUsActivity.class);
    }

    public void ClickLogout (View view){
        HomePageActivityPp.Logout(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        HomePageActivityPp.closeDrawer(drawerLayout);
    }
}