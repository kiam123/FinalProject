package tw.edu.fcu.recommendedfood.Server;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.Date;

import tw.edu.fcu.recommendedfood.Activity.FoodGpsSearchActivity;
import tw.edu.fcu.recommendedfood.Data.OnLocationChangeListener;


public class LocationService extends Service implements
        ConnectionCallbacks, OnConnectionFailedListener {

    protected static final String TAG = "Service-location";

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mCurrentLocation;
    private String mLastUpdateTime;

    private Boolean mRequestingLocationUpdates;
    private ServiceLocationListener serviceLocationListener;

    protected class ServiceLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            mCurrentLocation = location;
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
            Intent broadcasetIntent = new Intent();
            broadcasetIntent.setAction(FoodGpsSearchActivity.Map_ACTION);
            broadcasetIntent.putExtra("LAT",mCurrentLocation.getLatitude());
            broadcasetIntent.putExtra("LNG",mCurrentLocation.getLongitude());
            sendBroadcast(broadcasetIntent);
            Log.d(TAG, String.valueOf(String.valueOf(mCurrentLocation.getLatitude()) + ", " + mCurrentLocation.getLongitude()));

        }
    }// LocationListener class end

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();
        buildGoogleApiClient();
        mRequestingLocationUpdates = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int starId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, starId);

        serviceLocationListener = new ServiceLocationListener();
        mGoogleApiClient.connect();
        if (!mRequestingLocationUpdates) {
            mRequestingLocationUpdates = true;
            if (!mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            } else {
                startLocationUpdates();
            }

        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);//設定上傳時間為10秒
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);//問setInterval與setFastestInterval的差別
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, serviceLocationListener);
    }


    @Override
    public void onConnected(Bundle bundle) {
        if (mCurrentLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);//傳回當前的位置
            mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        }

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {//問 爲什麽暫停的時候還繼續connect
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, serviceLocationListener);//取消更新目前狀況
        Log.d(TAG, "onDestroy");
    }

}
