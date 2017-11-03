package tw.edu.fcu.recommendedfood.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import tw.edu.fcu.recommendedfood.Adapter.WaitingEatAdapter;
import tw.edu.fcu.recommendedfood.Data.FoodTextSearchData;
import tw.edu.fcu.recommendedfood.Data.WaitingEatData;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;
import tw.edu.fcu.recommendedfood.Server.WaitingEatDBItem;

public class WaitingEatSearchActivity extends AppCompatActivity {
    Toolbar fragmentToolbar;
    private WaitingEatAdapter waitingEatAdapter;
    private WaitingEatDBItem waitingEatDBItem;
    private List<WaitingEatData> waitingEatDatas;
    EditText etSearchNote;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_eat_search);

        initView();
        initAdapter();
    }

    public void initView(){
        fragmentToolbar = (Toolbar) findViewById(R.id.fragment_toolbar);
        listView = (ListView) findViewById(R.id.item_list);
        etSearchNote = (EditText) findViewById(R.id.et_search_food);
        etSearchNote.addTextChangedListener(new NewTextWatcher());

        setSupportActionBar(fragmentToolbar);
    }

    public void initAdapter() {
        waitingEatDBItem = new WaitingEatDBItem(this);


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
            Log.v("aabb",s.toString());
            waitingEatDatas = waitingEatDBItem.getData(s.toString());
//            // 建立自定Adapter物件
            waitingEatAdapter = new WaitingEatAdapter(WaitingEatSearchActivity.this, R.layout.layout_waiting_eat_singleitem, waitingEatDatas);
            listView.setAdapter(waitingEatAdapter);
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


}
