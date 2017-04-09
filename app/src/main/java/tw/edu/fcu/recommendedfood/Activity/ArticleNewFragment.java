package tw.edu.fcu.recommendedfood.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tw.edu.fcu.recommendedfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleNewFragment extends Fragment {


    public ArticleNewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_article_new, container, false);

        initView(viewGroup);

        return viewGroup;
    }

    public void initView(ViewGroup viewGroup){

    }
}
