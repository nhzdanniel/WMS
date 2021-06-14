package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class OutboundPurchaseOrdersSUP extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outbound_purchase_orders_sup);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}