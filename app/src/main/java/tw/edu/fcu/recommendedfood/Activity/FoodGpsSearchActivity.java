package tw.edu.fcu.recommendedfood.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

import tw.edu.fcu.recommendedfood.Data.OnLocationChangeListener;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;
import tw.edu.fcu.recommendedfood.Server.LocationService;

public class FoodGpsSearchActivity extends FragmentActivity implements OnMapReadyCallback, OnLocationChangeListener/*,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener*/ {
    private GoogleMap mMap;
    public static Broadcast broadcast;
    public static final String Map_ACTION = "Map_ACTION";
    private HttpCall httpCallPost;
    private HashMap<String, String> params = new HashMap<String, String>();
    HashMap<String, String> hashShopDetail = new HashMap<>();
    ArrayList<LatLng> myLocation = new ArrayList<>();
    boolean isFirst = true;
    String date="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_gps_search);

        initNework();
        initView();
        initBroadcastReceiver();
        Intent intent = new Intent(FoodGpsSearchActivity.this, LocationService.class);
        startService(intent);
    }

    public void initNework() {
        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
        httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/food_connect_MySQL.php");
    }

    public void initView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();
        setMapAction();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    public void setMapAction() {
        mMap.getUiSettings().setZoomControlsEnabled(true);//enable zoom controls
        mMap.getUiSettings().setAllGesturesEnabled(true);//enable all gestures
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                return false;
            }
        });
    }

    public void initBroadcastReceiver() {
        broadcast = new Broadcast(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Map_ACTION);
        registerReceiver(broadcast, intentFilter);
    }

    @Override
    public void getLocation(LatLng latLng) {
        Log.v("abc", latLng.latitude + "," + latLng.longitude);
        myLocation.add(latLng);

        postServer();
    }


    public void postServer() {
        params.clear();
        params.put("query_string", "6 " + myLocation.get(0).latitude + "," + myLocation.get(0).longitude);
        httpCallPost.setParams(params);
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                try {
                    Log.v("abc", response);
                    mMap.clear();
                    addMapMarker(myLocation.get(0), "目前位置", myLocation.get(0) + "");
                    if(isFirst) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation.get(0), 18));
                        isFirst = false;
                    }
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        String shopName = jsonArray.getJSONObject(i).getString("shop_name");
                        String food[] = jsonArray.getJSONObject(i).getString("food").split(",");
                        String latlng[] = jsonArray.getJSONObject(i).getString("latlng").split(",");
                        LatLng location = new LatLng(Double.parseDouble(latlng[0]), Double.parseDouble(latlng[1]));
                        addMapMarker(location, shopName, location + "");
                        for (int j = 0; j < food.length; j++) {
                            Log.v("lat",shopName+"  "+latlng[0] + "," +latlng[1]+"");

                            hashShopDetail.put(latlng[0] + "," +latlng[1], food[j]);
                        }
                    }

                } catch (Exception e) {
                }
            }
        }.execute(httpCallPost);
    }

    public void addMapMarker(LatLng latLng, String title, String snipper) {
        MarkerOptions options = new MarkerOptions(); // 建立標記選項的實例
        options.position(latLng);// 標記經緯度
        options.title(title); // Info-Window標題
        options.snippet(snipper); // Info-Window標記摘要
        options.anchor(0.5f, 1.0f); // 錨點
        options.draggable(false); // 是否可以拖曳標記?
        mMap.addMarker(options);

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                String latlngPosition = marker.getPosition().toString();
                latlngPosition = latlngPosition.substring(10, latlngPosition.length() - 1);

                if (hashShopDetail.get(latlngPosition) != null) {
                    Intent intent = new Intent();
                    intent.putExtra("SHOP",latlngPosition);
                    intent.putExtra("DATE",date);

                    intent.setClass(FoodGpsSearchActivity.this, FoodGpsDialogShopActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    public static class Broadcast extends BroadcastReceiver {
        OnLocationChangeListener onLocationChangeListener;

        public Broadcast() {
        }

        public Broadcast(OnLocationChangeListener onLocationChangeListener) {
            this.onLocationChangeListener = onLocationChangeListener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Map_ACTION)) {
                double lat, lng;
                lat = intent.getDoubleExtra("LAT", 0);
                lng = intent.getDoubleExtra("LNG", 0);
                onLocationChangeListener.getLocation(new LatLng(lat, lng));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        date = intent.getStringExtra("DATE");
        Log.v("DATE",date);
    }
}
