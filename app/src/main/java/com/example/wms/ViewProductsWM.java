package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class ViewProductsWM extends AppCompatActivity {

    RecyclerView recyclerviewProductsWM;
    ViewProductsRecyclerViewAdapter viewProductsRecyclerViewAdapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products_wm);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        recyclerviewProductsWM = findViewById(R.id.recyclerviewProductsWM);
        setRecyclerView();
    }

    //drawer settings
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

    private void setRecyclerView(){
        recyclerviewProductsWM.setHasFixedSize(true);
        recyclerviewProductsWM.setLayoutManager(new LinearLayoutManager(this));
        viewProductsRecyclerViewAdapter = new ViewProductsRecyclerViewAdapter(this,getList());
        recyclerviewProductsWM.setAdapter(viewProductsRecyclerViewAdapter);
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

/*    //logout
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
                Intent intent = new Intent (ViewProductsWM.this, LoginActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}