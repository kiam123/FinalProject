package tw.edu.fcu.recommendedfood.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.Adapter.ArticleClassificationAdapter;
import tw.edu.fcu.recommendedfood.Data.ArticleClassificationData;
import tw.edu.fcu.recommendedfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends Fragment {
    private DrawerLayout drawerlayout;
    private NavigationView navigationview;
    private boolean openDrawerLayout = false;//侧滑菜单是否打开的标识
    private ImageView imgSearch;
    private ArrayList fragmentArrayList;
    private Fragment mCurrentFrgment;
    private int currentIndex = 0;
    private ListView listviewClassification;
    private ArticleClassificationAdapter articleClassificationAdapter;
    public static final String KEY_CLASSIFICATION = "KEY_CLASSIFICATION";
    public static final String PAGE_NUMBER = "PAGENUMBER";
    private String classification[] = {"全部", "中式", "港式", "西式", "南洋", "韓式", "日式",
            "飲料", "點心", "速食", "食譜", "燒烤"};
    Toolbar toolbar;

    public ArticleFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initClassificationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_article, container, false);
        initView(viewGroup);
        initActionBar(viewGroup);
        initClassificationAdapter();

        return viewGroup;
    }

    //初始化view
    public void initView(ViewGroup viewGroup) {
        drawerlayout = ((DrawerLayout) viewGroup.findViewById(R.id.drawerlayout));//侧滑的根布局
        navigationview = ((NavigationView) viewGroup.findViewById(R.id.navigationview));//侧滑菜单布局
        imgSearch = (ImageView) viewGroup.findViewById(R.id.img_search);
        listviewClassification = (ListView) viewGroup.findViewById(R.id.listviewClassification);


        drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//函数来关闭手势滑动
        drawerlayout.addDrawerListener(drawerlayoutListerner);
        imgSearch.setOnClickListener(imgSearchOnClickListener);
    }

    public void initActionBar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.fragment_toolbar);

        setHasOptionsMenu(true);//fragment在需要使用這個method來讓onCreateOptionsMenu生效
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);//隱藏action bar title
        toolbar.setNavigationIcon(R.drawable.ic_list_white_36dp);
        toolbar.setNavigationOnClickListener(toggleOnClickListener);

//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);//設定back key
    }

    private View.OnClickListener toggleOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (openDrawerLayout) {//如果是打开的,就关闭侧滑菜单
                drawerlayout.closeDrawers();
            } else {
                drawerlayout.openDrawer(Gravity.LEFT);//布局中设置从左边打开,这里也要设置为左边打开
            }
        }
    };

    public void initClassificationAdapter() {
        articleClassificationAdapter = new ArticleClassificationAdapter(getActivity());
        for (int i = 0; i < classification.length; i++) {
            articleClassificationAdapter.addItem(new ArticleClassificationData(classification[i]));
        }

        listviewClassification.setAdapter(articleClassificationAdapter);
        listviewClassification.setOnItemClickListener(classificationAdapterOnItemClickListener);

        listviewClassification.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int visibleThreshold = 5;
            private int currentPage = 0;
            private int previousTotal = 0;
            private boolean loading = true;

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                Log.v("total",listviewClassification.getCount()+"");
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                        currentPage++;

                    }
                }
                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    // load more items
                    loading = true;
//                    Toast.makeText(getActivity(),"asd",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.v("total",listviewClassification.getCount()+"");
//                Toast.makeText(getActivity(),"asd",Toast.LENGTH_LONG).show();
            }

        });
    }

    private AdapterView.OnItemClickListener classificationAdapterOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            loadClassification(position);

        }
    };

    public void loadClassification(int position) {
        changeTab(position);
        openDrawerLayout = false;
        drawerlayout.closeDrawers();
    }

    private void initClassificationFragment() {
        fragmentArrayList = new ArrayList<Fragment>(12);
        fragmentArrayList.add(ArticleClassificationFragment.create(0));
        fragmentArrayList.add(ArticleClassificationFragment.create(1));
        fragmentArrayList.add(ArticleClassificationFragment.create(2));
        fragmentArrayList.add(ArticleClassificationFragment.create(3));
        fragmentArrayList.add(ArticleClassificationFragment.create(4));
        fragmentArrayList.add(ArticleClassificationFragment.create(5));
        fragmentArrayList.add(ArticleClassificationFragment.create(6));
        fragmentArrayList.add(ArticleClassificationFragment.create(7));
        fragmentArrayList.add(ArticleClassificationFragment.create(8));
        fragmentArrayList.add(ArticleClassificationFragment.create(9));
        fragmentArrayList.add(ArticleClassificationFragment.create(10));
        fragmentArrayList.add(ArticleClassificationFragment.create(11));
//        fragmentArrayList.add(new ArticleClassificationFragment());
//        fragmentArrayList.add(new ArticleClassificationFragment());
//        fragmentArrayList.add(new ArticleClassificationFragment());
//        fragmentArrayList.add(new ArticleClassificationFragment());
//        fragmentArrayList.add(new ArticleClassificationFragment());
//        fragmentArrayList.add(new ArticleClassificationFragment());
//        fragmentArrayList.add(new ArticleClassificationFragment());
//        fragmentArrayList.add(new ArticleClassificationFragment());
//        fragmentArrayList.add(new ArticleClassificationFragment());
//        fragmentArrayList.add(new ArticleClassificationFragment());
//        fragmentArrayList.add(new ArticleClassificationFragment());
//        fragmentArrayList.add(new ArticleClassificationFragment());

        changeTab(0);
    }

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
            bundle.putString(KEY_CLASSIFICATION, classification[currentIndex]);
            bundle.putInt(PAGE_NUMBER, currentIndex);
            Log.v("papapa",(currentIndex)+"");
            fragment.setArguments(bundle);
            ft.replace(R.id.fragment, fragment, fragment.getClass().getName());
//            ft.addToBackStack(null);
        } else {
            ft.show(fragment);
        }
        ft.commit();
    }

    private DrawerLayout.DrawerListener drawerlayoutListerner = new DrawerLayout.SimpleDrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            //slideOffset 变化范围0~1

            View contentView = drawerlayout.getChildAt(0);//获得content
            View leftView = drawerView;

            contentView.setTranslationX(leftView.getMeasuredWidth() * slideOffset);//平移
        }

        //当侧滑菜单关闭
        @Override
        public void onDrawerClosed(View drawerView) {
            super.onDrawerClosed(drawerView);
            openDrawerLayout = false;
        }

        //当侧滑菜单打开
        @Override
        public void onDrawerOpened(View drawerView) {
            super.onDrawerOpened(drawerView);
            openDrawerLayout = true;
        }
    };

    private View.OnClickListener imgSearchOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ArticleSeachAll.class);
            startActivity(intent);
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menuEdit) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), ArticleCommandActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
