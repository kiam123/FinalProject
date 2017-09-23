package tw.edu.fcu.recommendedfood.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.HttpCall;
import tw.edu.fcu.recommendedfood.Server.HttpRequest;


public class ArticleClassificationFragment extends Fragment {
    private ArrayList fragmentArrayList;
    private Fragment mCurrentFrgment;
    private List<String> titles;
    private TabLayout tabNotification;
    int currentIndex;
    private TextView txtClassificationSet;
    HashMap<String, String> params = new HashMap<String, String>();
    HttpCall httpCallPost;
    public static final String KEY_CLASSIFICATION = "KEY_CLASSIFICATION";
    public static final String PAGE_NUMBER = "PAGENUMBER";
    int page;

    public static Fragment create(int pageNumber) {
        ArticleClassificationFragment fragment = new ArticleClassificationFragment();

        return fragment;
    }

    public ArticleClassificationFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_article_classification, container, false);

        initView(viewGroup);
        initNotificationTab(viewGroup);
        initFragment();

        return viewGroup;
    }

    public void initView(ViewGroup viewGroup) {
        txtClassificationSet = (TextView) viewGroup.findViewById(R.id.txt_classification_set);

        if (getArguments() != null) {
            txtClassificationSet.setText(getArguments().getString(ArticleFragment.KEY_CLASSIFICATION));
            page = getArguments().getInt(ArticleFragment.PAGE_NUMBER);
        }
    }

    public void initNotificationTab(ViewGroup viewGroup) {
        titles = new ArrayList<>();//因為imageview不能比較，所以就用textview的方法來比較
        titles.add("熱門");
        titles.add("最新");
        tabNotification = (TabLayout) viewGroup.findViewById(R.id.tabs_notification);

        tabNotification.addTab(tabNotification.newTab().setText(titles.get(0)));
        tabNotification.addTab(tabNotification.newTab().setText(titles.get(1)));
        tabNotification.addOnTabSelectedListener(tabNotificationOnTabSelectedListener);
    }

    private void initFragment() {
        fragmentArrayList = new ArrayList<Fragment>(2);
        fragmentArrayList.add(new ArticleClassificationHotFragment());
        fragmentArrayList.add(new ArticleClassificationNewFragment());

        changeTab(0);
    }

    private TabLayout.OnTabSelectedListener tabNotificationOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            changeTab(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    public void changeTab(int index) {
        currentIndex = index;

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (mCurrentFrgment != null) {
            ft.hide(mCurrentFrgment);
        }

        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(fragmentArrayList.get(currentIndex).getClass().getName());
        if (mCurrentFrgment != null) {
            Log.v("tag1", "" + mCurrentFrgment.toString());
        }
        Log.v("tag2", "" + fragmentArrayList.get(currentIndex).getClass().getName());

        if (fragment == null) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = (Fragment) fragmentArrayList.get(index);
        }
        mCurrentFrgment = fragment;
        if (mCurrentFrgment != null) {
            Log.v("tag3", "" + mCurrentFrgment.toString());

            if (fragment != null)
                Log.v("tag4", "" + fragment.toString());
        }
        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putInt(PAGE_NUMBER, page);
            fragment.setArguments(bundle);
            ft.replace(R.id.classification_fragment, fragment, fragment.getClass().getName());
//            ft.addToBackStack(null);
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }
}
