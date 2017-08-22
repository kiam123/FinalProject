package tw.edu.fcu.recommendedfood.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

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
    HttpCall httpCallPost;
    int page;


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
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_article_classification_new, container, false);

        initView(viewGroup);
        initAdapter();
        getArticle();

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

    private AdapterView.OnItemClickListener articleBlogOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent();
            intent.setClass(getActivity(),ArticleBlogActivity.class);
            startActivity(intent);
        }
    };

    //TODO 文章
    public void getArticle() {
        httpCallPost = new HttpCall();
        httpCallPost.setMethodtype(HttpCall.POST);
        httpCallPost.setUrl("http://140.134.26.31/recommended_food_db/article_connect_MySQL.php");//140.134.26.31

        params.put("query_string", page+"");
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

                    int click;
                    String title;
                    String content = "內容";
                    for (int i = 0; i < jsonArray.length(); i++) {
                        title = jsonArray.getJSONObject(i).getString("title");
                        click = Integer.parseInt(jsonArray.getJSONObject(i).getString("click"));
                        articleAdapter.addItem(new ArticleData(click, title, content));
                    }
                    params.clear();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(httpCallPost);
    }
}
