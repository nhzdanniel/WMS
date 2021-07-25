package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wms.adapters.ViewAllReceivingListAdapter;
import com.example.wms.adapters.ViewReceivingListDetailsAdapter;
import com.example.wms.models.PickingList;
import com.example.wms.models.ReceivingList;
import com.example.wms.models.ReceivingListDetails;
import com.example.wms.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class IndividualReceivingList extends AppCompatActivity implements ViewReceivingListDetailsAdapter.OnReceivingListDetailsListener {

    private static final String TAG = "individualreceivinglist";
    private TextView poText, supplierText, etaText;
    private ReceivingList receivingList;
    RecyclerView recyclerViewReceivingListDetails;
    ViewReceivingListDetailsAdapter viewReceivingListDetailsAdapter;

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

        recyclerViewReceivingListDetails = findViewById(R.id.recyclerViewReceivingListDetails);
        setRecyclerView();
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
        HomePageActivityRec.redirectActivity(this, HomePageActivityRec.class);
    }

    public void ClickAboutUs (View view){
        HomePageActivityRec.redirectActivity(this,AboutUsActivity.class);
    }

    public void ClickLogout(View view){
        HomePageActivityRec.Logout(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }

    private void setRecyclerView(){
        recyclerViewReceivingListDetails.setHasFixedSize(true);
        recyclerViewReceivingListDetails.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerViewReceivingListDetails.addItemDecoration(itemDecorator);
        viewReceivingListDetailsAdapter = new ViewReceivingListDetailsAdapter(this,getList(), this);
        recyclerViewReceivingListDetails.setAdapter(viewReceivingListDetailsAdapter);
    }

    private ArrayList<ReceivingListDetails> getList(){
        ArrayList <ReceivingListDetails> receivingListDetails = new ArrayList<>();
        receivingListDetails.add(new ReceivingListDetails(1,87998, "Exhaust muffler (14')", 60, 60, 0));
        return receivingListDetails;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewReceivingListDetailsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onReceivingListDetailsClick(int position) {
        //Log.d (TAG, "onPPClick: clicked" + position);

        Intent intent = new Intent (this, IndividualReceivingList.class);
        intent.putExtra("selectedReceivingListDetails", getList().get(position));
        startActivity(intent);
    }
}



























