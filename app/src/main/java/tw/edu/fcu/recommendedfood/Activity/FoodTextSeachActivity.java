package tw.edu.fcu.recommendedfood.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import tw.edu.fcu.recommendedfood.Adapter.FoodTextSeachAdapter;
import tw.edu.fcu.recommendedfood.Adapter.FoodTextShopAdapter;
import tw.edu.fcu.recommendedfood.Data.ArticleData;
import tw.edu.fcu.recommendedfood.Data.FoodShopData;
import tw.edu.fcu.recommendedfood.Data.FoodTextSearchData;
import tw.edu.fcu.recommendedfood.Data.OnItemClickLitener;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;
import tw.edu.fcu.recommendedfood.Widget.HorizontalDividerItemDecoration;

public class FoodTextSeachActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodTextSeachAdapter foodTextSeachAdapter;
    private EditText etSearchFood;
    private HttpCall httpCallPost;
    private HashMap<String, String> params = new HashMap<String, String>();
    Toolbar fragmentToolbar;
    Dialog shopDialog;
    RecyclerView dlgRecyclerView;
    FoodTextShopAdapter foodTextShopAdapter;
    public String date;
    static final int AdapterFoodUpdate = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_text_seach);


        initNework();
        initView();
        initDialog();
        initAdapter();
    }

    public void initNework() {
        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
        httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/food_connect_MySQL.php");
    }

    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.listview);
        etSearchFood = (EditText) findViewById(R.id.et_search_food);
        fragmentToolbar = (Toolbar) findViewById(R.id.fragment_toolbar);
        setSupportActionBar(fragmentToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        etSearchFood.addTextChangedListener(new NewTextWatcher());
    }

    public void initDialog() {
        shopDialog = new Dialog(this, R.style.MyDialog2);
        shopDialog.setContentView(R.layout.layout_food_dlg_shop);
        dlgRecyclerView = (RecyclerView) shopDialog.findViewById(R.id.listview);
        foodTextShopAdapter = new FoodTextShopAdapter(this);
        dlgRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dlgRecyclerView.setAdapter(foodTextShopAdapter);
    }

    public void initAdapter() {
        foodTextSeachAdapter = new FoodTextSeachAdapter();
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(foodTextSeachAdapter);
        foodTextSeachAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                params.clear();
                params.put("query_string", "5 " + foodTextSeachAdapter.foodTextSearchDatas.get(position).getFoodId());
                httpCallPost.setParams(params);
                new HttpRequest() {
                    @Override
                    public void onResponse(String response) {
                        super.onResponse(response);
                        try {
                            Log.v("abc", response);
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
                shopDialog.show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    public class NewTextWatcher implements TextWatcher {
        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            params.put("query_string", "3 " + s.toString());
            httpCallPost.setParams(params);
            foodTextSeachAdapter.removeItem();
//            getFireBase(s.toString());


            new HttpRequest() {
                @Override
                public void onResponse(String response) {
                    super.onResponse(response);
                    params.clear();
                    try {
                        Log.v("response", response);
                        JSONArray jsonArray = new JSONArray(response);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonData = jsonArray.getJSONObject(i);

                            String foodId = jsonData.getString("food_id");
                            String shopName = jsonData.getString("shop_name");
                            String address = jsonData.getString("address");
                            String phone = jsonData.getString("phone");

                            foodTextSeachAdapter.addItem(new FoodTextSearchData(foodId, "店名：" + shopName, "地址：" + address, "電話：" + phone));
                            Log.v("json", foodTextSeachAdapter.getItemCount() + "");
                        }
                    } catch (Exception e) {
                    }
                }
            }.execute(httpCallPost);
        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AdapterFoodUpdate: {

                    String foodId = msg.getData().getString("FoodId");
                    String shopName = msg.getData().getString("ShopName");
                    String address = msg.getData().getString("Address");
                    String phone = msg.getData().getString("Phone");

                    foodTextSeachAdapter.addItem(new FoodTextSearchData(foodId, "店名：" + shopName, "地址：" + address, "電話：" + phone));

                    break;
                }

            }
        }
    };

    public void getFireBase(String searchId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        myRef.child("food_note_table").child(searchId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                new FireBaseThread(dataSnapshot).start();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    class FireBaseThread extends Thread {
        private DataSnapshot dataSnapshot;

        public FireBaseThread(DataSnapshot dataSnapshot) {
            this.dataSnapshot = dataSnapshot;
        }

        @Override
        public void run() {
            String content = "內容";

            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                if (ds.getKey().equals("article")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("FoodId",ds.child("food_id").getValue()+"");
                    bundle.putString("ShopName",ds.child("shop_name").getValue()+"");
                    bundle.putString("Address",ds.child("address").getValue()+"");
                    bundle.putString("Phone",ds.child("phone").getValue()+"");

                    Message msg = new Message();
                    msg.what = AdapterFoodUpdate;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuBack) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        date = intent.getStringExtra("DATE");
    }
}
