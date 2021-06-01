package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class HomePageActivityPp extends AppCompatActivity implements View.OnClickListener {

    public CardView c1, c2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_pp);

        c1 = (CardView) findViewById(R.id.viewPickingList);
        c2 = (CardView) findViewById(R.id.scanBarcodePp);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch(v.getId()){
            case R.id.viewPickingList:
                i = new Intent(this, ViewPackingListActivity.class);
                startActivity(i);
                break;

            case R.id.scanBarcodePp :
                i = new Intent(this, ScanBarcodeActivity.class);
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
                Intent intent = new Intent (HomePageActivityPp.this, LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}