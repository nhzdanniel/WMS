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

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wms.adapters.ViewPickingListsRecyclerViewAdapterPP;
import com.example.wms.models.PickingList;
import com.example.wms.util.VerticalSpacingItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewPickingListPP extends AppCompatActivity implements ViewPickingListsRecyclerViewAdapterPP.OnPickingListListener {

    private static final String TAG = "ppactivity";
    private String URL = "http://13.59.50.74/android_connect/viewpickinglist.php";
    RecyclerView recyclerviewPickingListPP;
    ViewPickingListsRecyclerViewAdapterPP viewPickingListsRecyclerViewAdapterPP;
    DrawerLayout drawerLayout;
    ArrayList<PickingList> pickingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_picking_list_pp);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        recyclerviewPickingListPP = findViewById(R.id.recyclerviewPickingListPP);
        pickingList = new ArrayList<PickingList>();
        viewList();
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
        Log.d("output", "setrc");
        viewPickingListsRecyclerViewAdapterPP = new ViewPickingListsRecyclerViewAdapterPP(this, pickingList, this);
        recyclerviewPickingListPP.setAdapter(viewPickingListsRecyclerViewAdapterPP);
        recyclerviewPickingListPP.setHasFixedSize(true);
        recyclerviewPickingListPP.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerviewPickingListPP.addItemDecoration(itemDecorator);

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
                        int PONum = productObject.getInt("PONum");
                        String company = productObject.getString("company");
                        String date = productObject.getString("date_created");

                        PickingList pl = new PickingList(sn, company, PONum, date);
                        pickingList.add(pl);


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
                Toast.makeText(ViewPickingListPP.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                viewPickingListsRecyclerViewAdapterPP.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onPickingListClick(int position) {
        Log.d (TAG, "onPPClick: clicked" + position);

        Intent intent = new Intent (this, IndividualPickingList.class);
        intent.putExtra("selectedPickingList", pickingList.get(position));
        startActivity(intent);
    }
}