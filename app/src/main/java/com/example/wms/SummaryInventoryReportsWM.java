package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class SummaryInventoryReportsWM extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_inventory_reports_wm);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    //drawer settings
    public void ClickMenu (View view){
        HomePageActivityWm.openDrawer(drawerLayout);
    }

    public void ClickLogo (View view){
        HomePageActivityWm.closeDrawer(drawerLayout);
    }

    public void ClickHome (View view){
        HomePageActivityWm.redirectActivity(this, HomePageActivityWm.class);
    }

    public void ClickAboutUs (View view){
        HomePageActivityWm.redirectActivity(this, AboutUsActivity.class);
    }

    public void ClickLogout (View view){
        HomePageActivityWm.Logout(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        HomePageActivityWm.closeDrawer(drawerLayout);
    }
}