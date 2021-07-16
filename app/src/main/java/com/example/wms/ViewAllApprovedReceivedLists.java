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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.wms.adapters.ViewAllApprovedReceivedListsAdapter;
import com.example.wms.adapters.ViewAllReceivingListAdapter;
import com.example.wms.models.ApprovedReceivedList;
import com.example.wms.models.ReceivingList;
import com.example.wms.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;

public class ViewAllApprovedReceivedLists extends AppCompatActivity implements ViewAllApprovedReceivedListsAdapter.OnApprovedReceivedListListener {

    RecyclerView recyclerviewApprovedReceivedList;
    ViewAllApprovedReceivedListsAdapter viewAllApprovedReceivedListsAdapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_approved_received_lists);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        recyclerviewApprovedReceivedList = findViewById(R.id.recyclerviewApprovedReceivedList);
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
        recyclerviewApprovedReceivedList.setHasFixedSize(true);
        recyclerviewApprovedReceivedList.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerviewApprovedReceivedList.addItemDecoration(itemDecorator);
        viewAllApprovedReceivedListsAdapter = new ViewAllApprovedReceivedListsAdapter(this,getList(), this);
        recyclerviewApprovedReceivedList.setAdapter(viewAllApprovedReceivedListsAdapter);
    }

    private ArrayList<ApprovedReceivedList> getList(){
        ArrayList <ApprovedReceivedList> approvedReceivedLists = new ArrayList<>();
        approvedReceivedLists.add(new ApprovedReceivedList(1,87998, 84759, "ABC CO", "Not Received"));
        return approvedReceivedLists;
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
                viewAllApprovedReceivedListsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onApprovedReceivedListClick(int position) {
        //Log.d (TAG, "onPPClick: clicked" + position);

        Intent intent = new Intent (this, IndividualApprovedReceivedList.class);
        intent.putExtra("selectedApprovedReceivedList", getList().get(position));
        startActivity(intent);
    }
}