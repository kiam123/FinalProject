package tw.edu.fcu.recommendedfood.Activity;

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

import java.util.HashMap;

import tw.edu.fcu.recommendedfood.Adapter.ArticleSeachAdapter;
import tw.edu.fcu.recommendedfood.Data.ArticleData;
import tw.edu.fcu.recommendedfood.Data.OnItemClickLitener;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Widget.HorizontalDividerItemDecoration;

public class ArticleSeachAll extends AppCompatActivity {
    RecyclerView recyclerView;
    ArticleSeachAdapter articleSeachAdapter;
    EditText etSearchArticle;
    private HttpCall httpCallPost;
    private HashMap<String, String> params = new HashMap<String, String>();
    static final String ARTICLEDATA = "ARTICLEDATA";
    Toolbar fragmentToolbar;
    static final int ArticleSearch = 1;

    //    private ImageView imgBackActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_seach_all);

        initToolBar();
        initNework();
        initView();
    }

    public void initToolBar() {
        fragmentToolbar = (Toolbar) findViewById(R.id.fragment_toolbar);
        setSupportActionBar(fragmentToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.listview);
        etSearchArticle = (EditText) findViewById(R.id.et_search_article);
        articleSeachAdapter = new ArticleSeachAdapter();

        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(articleSeachAdapter);
        etSearchArticle.addTextChangedListener(new NewTextWatcher());
        articleSeachAdapter.setOnItemClickLitener(new OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent();
                intent.putExtra(ARTICLEDATA, (ArticleData) articleSeachAdapter.getItem(position));
                intent.setClass(ArticleSeachAll.this, ArticleBlogActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }


    public void initNework() {
        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
        httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/article_search.php");
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
            if(s.toString().trim().equals("")){
                return;
            }
//            params.put("ArticleData", s.toString());
//            httpCallPost.setParams(params);
//
//
//            new HttpRequest() {
//                @Override
//                public void onResponse(String response) {
//                    super.onResponse(response);
//                    params.clear();
//                    try {
//                        JSONArray jsonArray = new JSONArray(response);
//
//                        if (jsonArray != null) {
//                            int click;
//                            String article_id;
//                            String title;
//                            String content = "內容";
//                            String author;
//                            String date;
//                            String time;
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                click = Integer.parseInt(jsonArray.getJSONObject(i).getString("click"));
//                                article_id = jsonArray.getJSONObject(i).getString("article_id");
//                                title = jsonArray.getJSONObject(i).getString("title");
//                                author = jsonArray.getJSONObject(i).getString("account_id");
//                                date = jsonArray.getJSONObject(i).getString("date");
//                                time = jsonArray.getJSONObject(i).getString("time");
//                                Log.v("mmm", click + "");
//                                ArticleData tempArticleData = new ArticleData();
//                                tempArticleData.setCount(click);
//                                tempArticleData.setArticleId(article_id);
//                                tempArticleData.setTitle(title);
//                                tempArticleData.setContent(content);
//                                tempArticleData.articleBlogData.setDate(date);
//                                tempArticleData.articleBlogData.setAuthor(author);
//                                tempArticleData.articleBlogData.setTime(time);
//                                articleSeachAdapter.addItem(tempArticleData);
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }.execute(httpCallPost);

            articleSeachAdapter.removeItem();
            final String searchKey = s.toString();
            Log.v("datadata", searchKey);
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("article_title_table");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    new FireBaseThread(dataSnapshot, searchKey).start();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    class FireBaseThread extends Thread {
        private DataSnapshot dataSnapshot;
        String keyword;

        public FireBaseThread(DataSnapshot dataSnapshot, String keyword) {
            this.dataSnapshot = dataSnapshot;
            this.keyword = keyword;
        }

        @Override
        public void run() {
            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                if ((ds.child("title").getValue() + "").contains(keyword)) {
                    Bundle bundle = new Bundle();

                    bundle.putString("AccountId", ds.child("account_id").getValue() + "");
                    bundle.putString("ArticleId", ds.child("article_id").getValue() + "");
                    bundle.putInt("Click", Integer.parseInt(ds.child("click").getValue() + ""));
                    bundle.putString("Date", ds.child("date").getValue() + "");
                    bundle.putString("Time", ds.child("time").getValue() + "");
                    bundle.putString("Title", ds.child("title").getValue() + "");

                    Message msg = new Message();
                    msg.what = ArticleSearch;
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }

        }
    }


    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ArticleSearch: {
                    int click = msg.getData().getInt("Click");
                    String article_id = msg.getData().getString("ArticleId");
                    String title = msg.getData().getString("Title");
                    String content = "內容";
                    String author = msg.getData().getString("AccountId");
                    String date = msg.getData().getString("Date");
                    String time = msg.getData().getString("Time");

                    ArticleData tempArticleData = new ArticleData();
                    tempArticleData.setCount(click);
                    tempArticleData.setArticleId(article_id);
                    tempArticleData.setTitle(title);
                    tempArticleData.setContent(content);
                    tempArticleData.articleBlogData.setDate(date);
                    tempArticleData.articleBlogData.setAuthor(author);
                    tempArticleData.articleBlogData.setTime(time);
                    articleSeachAdapter.addItem(tempArticleData);
                    break;
                }
            }
        }
    };


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
