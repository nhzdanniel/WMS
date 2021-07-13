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
        productList.add(new Product("87998", "200", "500", "pcs", "Brake Pad", "3B", "20x20"));
        productList.add(new Product("45323", "563", "1500", "pcs", "Bride Bucket Seat", "6G", "57x23"));
        productList.add(new Product("53358", "747", "200", "pcs", "Cat Converter", "3H", "78x54"));
        productList.add(new Product("34522", "34", "200", "pcs", "Brake Caliper", "9H", "23x27"));
        productList.add(new Product("45398", "578", "577", "pcs", "Silencer", "13F", "27x29"));
        productList.add(new Product("34532", "7534", "346", "pcs", "Brake Pad", "15H", "23x29"));
        productList.add(new Product("45634", "645", "23", "pcs", "Steering Wheel", "3D", "23x26"));

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