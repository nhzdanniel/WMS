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
import com.example.wms.adapters.ViewPickingListDetailsAdapter;
import com.example.wms.adapters.ViewReceivingListDetailsAdapter;
import com.example.wms.models.PickingList;
import com.example.wms.models.PickingListDetails;
import com.example.wms.models.ReceivingListDetails;
import com.example.wms.util.VerticalSpacingItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IndividualPickingList extends AppCompatActivity implements ViewPickingListDetailsAdapter.OnPickingListDetailsListener{
    private static final String TAG = "individualpickinglist";
    private String URL = "http://13.59.50.74/android_connect/viewindividualpl.php?PONum=";
    private TextView poText, companyText;
    private PickingList pickingList;
    RecyclerView recyclerviewPickingListDetails;
    ViewPickingListDetailsAdapter viewPickingListDetailsAdapter;
    ArrayList<PickingListDetails> pickingListDetails;


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
        pickingListDetails = new ArrayList<PickingListDetails>();
    }

    private void setPickingListProperties() {
        poText.setText(String.valueOf(pickingList.getPoNumber()));
        companyText.setText(pickingList.getCompanyName());
        loadProducts(String.valueOf(pickingList.getPoNumber()));
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
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerviewPickingListDetails.addItemDecoration(itemDecorator);
        viewPickingListDetailsAdapter = new ViewPickingListDetailsAdapter(this,pickingListDetails, this);
        recyclerviewPickingListDetails.setAdapter(viewPickingListDetailsAdapter);
        recyclerviewPickingListDetails.setHasFixedSize(true);
        recyclerviewPickingListDetails.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadProducts(String PONumber){
        Log.d("output", PONumber);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL + PONumber, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response", response);

                try {
                    JSONArray products = new JSONArray(response);

                    for(int i = 0; i<products.length(); i++)
                    {
                        JSONObject productObject = products.getJSONObject(i);
                        int sn = productObject.getInt("sn");
                        int upc = productObject.getInt("upc");
                        String prod_name = productObject.getString("prod_name");
                        String sku = productObject.getString("sku");
                        String sku_scanned = productObject.getString("sku_scanned");
                        String location = productObject.getString("location");

                        PickingListDetails product = new PickingListDetails(sn, location, upc, prod_name, sku,
                                                                            sku_scanned);
                        pickingListDetails.add(product);
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
                Toast.makeText(IndividualPickingList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                viewPickingListDetailsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onPickingListDetailsClick(int position) {
        //Log.d (TAG, "onPPClick: clicked" + position);

        Intent intent = new Intent (this, IndividualPickingList.class);
        intent.putExtra("selectedPickingListDetails", pickingListDetails.get(position));
        startActivity(intent);
    }
}