package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class HomePageActivityRec extends AppCompatActivity implements View.OnClickListener{

    public CardView c1, c2, c3, c4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_rec);

        c1 = (CardView) findViewById(R.id.productMngRec);
        c2 = (CardView) findViewById(R.id.printInformationLabel);
        c3 = (CardView) findViewById(R.id.scanBarcodeRec);
        c4 = (CardView) findViewById(R.id.viewPoRec);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch(v.getId()) {
            case R.id.productMngRec:
                i = new Intent(this, ProductMngRecActivity.class);
                startActivity(i);
                break;

            case R.id.printInformationLabel:
                i = new Intent(this, PrintInformationLabelActivity.class);
                startActivity(i);
                break;

            case R.id.scanBarcodeRec:
                i = new Intent(this, ScanBarcodeActivity.class);
                startActivity(i);
                break;

            case R.id.viewPoRec:
                i = new Intent(this, ViewPOActivity.class);
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
                Intent intent = new Intent (HomePageActivityRec.this, LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}