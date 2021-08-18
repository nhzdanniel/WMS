package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
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
import com.example.wms.adapters.ViewReceivingListDetailsAdapter;
import com.example.wms.models.ReceivingList;
import com.example.wms.models.ReceivingListDetails;
import com.example.wms.util.VerticalSpacingItemDecorator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IndividualReceivingList extends AppCompatActivity implements ViewReceivingListDetailsAdapter.OnReceivingListDetailsListener, View.OnClickListener {

    private static final String TAG = "individualreceivinglist";
    private String URL = "http://13.59.50.74/android_connect/viewindividualrl.php?PONum=";
    private String updateqty = "http://13.59.50.74/android_connect/updateqtyrcv.php";
    private String addinfourl = "http://13.59.50.74/android_connect/addinfo.php";
    private String updateStatusURL = "http://13.59.50.74/android_connect/updatePOinstatus.php?PONum=";
    private String updatewip = "http://13.59.50.74/android_connect/receiverwip.php?PONum=";
    private String wiprevert = "http://13.59.50.74/android_connect/receiverrevert.php?PONum=";

    private TextView poText, supplierText, etaText;
    private ReceivingList receivingList;
    private Button updateButton;
    private String myText;
    String username;


    RecyclerView recyclerViewReceivingListDetails;
    ViewReceivingListDetailsAdapter viewReceivingListDetailsAdapter;
    ArrayList<ReceivingListDetails> receivingListDetails;

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_receiving_list);
        drawerLayout = findViewById(R.id.drawer_layout);

        poText = findViewById(R.id.po_text);
        supplierText = findViewById(R.id.supplier_text);
        etaText = findViewById(R.id.eta_text);

        updateButton = findViewById(R.id.btn_update);
        //might be problem
        updateButton.setOnClickListener(this);

        /*if (getIntent().hasExtra("username")) {
            username = getIntent().getStringExtra("username");
        }*/

        SharedPreferences userDetails= getApplicationContext().getSharedPreferences("Myuser", Context.MODE_PRIVATE);
        username = userDetails.getString("username", "");

        if (getIntent().hasExtra("selectedReceivingList")) {
            receivingList = getIntent().getParcelableExtra("selectedReceivingList");
        }

        setReceivingListProperties();

        recyclerViewReceivingListDetails = findViewById(R.id.recyclerViewReceivingListDetails);
        receivingListDetails = new ArrayList<ReceivingListDetails>();
        updatewip(String.valueOf(receivingList.getPoNumber()));
    }

    private void setReceivingListProperties() {
        poText.setText(String.valueOf(receivingList.getPoNumber()));
        supplierText.setText(receivingList.getSupplierName());
        etaText.setText(receivingList.getEta());
        loadProducts(String.valueOf(receivingList.getPoNumber()));
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
        revertwip(String.valueOf(receivingList.getPoNumber()));
    }

    public void ClickAboutUs (View view){
        HomePageActivityRec.redirectActivity(this,AboutUsActivity.class);
        revertwip(String.valueOf(receivingList.getPoNumber()));
    }

    public void ClickLogout(View view){
        HomePageActivityRec.Logout(this);
        revertwip(String.valueOf(receivingList.getPoNumber()));
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }

    @Override
    public void onClick(View v){

        AlertDialog.Builder donum = new AlertDialog.Builder(this);

        donum.setTitle("DO Number: ");
        final EditText donumber = new EditText(IndividualReceivingList.this);
        donumber.setInputType(InputType.TYPE_CLASS_TEXT);
        donum.setView(donumber);

        donum.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("adapter", viewReceivingListDetailsAdapter.receivingListDetails.get(0).getExpirydate());

                myText=donumber.getText().toString();
                Toast.makeText(IndividualReceivingList.this, "DO Number entered is "+ myText, Toast.LENGTH_LONG).show();
                ArrayList<String> upc = new ArrayList<String>();
                ArrayList<String> qtylist = new ArrayList<String>();
                ArrayList<String> expirydates = new ArrayList<String>();

                for(int i=0; i<viewReceivingListDetailsAdapter.receivingListDetails.size();i++)
                {
                    upc.add(viewReceivingListDetailsAdapter.receivingListDetails.get(i).getUpc());
                    qtylist.add(viewReceivingListDetailsAdapter.receivingListDetails.get(i).getQtyReceived());
                    expirydates.add(viewReceivingListDetailsAdapter.receivingListDetails.get(i).getExpirydate());
                }
                updateStatus(String.valueOf(receivingList.getPoNumber()));
                updatequantity(upc, qtylist);
                addinfo(upc, qtylist,expirydates);

                Intent intent = new Intent (IndividualReceivingList.this, HomePageActivityRec.class);
                IndividualReceivingList.this.startActivity(intent);
            }
        });
        donum.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        donum.show();

    }

    @Override
    public void onBackPressed() {
        revertwip(String.valueOf(receivingList.getPoNumber()));
        Intent intent = new Intent (this, HomePageActivityRec.class);
        startActivity(intent);
    }

    private void setRecyclerView(){
        recyclerViewReceivingListDetails.setHasFixedSize(true);
        recyclerViewReceivingListDetails.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerViewReceivingListDetails.addItemDecoration(itemDecorator);
        viewReceivingListDetailsAdapter = new ViewReceivingListDetailsAdapter(this,receivingListDetails, this);
        recyclerViewReceivingListDetails.setAdapter(viewReceivingListDetailsAdapter);
    }

    private void updatewip(String PONumber){
        Log.d("output", PONumber);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, updatewip + PONumber, new Response.Listener<String>() {

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

    private void revertwip(String PONumber){
        Log.d("output", PONumber);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, wiprevert + PONumber, new Response.Listener<String>() {

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



    private void updateStatus(String PONumber){
        Log.d("output", PONumber);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, updateStatusURL + PONumber, new Response.Listener<String>() {

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


    private void updatequantity(ArrayList<String> upc, ArrayList<String> qtylist){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, updateqty, new Response.Listener<String>() {

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
                String PONum = String.valueOf(receivingList.getPoNumber());
                ArrayList<String>PONumupc = new ArrayList<String>();

                for(int i=0; i<upc.size();i++)
                {
                    PONumupc.add(PONum+'-'+upc.get(i));
                }
                Log.d("PONumupc", String.valueOf(PONumupc));

                for(int i=0; i<qtylist.size();i++)
                {
                    params.put(PONumupc.get(i), String.valueOf(qtylist.get(i)));
                }

                return params;
            }

        }; ;
        Log.d("output", stringRequest.toString());

        Volley.newRequestQueue(this).add(stringRequest);
    }


    private void addinfo(ArrayList<String> upc, ArrayList<String> qtylist, ArrayList<String> expirydates){
        Log.d("expdate", String.valueOf(expirydates));

        StringRequest stringRequest = new StringRequest(Request.Method.POST, addinfourl, new Response.Listener<String>() {

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
                String PONum = String.valueOf(receivingList.getPoNumber());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String todaydate=String.valueOf(dateFormat.format(new Date()));

                ArrayList<String>PONumupc = new ArrayList<String>();
                for(int i=0; i<upc.size();i++)
                {
                    PONumupc.add(PONum+'-'+upc.get(i));
                }
                Log.d("PONumupc", String.valueOf(PONumupc));

                JSONArray jsonArray = new JSONArray();
                for(int i=0; i<qtylist.size();i++)
                {
                    JSONObject jsonObject = new JSONObject();
                    try{
                        jsonObject.put("PONumupc", String.valueOf(PONumupc.get(i)));
                        jsonObject.put("DONum", myText);
                        jsonObject.put("Qtyreceive", String.valueOf(qtylist.get(i)));
                        jsonObject.put("Receivedby", username);
                        jsonObject.put("Todaydate", todaydate);
                        jsonObject.put("Expirydate", String.valueOf(expirydates.get(i)));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    /*
                    params.put("PONumupc", String.valueOf(PONumupc.get(i)));
                    params.put("DONum", myText);
                    params.put("Qtyreceive", String.valueOf(qtylist.get(i)));
                    params.put("Receivedby", username);
                    params.put("Todaydate", todaydate);
                    params.put("Expirydate", String.valueOf(expirydates.get(i)));

                     */
                    jsonArray.put(jsonObject);
                }
                params.put("JsonArray", String.valueOf(jsonArray));


                return params;
            }

        }; ;
        Log.d("output", stringRequest.toString());

        Volley.newRequestQueue(this).add(stringRequest);
    }









    private void loadProducts(String PONumber){
        Log.d("output", "loadproducts");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL+ PONumber, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray products = new JSONArray(response);

                    for(int i = 0; i<products.length(); i++)
                    {
                        JSONObject productObject = products.getJSONObject(i);
                        //int sn = productObject.getInt("sn");
                        String upc = productObject.getString("upc");
                        String prod_name = productObject.getString("prod_name");
                        int qty_ordered = productObject.getInt("qty_ordered");
                        String qty_received = productObject.getString("qty_rcv");
                        int qty_remaining = productObject.getInt("qty_remaining");
                        //String expiryDate = productObject.getString("expiry_date");


                        ReceivingListDetails product = new ReceivingListDetails(i+1, prod_name, qty_ordered, upc, qty_received, qty_remaining,"");
                        //ReceivingListDetails product = new ReceivingListDetails(i+1, prod_name, expiryDate, upc, qty_received, qty_ordered, qty_remaining);
                        receivingListDetails.add(product);


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
                Toast.makeText(IndividualReceivingList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                viewReceivingListDetailsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onReceivingListDetailsClick(int position) {
        //Log.d (TAG, "onPPClick: clicked" + position);

        Intent intent = new Intent (this, IndividualReceivingList.class);
        intent.putExtra("selectedReceivingListDetails", receivingListDetails.get(position));
        intent.putExtra("username", username);
        startActivity(intent);
    }
}