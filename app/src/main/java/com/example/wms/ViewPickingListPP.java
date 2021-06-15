package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class ViewPickingListPP extends AppCompatActivity {

    RecyclerView recyclerviewPickingListPP;
    ViewPickingListsRecyclerViewAdapterPP viewPickingListsRecyclerViewAdapterPP;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_picking_list_pp);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        recyclerviewPickingListPP = findViewById(R.id.recyclerviewPickingListPP);
        setRecyclerView();
    }

    //drawer settings
    public void ClickMenu (View view){
        //HomePageActivityWm.openDrawer(drawerLayout);
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo (View view){
        HomePageActivityPp.closeDrawer(drawerLayout);
    }

    public void ClickHome (View view){
        HomePageActivityPp.redirectActivity(this, HomePageActivityPp.class);
    }

    public void ClickAboutUs (View view){
        HomePageActivityPp.redirectActivity(this, AboutUsActivity.class);
    }

    public void ClickLogout (View view){
        HomePageActivityPp.Logout(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        HomePageActivityPp.closeDrawer(drawerLayout);
    }

    private void setRecyclerView(){
        recyclerviewPickingListPP.setHasFixedSize(true);
        recyclerviewPickingListPP.setLayoutManager(new LinearLayoutManager(this));
        viewPickingListsRecyclerViewAdapterPP = new ViewPickingListsRecyclerViewAdapterPP(this,getList());
        recyclerviewPickingListPP.setAdapter(viewPickingListsRecyclerViewAdapterPP);
    }

    private ArrayList<PickingList> getList(){
        ArrayList <PickingList> pickingList = new ArrayList<>();
        pickingList.add(new PickingList("76839", "ABC Co.", "290920"));
        pickingList.add(new PickingList("35532", "GBI Limited", "240220"));
        pickingList.add(new PickingList("23532", "Ah Beng Automotive.", "080220"));
        return pickingList;
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
                viewPickingListsRecyclerViewAdapterPP.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}