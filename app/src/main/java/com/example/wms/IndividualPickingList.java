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

import com.example.wms.adapters.ViewPickingListDetailsAdapter;
import com.example.wms.adapters.ViewReceivingListDetailsAdapter;
import com.example.wms.models.PickingList;
import com.example.wms.models.PickingListDetails;
import com.example.wms.models.ReceivingListDetails;
import com.example.wms.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class IndividualPickingList extends AppCompatActivity implements ViewPickingListDetailsAdapter.OnPickingListDetailsListener{
    private static final String TAG = "individualpickinglist";
    private TextView poText, companyText;
    private PickingList pickingList;
    RecyclerView recyclerviewPickingListDetails;
    ViewPickingListDetailsAdapter viewPickingListDetailsAdapter;

    private boolean existingPickingList;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_picking_list);
        drawerLayout = findViewById(R.id.drawer_layout);

        poText = findViewById(R.id.po_text);
        companyText = findViewById(R.id.company_text);


        if (getIntent().hasExtra("selectedPickingList")) {
            pickingList = getIntent().getParcelableExtra("selectedPickingList");
        }

        setPickingListProperties();

        recyclerviewPickingListDetails = findViewById(R.id.recyclerviewPickingListDetails);
        setRecyclerView();
    }

    private void setPickingListProperties() {
        poText.setText(String.valueOf(pickingList.getPoNumber()));
        companyText.setText(pickingList.getCompanyName());
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
        HomePageActivityPp.redirectActivity(this,AboutUsActivity.class);
    }

    public void ClickLogout(View view){
        HomePageActivityPp.Logout(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }

    private void setRecyclerView(){
        recyclerviewPickingListDetails.setHasFixedSize(true);
        recyclerviewPickingListDetails.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerviewPickingListDetails.addItemDecoration(itemDecorator);
        viewPickingListDetailsAdapter = new ViewPickingListDetailsAdapter(this,getList(), this);
        recyclerviewPickingListDetails.setAdapter(viewPickingListDetailsAdapter);
    }

    private ArrayList<PickingListDetails> getList(){
        ArrayList <PickingListDetails> pickingListDetails = new ArrayList<>();
        pickingListDetails.add(new PickingListDetails(1,"A.1.1", 23456, "muffler", 602344, 874383));
        return pickingListDetails;
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
                viewPickingListDetailsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onPickingListDetailsClick(int position) {
        //Log.d (TAG, "onPPClick: clicked" + position);

        Intent intent = new Intent (this, IndividualReceivingList.class);
        intent.putExtra("selectedPickingListDetails", getList().get(position));
        startActivity(intent);
    }
}