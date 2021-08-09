package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText eName, ePassword;
    private String username, password;
    private String URL = "http://13.59.50.74/android_connect/login.php";

    //private Button eLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = password = "";
        eName = findViewById(R.id.eTName);
        ePassword = findViewById(R.id.eTPassword);
        //eLogin = findViewById(R.id.btnLogin);



    }


    public void login(View view) {
        username = eName.getText().toString().trim();
        password = ePassword.getText().toString().trim();

        SharedPreferences userDetails = getSharedPreferences("Myuser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userDetails.edit();

        editor.putString("username", username);
        editor.commit();
        String stored = userDetails.getString("username","");
        Log.d("oks",stored);


        if(!username.equals("") && !password.equals("")){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response", response);
                    if (response.equals("pp")) {
                        Intent intent = new Intent(LoginActivity.this, HomePageActivityPp.class);
                        //intent.putExtra("username", username);
                        startActivity(intent);
                    }
                    else if (response.equals("rec")) {
                        Intent intent = new Intent(LoginActivity.this, HomePageActivityRec.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }
                    else if (response.equals("failure")){
                        Toast.makeText(LoginActivity.this, "Invalid Username or Password. Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<String, String>();
                    data.put("name", username);
                    data.put("password", password);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
        else{
            Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();
        }

    }

/*        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = eName.getText().toString();
                String inputPassword = ePassword.getText().toString();

                if (inputName.isEmpty() || inputPassword.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username or Password is empty", Toast.LENGTH_SHORT).show();
                }
                else if (inputName.equals("wm") && inputPassword.equals("1")){
                    Intent intent = new Intent(LoginActivity.this, HomePageActivityWm.class);
                    startActivity(intent);
                }
                else if (inputName.equals("sup")){
                    Intent intent = new Intent (LoginActivity.this, HomePageActivitySup.class);
                    startActivity(intent);
                }
                else if (inputName.equals("pp")){
                    Intent intent = new Intent (LoginActivity.this, HomePageActivityPp.class);
                    startActivity(intent);
                }
                else if (inputName.equals("rec")){
                    Intent intent = new Intent (LoginActivity.this, HomePageActivityRec.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Username or Password is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

}