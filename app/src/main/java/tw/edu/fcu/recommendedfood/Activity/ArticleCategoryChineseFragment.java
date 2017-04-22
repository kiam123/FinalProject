package tw.edu.fcu.recommendedfood.Activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tw.edu.fcu.recommendedfood.R;

//TODO 不確定是否需要用到這個類別
public class ArticleCategoryChineseFragment extends Fragment {
    private ArrayList fragmentArrayList;
    private Fragment mCurrentFrgment = null;
    private List<String> titles;
    private TabLayout tabNotification;
    int currentIndex;
    private TextView txtClassificationSet;
    private Bundle bundle;

    public ArticleCategoryChineseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragment();
        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_article_category_chinese, container, false);

        initView(viewGroup);
        initNotificationTab(viewGroup);


        return viewGroup;
    }

    public void initView(ViewGroup viewGroup) {
        txtClassificationSet = (TextView) viewGroup.findViewById(R.id.txt_classification_set);

        txtClassificationSet.setText(bundle.getString(ArticleFragment.KEY_CLASSIFICATION));
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

    private void initFragment(){
        fragmentArrayList = new ArrayList<Fragment>(2);
        fragmentArrayList.add(new ArticleClassificationHotFragment());
        fragmentArrayList.add(new ArticleClassificationNewFragment());

        changeTab(0);
    }

    public void changeTab(int index) {
        currentIndex = index;

        String []title = {"ch_hot","ch_new"};
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (mCurrentFrgment != null) {
            ft.hide(mCurrentFrgment);
        }


        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
//        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(fragmentArrayList.get(currentIndex).getClass().getName());
        Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(title[currentIndex]);
        if (fragment == null) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = (Fragment) fragmentArrayList.get(index);
        }
        mCurrentFrgment = fragment;

        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            ft.add(R.id.chinese_fragment, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }
}
