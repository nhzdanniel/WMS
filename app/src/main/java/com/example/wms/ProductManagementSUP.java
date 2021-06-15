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

public class ProductManagementSUP extends AppCompatActivity {

    RecyclerView recyclerProductMngSUP;
    ViewProductsRecyclerViewAdapter viewProductsRecyclerViewAdapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_management_sup);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        recyclerProductMngSUP = findViewById(R.id.recyclerProductMngSUP);
        setRecyclerView();
    }

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

    private void setRecyclerView() {
        recyclerProductMngSUP.setHasFixedSize(true);
        recyclerProductMngSUP.setLayoutManager(new LinearLayoutManager(this));
        viewProductsRecyclerViewAdapter = new ViewProductsRecyclerViewAdapter(this,getList());
        recyclerProductMngSUP.setAdapter(viewProductsRecyclerViewAdapter);
    }

    private ArrayList<Product> getList(){
        ArrayList <Product> productList = new ArrayList<>();
        productList.add(new Product("Brake Pads", "87998", "3A", "200", "pieces", "500g", "20x20"));
        productList.add(new Product("Clutch Disks", "56754", "2H", "674", "pieces", "1020g", "30x30"));
        productList.add(new Product("Yellow Rim", "34562", "6A", "16", "pieces", "15000g", "60x60"));
        productList.add(new Product("Black Rim", "34579", "6B", "16", "pieces", "15000g", "70x70"));
        productList.add(new Product("Spark Plug", "72346", "1CA", "800", "pieces", "10g", "2X4"));
        return productList;
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
                viewProductsRecyclerViewAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}