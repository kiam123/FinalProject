package tw.edu.fcu.recommendedfood.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import tw.edu.fcu.recommendedfood.Adapter.ArticleAdapter;
import tw.edu.fcu.recommendedfood.Data.ArticleData;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleClassificationNewFragment extends Fragment {
    ArticleAdapter articleAdapter;
    ListView listView;
    HashMap<String, String> params = new HashMap<String, String>();
    static final String ARTICLEDATA = "ARTICLEDATA";
    public static final int AdapterUpdate = 1;
    HttpCall httpCallPost;
    int page;

    public static ArticleClassificationNewFragment create(int pageNumber) {
        ArticleClassificationNewFragment fragment = new ArticleClassificationNewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ArticleClassificationFragment.PAGE_NUMBER, pageNumber);
        fragment.setArguments(bundle);

        return fragment;
    }

    public ArticleClassificationNewFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            page = getArguments().getInt(ArticleClassificationFragment.PAGE_NUMBER);
            Log.v("PAGE_NUMBER",page+"");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_article_classification_hot, container, false);

        initView(viewGroup);
        initAdapter();
        getFireBase();
//        getArticle();

        return viewGroup;
    }

    public void initView(ViewGroup viewGroup){
        listView = (ListView) viewGroup.findViewById(R.id.listviewClassification);
    }

    public void initAdapter(){
        articleAdapter = new ArticleAdapter(getActivity());
        listView.setAdapter(articleAdapter);
        listView.setOnItemClickListener(articleBlogOnItemClickListener);

//        articleAdapter.addItem(new ArticleData("1", "標題", "內容"));
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AdapterUpdate: {
                    articleAdapter.addItem((ArticleData) msg.obj, "new");
                }
            }
        }
    };

    private AdapterView.OnItemClickListener articleBlogOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent();
            intent.putExtra(ARTICLEDATA,(ArticleData)articleAdapter.getItem(i));
            intent.setClass(getActivity(),ArticleBlogActivity.class);
            startActivity(intent);
        }
    };

    //TODO 文章
    public void getArticle() {
        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
        httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/article_new_MySQL.php");//140.134.26.31

        params.put("query_string", page+"");
        Log.v("classofication",""+page);
        httpCallPost.setParams(params);
        postToServer(httpCallPost);
    }

    public void postToServer(HttpCall httpCallPost) {
        new HttpRequest() {
            @Override
            public void onResponse(String response) {
                super.onResponse(response);
                final String result = response;
                Log.v("response", response);
                try {
                    JSONArray jsonArray = new JSONArray(result);

                    if(jsonArray != null) {
                        int click;
                        String article_id;
                        String title;
                        String content = "內容";
                        String author;
                        String date;
                        String time;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            click = Integer.parseInt(jsonArray.getJSONObject(i).getString("click"));
                            article_id = jsonArray.getJSONObject(i).getString("article_id");
                            title = jsonArray.getJSONObject(i).getString("title");
                            author = jsonArray.getJSONObject(i).getString("account_id");
                            date = jsonArray.getJSONObject(i).getString("date");
                            time = jsonArray.getJSONObject(i).getString("time");
                            ArticleData tempArticleData = new ArticleData();
                            tempArticleData.setCount(click);
                            tempArticleData.setArticleId(article_id);
                            tempArticleData.setTitle(title);
                            tempArticleData.setContent(content);
                            tempArticleData.articleBlogData.setDate(date);
                            tempArticleData.articleBlogData.setAuthor(author);
                            tempArticleData.articleBlogData.setTime(time);
                            articleAdapter.addItem(tempArticleData,"new");
                        }
                        params.clear();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpCallPost);
    }

    public void getFireBase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        myRef.child("article_title_table").addValueEventListener(new ValueEventListener() {
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
                DataSnapshot dsClick = ds.child("click");
                DataSnapshot dsArticleId = ds.child("article_id");
                DataSnapshot dsTitle = ds.child("title");
                DataSnapshot dsAuthor = ds.child("account_id");
                DataSnapshot dsDate = ds.child("date");
                DataSnapshot dsTime = ds.child("time");

//                Log.v("article123", ds.getKey());
                ArticleData tempArticleData = new ArticleData();
                tempArticleData.setCount(Integer.parseInt((String) dsClick.getValue()));
                tempArticleData.setArticleId((String) dsArticleId.getValue());
                tempArticleData.setTitle((String) dsTitle.getValue());
                tempArticleData.setContent(content);
                tempArticleData.articleBlogData.setDate((String) dsDate.getValue());
                tempArticleData.articleBlogData.setAuthor((String) dsAuthor.getValue());
                tempArticleData.articleBlogData.setTime((String) dsTime.getValue());


                Message msg = new Message();
                msg.what = AdapterUpdate;
                msg.obj = tempArticleData;
                handler.sendMessage(msg);
            }
        }
    }
}
