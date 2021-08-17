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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wms.adapters.ViewAllReceivingListAdapter;
import com.example.wms.models.ReceivingList;
import com.example.wms.util.VerticalSpacingItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewAllReceivingListREC extends AppCompatActivity implements ViewAllReceivingListAdapter.OnReceivingListListener{

    private String URL = "http://13.59.50.74/android_connect/viewreceivinglist.php";
    RecyclerView recyclerviewReceivingList;
    ViewAllReceivingListAdapter viewAllReceivingListAdapter;
    DrawerLayout drawerLayout;
    ArrayList<ReceivingList> receivingList;
    //String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_receiving_list_rec);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        /*if (getIntent().hasExtra("username")) {
            username = getIntent().getStringExtra("username");
        }*/

        recyclerviewReceivingList = findViewById(R.id.recyclerviewReceivingList);
        receivingList = new ArrayList<ReceivingList>();
        viewList();
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
        viewAllReceivingListAdapter = new ViewAllReceivingListAdapter(this, this);
        viewAllReceivingListAdapter.submitList(receivingList);
        recyclerviewReceivingList.setAdapter(viewAllReceivingListAdapter);
        recyclerviewReceivingList.setHasFixedSize(true);
        recyclerviewReceivingList.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerviewReceivingList.addItemDecoration(itemDecorator);

    }

    private void viewList(){
        Log.d("output", "loadproducts");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray products = new JSONArray(response);

                    for(int i = 0; i<products.length(); i++)
                    {
                        JSONObject productObject = products.getJSONObject(i);
                        //int sn = productObject.getInt("sn");
                        int ponum = productObject.getInt("PONum");
                        String supplier = productObject.getString("supplier");
                        String date = productObject.getString("eta");
                        String status = productObject.getString("status");

                        ReceivingList rl = new ReceivingList(i+1, ponum, supplier, date, status);
                        receivingList.add(rl);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setRecyclerView();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("output", "rb");
                Toast.makeText(ViewAllReceivingListREC.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Log.d("output", stringRequest.toString());

        Volley.newRequestQueue(this).add(stringRequest);
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
                Log.d("search",newText);
                viewAllReceivingListAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onReceivingListClick(ReceivingList rl) {
        //Log.d (TAG, "onPPClick: clicked" + position);
        Intent intent = new Intent (this, IndividualReceivingList.class);
        intent.putExtra("selectedReceivingList", rl);
        //intent.putExtra("username", username);
        startActivity(intent);
    }
}