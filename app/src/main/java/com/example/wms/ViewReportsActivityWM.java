package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ViewReportsActivityWM extends AppCompatActivity implements View.OnClickListener{
    public CardView c1, c2, c3;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports_wm);

        c1 = (CardView) findViewById(R.id.reportSummaryOutboundDoWM);
        c2 = (CardView) findViewById(R.id.reportSummaryInboundPoWM);
        c3 = (CardView) findViewById(R.id.reportInventoryWM);
        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
    }

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

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.reportSummaryOutboundDoWM:
                i = new Intent(this, SummaryDoOutboundWM.class);
                startActivity(i);
                break;

            case R.id.reportSummaryInboundPoWM:
                i = new Intent(this, SummaryPoInboundWM.class);
                startActivity(i);
                break;

            case R.id.reportInventoryWM:
                i = new Intent(this, SummaryInventoryReportsWM.class);
                startActivity(i);
                break;
        }
    }

    /*//logout
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
                Intent intent = new Intent (ViewReportsActivityWM.this, LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}