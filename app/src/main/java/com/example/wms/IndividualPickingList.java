package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.HashMap;
import java.util.Map;

public class IndividualPickingList extends AppCompatActivity implements View.OnClickListener/*implements ViewPickingListDetailsAdapter.OnPickingListDetailsListener*/{
    private static final String TAG = "individualpickinglist";
    private String viewIndividualURL = "http://13.59.50.74/android_connect/viewindividualpl.php?SONum=";
    private String updateStatusURL = "http://13.59.50.74/android_connect/updatePOoutstatus.php";
    private String updatePOoutsku = "http://13.59.50.74/android_connect/updatePOoutsku.php";
    private String updateprodinvurl = "http://13.59.50.74/android_connect/updateprodindv.php";

    private TextView poText, companyText;
    private PickingList pickingList;
    private Button scanButton;
    private Button updateButton;
    String username;




    RecyclerView recyclerviewPickingListDetails;
    ViewPickingListDetailsAdapter viewPickingListDetailsAdapter;
    ArrayList<PickingListDetails> pickingListDetails;

    DrawerLayout drawerLayout;

    final int BARCODE_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_picking_list);
        drawerLayout = findViewById(R.id.drawer_layout);

        poText = findViewById(R.id.po_text);
        companyText = findViewById(R.id.company_text);

        updateButton = findViewById(R.id.btn_update);
        updateButton.setOnClickListener(this);

       // if (getIntent().hasExtra("username")) {
       //     username = getIntent().getStringExtra("username");
       // }
        if (getIntent().hasExtra("selectedPickingList")) {
            pickingList = getIntent().getParcelableExtra("selectedPickingList");
        }
        SharedPreferences userDetails= getApplicationContext().getSharedPreferences("Myuser",Context.MODE_PRIVATE);
        username = userDetails.getString("username", "");



        setPickingListProperties();

        recyclerviewPickingListDetails = findViewById(R.id.recyclerviewPickingListDetails);
        pickingListDetails = new ArrayList<PickingListDetails>();
    }

    private void setPickingListProperties() {
        poText.setText(String.valueOf(pickingList.getSoNumber()));
        companyText.setText(pickingList.getCompanyName());
        loadProducts(String.valueOf(pickingList.getSoNumber()));
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

    @Override
    public void onClick(View v) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle("Please ensure that all SKU is filled.");
        builder.setMessage("If the SKU can't be found, use another SKU and notify Supervisor.");

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("output", String.valueOf(pickingList.getPoNumber()));
                updateStatus(String.valueOf(pickingList.getPoNumber()));
                Log.d("Help", viewPickingListDetailsAdapter.pickingListDetails.get(0).getSkuScanned());
                ArrayList<String> skuscans= new ArrayList<String>();
                ArrayList<String> skulist = new ArrayList<String>();
                for(int i=0; i<viewPickingListDetailsAdapter.pickingListDetails.size();i++){
                    skuscans.add(viewPickingListDetailsAdapter.pickingListDetails.get(i).getSkuScanned());
                }
                for(int i=0; i<viewPickingListDetailsAdapter.pickingListDetails.size();i++){
                    skulist.add(viewPickingListDetailsAdapter.pickingListDetails.get(i).getSku());
                }

                Log.d("suicide", String.valueOf(skuscans));
                updateprodindv(skuscans);
                updatesku(skuscans, skulist);
                Intent intent = new Intent (IndividualPickingList.this, HomePageActivityPp.class);
                IndividualPickingList.this.startActivity(intent);

            }
        });
        builder.show();


    }

    public void setRecyclerView(){
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerviewPickingListDetails.addItemDecoration(itemDecorator);
        viewPickingListDetailsAdapter = new ViewPickingListDetailsAdapter(pickingListDetails);
        recyclerviewPickingListDetails.setAdapter(viewPickingListDetailsAdapter);
        recyclerviewPickingListDetails.setHasFixedSize(true);
        recyclerviewPickingListDetails.setLayoutManager(new LinearLayoutManager(this));

        viewPickingListDetailsAdapter.setOnItemClickListener(new ViewPickingListDetailsAdapter.OnPickingListDetailsListener() {
            @Override
            public void onScanClick(int position) {
                //startActivity(new Intent(getApplicationContext(), ScanCode.class));
                Intent intent = new Intent(IndividualPickingList.this, ScanCode.class);
                intent.putExtra("clickPosition", position);
                startActivityForResult(intent, 500);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 500) { //Remember the result code we set in startActivityForResult()? This is how we identify our incoming data
            if (resultCode == Activity.RESULT_OK) {  //Data is received
                String result = data.getStringExtra("tvresult1");
                //Your result is received
                int adapterPosition = data.getIntExtra("clickPosition", -1);
                viewPickingListDetailsAdapter.updateList(adapterPosition, result);
                Log.d("scanning", result);
            }
        }
    }

    private void loadProducts(String SONumber){
        Log.d("output", SONumber);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, viewIndividualURL + SONumber, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response", response);

                try {
                    JSONArray products = new JSONArray(response);

                    for(int i = 0; i<products.length(); i++)
                    {
                        JSONObject productObject = products.getJSONObject(i);
                        //int sn = productObject.getInt("sn");
                        int upc = productObject.getInt("upc");
                        String prod_name = productObject.getString("prod_name");
                        String sku = productObject.getString("sku");
                        String sku_scanned = "";
                        //String sku_scanned = productObject.getString("sku_scanned");
                        String location = productObject.getString("location");

                        PickingListDetails product = new PickingListDetails(i+1, location, upc, prod_name, sku,
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
        })
                       /*{
        @Override
        protected Map<String,String> getParams(){
        Map<String,String> params = new HashMap<String, String>();
        params.put(KEY_USERNAME,username);
        params.put(KEY_PASSWORD,password);
        params.put(KEY_EMAIL, email);
        return params;
    }

    }; */;
        Log.d("output", stringRequest.toString());

        Volley.newRequestQueue(this).add(stringRequest);
    }


    private void updateStatus(String PONumber){
        Log.d("output", PONumber);
        String params = "?PONum="+PONumber+ "&username="+username;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, updateStatusURL + params, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response", "request success");
                Log.d("response", response);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.getMessage());

            }
        });
        Log.d("output", stringRequest.toString());

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void updateprodindv(ArrayList<String> skuscans){
        //Log.d("output", skulist);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateprodinvurl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response", response);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.getMessage());

            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                for(int i=0; i<skuscans.size();i++)
                {
                    params.put("skuscan"+i, skuscans.get(i));
                }
                Log.d("params", String.valueOf(params));

                return params;
            }

        };;
        Log.d("output", stringRequest.toString());

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void updatesku(ArrayList<String> skuscans, ArrayList<String> skulist){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, updatePOoutsku, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("response", "request success");
                Log.d("response", response);




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error", error.getMessage());

            }
        })
                       {
        @Override
        protected Map<String,String> getParams(){
        Map<String,String> params = new HashMap<String, String>();
        for(int i=0; i<skuscans.size();i++)
        {
            params.put(skulist.get(i),skuscans.get(i));
        }
            Log.d("params", String.valueOf(params));

            return params;
    }

    }; ;
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

    public void setButton(){
        scanButton = findViewById(R.id.scanButton);
    }
}