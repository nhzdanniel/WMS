package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ViewPoSUP extends AppCompatActivity implements View.OnClickListener{

    public CardView c1, c2;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_po_sup);

        c1 = (CardView) findViewById(R.id.inboundPoSUP);
        c2 = (CardView) findViewById(R.id.outboundPoSUP);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
    }

    //drawer settings
    public void ClickMenu (View view){
        HomePageActivitySup.openDrawer(drawerLayout);
    }

    public void ClickLogo (View view){
        HomePageActivitySup.closeDrawer(drawerLayout);
    }

    public void ClickHome (View view){
        HomePageActivitySup.redirectActivity(this, HomePageActivitySup.class);
    }

    public void ClickAboutUs (View view){
        HomePageActivitySup.redirectActivity(this, AboutUsActivity.class);
    }

    public void ClickLogout (View view){
        HomePageActivitySup.Logout(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        HomePageActivitySup.closeDrawer(drawerLayout);
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.inboundPoSUP:
                i = new Intent(this, InboundPurchaseOrdersSUP.class);
                startActivity(i);
                break;

            case R.id.outboundPoSUP:
                i = new Intent(this, OutboundPurchaseOrdersSUP.class);
                startActivity(i);
                break;
        }
    }
}