package com.kreativemachinez.theguruji;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.maps.model.LatLng;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kreativemachinez.theguruji.utils.GPSTracker;
import com.kreativemachinez.theguruji.utils.General;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MultiplePermissionsListener {

    TextView lat, lng;
    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);

        if(!General.ServiceRunning(this, TrackerService.class)){
            start.setText("Start Service");
        }else{
            start.setText("Tracking Running");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getLoction();
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                )
                .withListener(this)
                .check();
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if(report.areAllPermissionsGranted()){

            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    General.isMyServiceRunning(MainActivity.this);
                    start.setText("Tracking Running");
                }
            });
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

    }
}
