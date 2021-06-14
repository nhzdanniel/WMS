package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

public class ApprovalSystemSUP extends AppCompatActivity {

    RecyclerView recyclerviewProductsWM;
    ViewProductsRecyclerViewAdapterWM viewProductsRecyclerViewAdapterWM;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval_system_sup);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

 /*       recyclerviewProductsWM = findViewById(R.id.recyclerviewProductsWM);
        setRecyclerView();*/
    }

/*    private void setRecyclerView() {
    }*/
//drawer settings
public void ClickMenu (View view){
    //HomePageActivityWm.openDrawer(drawerLayout);
    drawerLayout.openDrawer(GravityCompat.START);
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