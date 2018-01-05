package com.kreativemachinez.theguruji;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.blankj.utilcode.util.LogUtils;
import com.google.android.gms.maps.model.LatLng;
import com.kreativemachinez.theguruji.utils.GPSTracker;

public class TrackerService extends Service {

    double prevLat = 0, prevLng = 0;

    public TrackerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        if()
        getLoction();
        return START_STICKY;
    }



    private void getLoction(){
        LogUtils.e("Location get");
        GPSTracker gpsTracker = new GPSTracker(this, new GPSTracker.OnLocationChange() {
            @Override
            public void locationChanged(LatLng location) {
                Log.e("location", location.latitude+" X "+location.longitude);
                if(location.latitude == 0 || location.longitude == 0)
                    return;
                if(prevLat == location.latitude || prevLng == location.longitude)
                    return;
                prevLat = location.latitude;
                prevLng = location.longitude;
                AndroidNetworking.get("http://eras-tech.in/acharyaji/api/")
                        .addQueryParameter("lat", String.valueOf(location.latitude))
                        .addQueryParameter("lng", String.valueOf(location.longitude))
                        .addQueryParameter("key", "gjjsik3434hg3hn35v")
                        .build()
                        .getAsString(new StringRequestListener() {
                            @Override
                            public void onResponse(String response) {
                                LogUtils.e("response generated -> "+response);
                            }

                            @Override
                            public void onError(ANError anError) {

                            }
                        });
            }
        });
        gpsTracker.getLocation();
    }
}
