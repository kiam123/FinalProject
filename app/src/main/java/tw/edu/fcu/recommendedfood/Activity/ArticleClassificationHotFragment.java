package tw.edu.fcu.recommendedfood.Activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import tw.edu.fcu.recommendedfood.Adapter.ArticleAdapter;
import tw.edu.fcu.recommendedfood.Data.ArticleData;
import tw.edu.fcu.recommendedfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleClassificationHotFragment extends Fragment {
    ArticleAdapter articleAdapter;
    ListView listView;

    public ArticleClassificationHotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_article_classification_hot, container, false);

        initView(viewGroup);
        initAdapter();

        return viewGroup;
    }

    public void initView(ViewGroup viewGroup){
        listView = (ListView) viewGroup.findViewById(R.id.listviewClassification);
    }

    public void initAdapter(){
        articleAdapter = new ArticleAdapter(getActivity());
        listView.setAdapter(articleAdapter);

        articleAdapter.addItem(new ArticleData("1", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("2", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("3", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("4", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("5", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("6", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("7", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("8", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("9", "標題", "內容"));
        articleAdapter.addItem(new ArticleData("10", "標題", "內容"));
    }
}
