package tw.edu.fcu.recommendedfood.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import tw.edu.fcu.recommendedfood.Adapter.FoodTextSeachAdapter;
import tw.edu.fcu.recommendedfood.Adapter.FoodTextShopAdapter;
import tw.edu.fcu.recommendedfood.Data.FoodShopData;
import tw.edu.fcu.recommendedfood.Data.FoodTextSearchData;
import tw.edu.fcu.recommendedfood.Data.OnItemClickLitener;
import tw.edu.fcu.recommendedfood.Data.OnLocationChangeListener;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;
import tw.edu.fcu.recommendedfood.Server.LocationService;
import tw.edu.fcu.recommendedfood.Widget.HorizontalDividerItemDecoration;

public class FoodGpsDialogShopActivity extends AppCompatActivity {
    String location;
    private HttpCall httpCallPost;
    private HashMap<String, String> params = new HashMap<String, String>();
    RecyclerView dlgRecyclerView;
    FoodTextShopAdapter foodTextShopAdapter;
    public String date="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_gps_shop);

        getIntentData();
        initNework();
        initView();
        initAdapter();
    }

    public void initNework() {
        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
        httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/food_connect_MySQL.php");
    }

    public void initView() {
        dlgRecyclerView = (RecyclerView) findViewById(R.id.listview);
    }

    public void initAdapter() {
        foodTextShopAdapter = new FoodTextShopAdapter(this);
        dlgRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dlgRecyclerView.setAdapter(foodTextShopAdapter);
        params.clear();
        params.put("query_string", "7 " + location);
        httpCallPost.setParams(params);
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                try {
                    Log.v("aabb", response);
                    FoodTextSearchData foodTextSearchData = new FoodTextSearchData();
                    ArrayList<FoodShopData> foodShopDatas = new ArrayList<FoodShopData>();
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonData = jsonArray.getJSONObject(0);

                    foodTextSearchData.setShopName(jsonData.getString("shop_name"));
                    String food[] = jsonData.getString("food").split(",");
                    String price[] = jsonData.getString("price").split(",");
                    String calorie[] = jsonData.getString("calorie").split(",");
                    String plasticizer[] = jsonData.getString("plasticizer").split(",");
//                            String b[] = jsonData.getString("b").split(",");
//                            String c[] = jsonData.getString("b").split(",");
                    foodTextSearchData.setAddress(jsonData.getString("address"));

                    Log.v("abc",food.length+"");
                    Log.v("abc",jsonData.getString("shop_name")+"");
                    for (int i = 0; i < food.length; i++) {
                        foodShopDatas.add(new FoodShopData(food[i], price[i], calorie[i], plasticizer[i], "1"));
                    }
                    foodTextSearchData.setFoodShopData(foodShopDatas);
                    foodTextShopAdapter.setFoodData(foodTextSearchData);
                } catch (Exception e) {
                }
            }
        }.execute(httpCallPost);
    }

    public void getIntentData(){
        Intent intent = getIntent();
        location = intent.getStringExtra("SHOP");
        Log.v("aabb", location);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        date = intent.getStringExtra("DATE");
        Log.v("DATE",date);
    }
}
