package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.wms.adapters.ViewAllReceivingListAdapter;
import com.example.wms.adapters.ViewPickingListsRecyclerViewAdapterPP;
import com.example.wms.models.ReceivingList;
import com.example.wms.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class ViewAllReceivingListREC extends AppCompatActivity implements ViewAllReceivingListAdapter.OnReceivingListListener{

    RecyclerView recyclerviewReceivingList;
    ViewAllReceivingListAdapter viewAllReceivingListAdapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_receiving_list_rec);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        recyclerviewReceivingList = findViewById(R.id.recyclerviewReceivingList);
        setRecyclerView();
    }

    //drawer settings
    public void ClickMenu (View view){
        //HomePageActivityWm.openDrawer(drawerLayout);
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo (View view){
        HomePageActivityRec.closeDrawer(drawerLayout);
    }

    public void ClickHome (View view){
        HomePageActivityRec.redirectActivity(this, HomePageActivityRec.class);
    }

    public void ClickAboutUs (View view){
        HomePageActivityRec.redirectActivity(this, AboutUsActivity.class);
    }

    public void ClickLogout (View view){
        HomePageActivityRec.Logout(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        HomePageActivityRec.closeDrawer(drawerLayout);
    }

    private void setRecyclerView(){
        recyclerviewReceivingList.setHasFixedSize(true);
        recyclerviewReceivingList.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerviewReceivingList.addItemDecoration(itemDecorator);
        viewAllReceivingListAdapter = new ViewAllReceivingListAdapter(this,getList(), this);
        recyclerviewReceivingList.setAdapter(viewAllReceivingListAdapter);
    }

    private ArrayList<ReceivingList> getList(){
        ArrayList <ReceivingList> receivingList = new ArrayList<>();
        receivingList.add(new ReceivingList(1,87998, "ABC CO", "12/07/20", "Not Received"));
        receivingList.add(new ReceivingList(2,23452, "DFG CO", "24/07/20", "Fully Received"));
        receivingList.add(new ReceivingList(3,34634, "GHF CO", "16/07/20", "Not Received"));
        receivingList.add(new ReceivingList(4,57633, "HTT CO", "05/07/20", "Not Received"));
        return receivingList;
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
                viewAllReceivingListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onReceivingListClick(int position) {
        //Log.d (TAG, "onPPClick: clicked" + position);

        Intent intent = new Intent (this, IndividualReceivingList.class);
        intent.putExtra("selectedReceivingList", getList().get(position));
        startActivity(intent);
    }
}