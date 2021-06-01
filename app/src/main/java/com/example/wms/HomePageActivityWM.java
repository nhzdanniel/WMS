package com.example.wms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class HomePageActivityWM extends AppCompatActivity implements View.OnClickListener {

    public CardView c1, c2, c3, c4, c5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_wm);

        c1 = (CardView) findViewById(R.id.productMngWM);
        c2 = (CardView) findViewById(R.id.viewInvRpts);
        c3 = (CardView) findViewById(R.id.mngAcc);
        c4 = (CardView) findViewById(R.id.viewPoWM);
        c5 = (CardView) findViewById(R.id.genIncPO);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch(v.getId()){
            case R.id.productMngWM:
                i = new Intent(this, ProductMngActivity.class);
                startActivity(i);
                break;

            case R.id.viewInvRpts :
                i = new Intent(this, ViewInventoryReportsActivity.class);
                startActivity(i);
                break;

            case R.id.mngAcc :
                i = new Intent(this, ManageAccountsActivity.class);
                startActivity(i);
                break;

            case R.id.viewPoWM:
                i = new Intent(this, ViewPOActivity.class);
                startActivity(i);
                break;

            case R.id.genIncPO :
                i = new Intent(this, GenerateIncomingPoActivity.class);
                startActivity(i);
                break;
        }
    }

    //logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Intent intent = new Intent (HomePageActivityWM.this, LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}