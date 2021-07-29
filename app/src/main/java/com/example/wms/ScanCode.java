package com.example.wms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;


import com.example.wms.adapters.ViewPickingListDetailsAdapter;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCode extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    int MY_PERMISSION_REQUEST_CAMERA = 0;
    ZXingScannerView scannerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerview = new ZXingScannerView(this);
        setContentView(scannerview);


    }

/*    @Override
    public void onActivityResult(int reqCode, int resCode, Intent intent) {
        if (requestCode == 0) {
            if (resCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText("Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();
            }
        }*/

    @Override
    public void handleResult(Result result) {

        Intent mainActivity = new Intent(this,IndividualPickingList.class);
        mainActivity.putExtra("tvresult1",result.getText());
        mainActivity.putExtra("clickPosition", getIntent().getExtras().getInt("clickPosition"));
        setResult(Activity.RESULT_OK, mainActivity);
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerview.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSION_REQUEST_CAMERA);
        }
        scannerview.setResultHandler(this);
        scannerview.startCamera();
    }

}