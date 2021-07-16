package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.wms.models.PickingList;
import com.example.wms.models.ReceivingList;

public class IndividualReceivingList extends AppCompatActivity {

    private static final String TAG = "individualpickinglist";
    private TextView poText, supplierText, etaText;
    private ReceivingList receivingList;
    private boolean existingPickingList;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_receiving_list);
        drawerLayout = findViewById(R.id.drawer_layout);

        poText = findViewById(R.id.po_text);
        supplierText = findViewById(R.id.supplier_text);
        etaText = findViewById(R.id.eta_text);

        if (getIntent().hasExtra("selectedReceivingList")) {
            receivingList = getIntent().getParcelableExtra("selectedReceivingList");
        }

        setReceivingListProperties();
    }

    private void setReceivingListProperties() {
        poText.setText(String.valueOf(receivingList.getPoNumber()));
        supplierText.setText(receivingList.getSupplierName());
        etaText.setText(receivingList.getEta());
    }

    //drawer settings
    public void ClickMenu (View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo (View view){
        closeDrawer (drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        HomePageActivityPp.redirectActivity(this, HomePageActivityPp.class);
    }

    public void ClickAboutUs (View view){
        redirectActivity(this,AboutUsActivity.class);
    }

    public void ClickLogout(View view){
        Logout(this);
    }

    public static void Logout(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
/*                activity.finishAffinity();
                System.exit(0);*/
                Intent intent = new Intent (activity, LoginActivity.class);
                activity.startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
}