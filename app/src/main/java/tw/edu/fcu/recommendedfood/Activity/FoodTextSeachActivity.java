package tw.edu.fcu.recommendedfood.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import tw.edu.fcu.recommendedfood.Adapter.FoodTextSeachAdapter;
import tw.edu.fcu.recommendedfood.Data.FoodTextSearchData;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;

public class FoodTextSeachActivity extends AppCompatActivity {
    private ListView listView;
    private FoodTextSeachAdapter foodTextSeachAdapter;
    private EditText etSearchFood;
    private Handler handler;
    private HttpCall httpCallPost;
    private HashMap<String,String> params = new HashMap<String,String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_text_seach);

        initNework();
        initView();
        initAdapter();
    }

    public void initNework(){
        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
        httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/food_connect_MySQL.php");
    }

    public void initView(){
        listView = (ListView) findViewById(R.id.listview);
        etSearchFood = (EditText) findViewById(R.id.et_search_food);

        etSearchFood.addTextChangedListener(new NewTextWatcher());
    }

    public void initAdapter(){
        foodTextSeachAdapter = new FoodTextSeachAdapter(FoodTextSeachActivity.this);
        listView.setAdapter(foodTextSeachAdapter);


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

            new HttpRequest() {
                @Override
                public void onResponse(String response) {
                    super.onResponse(response);
                    params.clear();
                    try {
                        Log.v("response",response);
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            final JSONObject jsonData = jsonArray.getJSONObject(i);

                            final String name = jsonData.getString("food_name");
                            final String calorie = jsonData.getString("calorie");
                            foodTextSeachAdapter.addItem(new FoodTextSearchData(name, calorie));
                        }
                    } catch (Exception e) {
                    }
                }
            }.execute(httpCallPost);
        }
    }

    public void imgBackAtivity(View view){
        finish();
    }
}
