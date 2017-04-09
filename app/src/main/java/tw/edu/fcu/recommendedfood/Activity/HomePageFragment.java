package tw.edu.fcu.recommendedfood.Activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment {
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private String[] mTitles = {"收藏", "文章", "朋友"};
    private TextView txtItem;

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_homepage, container, false);

        initFragment();
        initView(viewGroup);

        return viewGroup;
    }

    //view初始化
    public void initView(ViewGroup viewGroup) {//不一定要用viewGroup，也可以直接使用getView()
        viewPager = (ViewPager) viewGroup.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) viewGroup.findViewById(R.id.tabs);
        txtItem = (TextView) viewGroup.findViewById(R.id.txt_item);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getChildFragmentManager(), fragmentArrayList, mTitles));
        mTabLayout.setupWithViewPager(viewPager);

        initTabLayoutEvent();
    }

    private void initTabLayoutEvent() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    txtItem.setText("所有收藏");
                } else if (tab.getPosition() == 1) {
                    txtItem.setText("所有文章");
                } else {
                    txtItem.setText("所有朋友");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    //初始化每個頁面的fragment（部落格，記錄食物，待吃，個人主頁）
    private void initFragment() {
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new HomePageCollectArticleFragment());//收藏文章
        fragmentArrayList.add(new HomePageSelfArticleFragment());//自己的文章
        fragmentArrayList.add(new HomePageFriendsFragment());//朋友
    }

    //設定viewpager的adapter
    class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
        String[] mTitles;

        public SimpleFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList, String[] mTitles) {
            super(fm);
            this.fragmentArrayList = fragmentArrayList;
            this.mTitles = mTitles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
