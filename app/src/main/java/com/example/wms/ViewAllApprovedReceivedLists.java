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
import com.example.wms.adapters.ViewAllApprovedReceivedListsAdapter;
import com.example.wms.adapters.ViewAllReceivingListAdapter;
import com.example.wms.models.ApprovedReceivedList;
import com.example.wms.models.ReceivingList;
import com.example.wms.util.VerticalSpacingItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class  ViewAllApprovedReceivedLists extends AppCompatActivity implements ViewAllApprovedReceivedListsAdapter.OnApprovedReceivedListListener {

    private String URL = "http://13.59.50.74/android_connect/viewapprovedreceivinglist.php";
    RecyclerView recyclerviewApprovedReceivedList;
    ViewAllApprovedReceivedListsAdapter viewAllApprovedReceivedListsAdapter;
    DrawerLayout drawerLayout;
    ArrayList<ApprovedReceivedList> approvedreceivingList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_approved_received_lists);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        recyclerviewApprovedReceivedList = findViewById(R.id.recyclerviewApprovedReceivedList);
        approvedreceivingList = new ArrayList<ApprovedReceivedList>();
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
        viewAllApprovedReceivedListsAdapter = new ViewAllApprovedReceivedListsAdapter(this,approvedreceivingList, this);
        recyclerviewApprovedReceivedList.setAdapter(viewAllApprovedReceivedListsAdapter);
        recyclerviewApprovedReceivedList.setHasFixedSize(true);
        recyclerviewApprovedReceivedList.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerviewApprovedReceivedList.addItemDecoration(itemDecorator);
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
                        int sn = productObject.getInt("sn");
                        int ponum = productObject.getInt("PONum");
                        int donum = productObject.getInt("DONum");
                        String supplier = productObject.getString("supplier");
                        String date_rcv = productObject.getString("date_rcv");

                        ApprovedReceivedList rl = new ApprovedReceivedList(sn, ponum, donum, supplier, date_rcv);
                        approvedreceivingList.add(rl);
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
                Toast.makeText(ViewAllApprovedReceivedLists.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        intent.putExtra("selectedApprovedReceivedList", approvedreceivingList.get(position));
        startActivity(intent);
    }
}