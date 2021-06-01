package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class HomePageActivitySup extends AppCompatActivity implements View.OnClickListener{

    public CardView c1, c2, c3, c4, c5, c6, c7, c8, c9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_sup);

        c1 = (CardView) findViewById(R.id.trackShipmentStat);
        c2 = (CardView) findViewById(R.id.viewOrders);
        c3 = (CardView) findViewById(R.id.productMngSup);
        c4 = (CardView) findViewById(R.id.genDo);
        c5 = (CardView) findViewById(R.id.genPickingList);
        c6 = (CardView) findViewById(R.id.viewPoSup);
        c7 = (CardView) findViewById(R.id.editIncomingPo);
        c8 = (CardView) findViewById(R.id.storageMng);
        c9 = (CardView) findViewById(R.id.printDo);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);
        c6.setOnClickListener(this);
        c7.setOnClickListener(this);
        c8.setOnClickListener(this);
        c9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch(v.getId()) {
            case R.id.trackShipmentStat:
                i = new Intent(this, TrackShipmentStatActivity.class);
                startActivity(i);
                break;

            case R.id.viewOrders:
                i = new Intent(this, ViewOrdersActivity.class);
                startActivity(i);
                break;

            case R.id.productMngSup:
                i = new Intent(this, ProductMngActivity.class);
                startActivity(i);
                break;

            case R.id.genDo:
                i = new Intent(this, GenerateDoActivity.class);
                startActivity(i);
                break;

            case R.id.genPickingList:
                i = new Intent(this, GeneratePickingListActivity.class);
                startActivity(i);
                break;

            case R.id.viewPoSup:
                i = new Intent(this, ViewPOActivity.class);
                startActivity(i);
                break;

            case R.id.editIncomingPo:
                i = new Intent(this, EditIncomingPoActivity.class);
                startActivity(i);
                break;

            case R.id.storageMng:
                i = new Intent(this, StorageMngActivity.class);
                startActivity(i);
                break;

            case R.id.printDo:
                i = new Intent(this, PrintDoActivity.class);
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
                Intent intent = new Intent (HomePageActivitySup.this, LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}