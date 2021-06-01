package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText eName;
    private EditText ePassword;
    private Button eLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eName = findViewById(R.id.eTName);
        ePassword = findViewById(R.id.eTPassword);
        eLogin = findViewById(R.id.btnLogin);

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName = eName.getText().toString();
                String inputPassword = ePassword.getText().toString();

                if (inputName.isEmpty() || inputPassword.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username or Password is empty", Toast.LENGTH_SHORT).show();
                }
                else if (inputName.equals("wm")){
                    Intent intent = new Intent(LoginActivity.this, HomePageActivityWM.class);
                    startActivity(intent);
                }
                else if (inputName.equals("sup")){
                    Intent intent = new Intent (LoginActivity.this, HomePageActivitySup.class);
                    startActivity(intent);
                }
            }
        });
    }
}