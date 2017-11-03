package tw.edu.fcu.recommendedfood.Activity;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import tw.edu.fcu.recommendedfood.Adapter.ArticleAdapter;
import tw.edu.fcu.recommendedfood.Data.ArticleData;
import tw.edu.fcu.recommendedfood.Data.LoginContext;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HomePageDBHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageCollectArticleFragment extends Fragment {
    ArticleAdapter articleAdapter;
    static final String ARTICLEDATA = "ARTICLEDATA";
    ListView listView;
    HomePageDBHelper homePageDBHelper;

    public HomePageCollectArticleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_homepager_collect, container, false);

        initDataBase();
        initView(viewGroup);
        initAdapter();

        return viewGroup;
    }

    public void initDataBase() {
        homePageDBHelper = new HomePageDBHelper(getActivity());
    }

    public void initView(ViewGroup viewGroup) {
        listView = (ListView) viewGroup.findViewById(R.id.listview);
    }

    public void initAdapter() {
        articleAdapter = new ArticleAdapter(getActivity());
        listView.setAdapter(articleAdapter);
        listView.setOnItemClickListener(articleBlogOnItemClickListener);
    }

    public void getDataBaseArticle() {
        if (!homePageDBHelper.getAccount(LoginContext.getLoginContext().getAccount())) {
            Cursor res = homePageDBHelper.getAllData();
            if (res.getCount() != 0) {

                res.moveToFirst();
                articleAdapter.addItem(new ArticleData(Integer.parseInt(res.getString(3))
                        , res.getString(2), res.getString(4), res.getString(5)),"hot");

                while (res.moveToNext()) {
                    articleAdapter.addItem(new ArticleData(Integer.parseInt(res.getString(2))
                            , res.getString(1), res.getString(3), res.getString(4)),"hot");
                }
            }
        }
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

    @Override
    public void onResume() {
        super.onResume();
        getDataBaseArticle();
    }
}
