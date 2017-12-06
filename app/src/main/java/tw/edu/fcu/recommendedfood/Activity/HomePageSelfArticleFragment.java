package tw.edu.fcu.recommendedfood.Activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

import tw.edu.fcu.recommendedfood.Adapter.ArticleAdapter;
import tw.edu.fcu.recommendedfood.Data.ArticleBlogData;
import tw.edu.fcu.recommendedfood.Data.ArticleData;
import tw.edu.fcu.recommendedfood.Data.LoginContext;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageSelfArticleFragment extends Fragment {
    ArticleAdapter articleAdapter;
    private HttpCall httpCallPost;
    private HashMap<String, String> params = new HashMap<String, String>();
    static final String ARTICLEDATA = "ARTICLEDATA";
    static final int AdapterCollectionUpdate = 1;
    ListView listView;

    public HomePageSelfArticleFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_home_page_self_article, container, false);

        initView(viewGroup);
        initAdapter();
//        getArticle();
        getFireBase();
        return viewGroup;
    }

    public void initView(ViewGroup viewGroup) {
        listView = (ListView) viewGroup.findViewById(R.id.listview);
    }

    public void initAdapter() {
        articleAdapter = new ArticleAdapter(getActivity());
        listView.setAdapter(articleAdapter);
        listView.setOnItemClickListener(articleBlogOnItemClickListener);
        listView.setOnItemLongClickListener(articleBlogOnItemLongClickListener);
    }

    //TODO 文章
    public void getArticle() {
        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
        httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/article_self_MySQL.php");

        params.put("ArticleData", LoginContext.getLoginContext().getAccount());
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

                    if (jsonArray != null) {
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
                            articleAdapter.addItem(tempArticleData, "hot");
                        }
                        params.clear();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpCallPost);
    }

    private AdapterView.OnItemClickListener articleBlogOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intent = new Intent();
            intent.putExtra(ARTICLEDATA, (ArticleData) articleAdapter.getItem(i));
            intent.setClass(getActivity(), ArticleBlogActivity.class);
            startActivity(intent);
        }
    };

    private AdapterView.OnItemLongClickListener articleBlogOnItemLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("是否刪除 " + articleAdapter.getItem(position).getTitle());
            builder.setCancelable(true);

            builder.setPositiveButton(
                    "確定",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                            Query applesQuery = ref.child("article_title_table").orderByChild("article_id").equalTo(articleAdapter.getItem(position).getArticleId());
                            Query applesQuery2 = ref.child("account_collection_table").child(LoginContext.getLoginContext().getAccount()).
                                    orderByChild("article_id").equalTo(articleAdapter.getItem(position).getArticleId());

                            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });

                            applesQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                            Toast.makeText(getActivity(), "刪除成功", Toast.LENGTH_SHORT).show();
                            articleAdapter.deleteItem(position);
                            dialog.cancel();
                        }
                    });

            builder.setNegativeButton(
                    "取消",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AdapterCollectionUpdate: {
                    articleAdapter.addItem((ArticleData) msg.obj, "hot");
                    break;
                }

            }
        }
    };

    public void getFireBase() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        Log.v("account123", LoginContext.getLoginContext().getAccount());
        myRef.child("account_collection_table").child(LoginContext.getLoginContext().getAccount()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                articleAdapter.clearItem();
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
                ArticleData tempArticleData = new ArticleData();
                tempArticleData.setCount(Integer.parseInt((String) ds.child("click").getValue()));
                tempArticleData.setArticleId((String) ds.child("article_id").getValue());
                tempArticleData.setTitle((String) ds.child("title").getValue());
                tempArticleData.setContent(content);
                tempArticleData.articleBlogData.setDate((String) ds.child("date").getValue());
                tempArticleData.articleBlogData.setAuthor((String) ds.child("account_id").getValue());
                tempArticleData.articleBlogData.setTime((String) ds.child("time").getValue());
                Log.v("article123", (String) ds.child("date").getValue());

                Message msg = new Message();
                msg.what = AdapterCollectionUpdate;
                msg.obj = tempArticleData;
                handler.sendMessage(msg);
            }
        }
    }
}
