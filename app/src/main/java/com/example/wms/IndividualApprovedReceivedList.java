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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wms.adapters.ViewApprovedReceivedListDetailsAdapter;
import com.example.wms.adapters.ViewReceivingListDetailsAdapter;
import com.example.wms.models.ApprovedReceivedList;
import com.example.wms.models.ApprovedReceivedListDetails;
import com.example.wms.models.PickingListDetails;
import com.example.wms.models.ReceivingList;
import com.example.wms.models.ReceivingListDetails;
import com.example.wms.util.VerticalSpacingItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IndividualApprovedReceivedList extends AppCompatActivity implements ViewApprovedReceivedListDetailsAdapter.OnApprovedReceivedListDetailsListener {

    private static final String TAG = "individualpickinglist";
    private String URL = "http://13.59.50.74/android_connect/viewindividualapproverl.php";
    private TextView poText, doText;
    private ApprovedReceivedList approvedReceivedList;

    RecyclerView recyclerViewApprovedReceivedListDetails;
    ViewApprovedReceivedListDetailsAdapter viewApprovedReceivedListDetailsAdapter;
    ArrayList<ApprovedReceivedListDetails> approvedReceivedListDetails;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_approved_received_list);
        drawerLayout = findViewById(R.id.drawer_layout);

        poText = findViewById(R.id.po_text);
        doText = findViewById(R.id.do_text);

        if (getIntent().hasExtra("selectedApprovedReceivedList")) {
            approvedReceivedList = getIntent().getParcelableExtra("selectedApprovedReceivedList");
        }

        setReceivingListProperties();

        recyclerViewApprovedReceivedListDetails = findViewById(R.id.recyclerViewApprovedReceivedListDetails);
        approvedReceivedListDetails = new ArrayList<ApprovedReceivedListDetails>();
        loadProducts();
    }

    private void setReceivingListProperties() {
        poText.setText(String.valueOf(approvedReceivedList.getPoNumber()));
        doText.setText(String.valueOf(approvedReceivedList.getDoNumber()));
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
        recyclerViewApprovedReceivedListDetails.setHasFixedSize(true);
        recyclerViewApprovedReceivedListDetails.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerViewApprovedReceivedListDetails.addItemDecoration(itemDecorator);
        viewApprovedReceivedListDetailsAdapter = new ViewApprovedReceivedListDetailsAdapter(this,approvedReceivedListDetails, this);
        recyclerViewApprovedReceivedListDetails.setAdapter(viewApprovedReceivedListDetailsAdapter);
    }

    private void loadProducts() {
        Log.d("output", "loadproducts");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray products = new JSONArray(response);

                    for (int i = 0; i < products.length(); i++) {
                        JSONObject productObject = products.getJSONObject(i);
                        //int sn = productObject.getInt("sn");
                        int upc = productObject.getInt("upc");
                        String prod_name = productObject.getString("prod_name");
                        String sku = productObject.getString("sku");
                        String location = productObject.getString("location");

                        ApprovedReceivedListDetails product = new ApprovedReceivedListDetails(i+1, location, prod_name, upc, sku);
                        approvedReceivedListDetails.add(product);
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
                Toast.makeText(IndividualApprovedReceivedList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                viewApprovedReceivedListDetailsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onApprovedReceivedListDetailsClick(int position) {
        //Log.d (TAG, "onPPClick: clicked" + position);

        Intent intent = new Intent (this, IndividualReceivingList.class);
        intent.putExtra("selectedApprovedReceivedListDetails", approvedReceivedListDetails.get(position));
        startActivity(intent);
    }
}