package tw.edu.fcu.recommendedfood.Activity;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tw.edu.fcu.recommendedfood.R;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ArrayList<Fragment> fragmentArrayList;
    private List<String> titles;
    private TabLayout mTabLayout;

    final Integer[] ICONS = new Integer[]{
            R.drawable.ic_home_white_18dp,
            R.drawable.ic_event_white_18dp,
            R.drawable.ic_favorite_white_18dp,
            R.drawable.ic_account_circle_white_18dp
    };

    final Integer[] ICONS2 = new Integer[]{
            R.drawable.ic_home_black_18dp,
            R.drawable.ic_event_black_18dp,
            R.drawable.ic_favorite_black_18dp,
            R.drawable.ic_account_circle_black_18dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        initView();
    }

    //初始化每個view
    public void initView(){
        titles = new ArrayList<>();//因為imageview不能比較，所以就用textview的方法來比較
        titles.add("One");
        titles.add("Two");
        titles.add("Three");
        titles.add("Four");

        viewPager = (ViewPager) this.findViewById(R.id.viewpager);
        mTabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(),fragmentArrayList,this));
        mTabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        initTabLayoutEvent();
    }

    //初始化tab
    private void setupTabIcons() {
        mTabLayout.getTabAt(0).setCustomView(getTabView(0));
        mTabLayout.getTabAt(1).setCustomView(getTabView(1));
        mTabLayout.getTabAt(2).setCustomView(getTabView(2));
        mTabLayout.getTabAt(3).setCustomView(getTabView(3));
    }

    //自定義tab
    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_main_tab, null);//自定義layout設定tab（部落格，記錄，待吃，個人）裡面的設計
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setText(titles.get(position));
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        Picasso.with(this).load(ICONS2[position]).into(img_title);

        if (position == 0) {
            Picasso.with(this).load(ICONS[position]).into(img_title);
        } else {
            Picasso.with(this).load(ICONS2[position]).into(img_title);
        }
        return view;
    }

    //設定滑動另一頁後的事件
    private void initTabLayoutEvent() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabSelect(tab);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabNormal(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //選中以後的圖，設白色底
    private void changeTabSelect(TabLayout.Tab tab) {
        View view = tab.getCustomView();//使用自定義view
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);

        if (txt_title.getText().toString().equals("One")) {
            Picasso.with(this).load(ICONS[0]).into(img_title);
            viewPager.setCurrentItem(0);
        } else if (txt_title.getText().toString().equals("Two")) {
            Picasso.with(this).load(ICONS[1]).into(img_title);
            viewPager.setCurrentItem(1);
        } else if (txt_title.getText().toString().equals("Three")){
            Picasso.with(this).load(ICONS[2]).into(img_title);
            viewPager.setCurrentItem(2);
        }        else {
            Picasso.with(this).load(ICONS[3]).into(img_title);
            viewPager.setCurrentItem(3);
        }
    }

    //沒被選中的圖，設黑色底
    private void changeTabNormal(TabLayout.Tab tab) {
        View view = tab.getCustomView();//使用自定義view
        ImageView img_title = (ImageView) view.findViewById(R.id.img_title);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title);

        if (txt_title.getText().toString().equals("One")) {
            Picasso.with(this).load(ICONS2[0]).into(img_title);
        } else if (txt_title.getText().toString().equals("Two")) {
            Picasso.with(this).load(ICONS2[1]).into(img_title);
        }else if (txt_title.getText().toString().equals("Three")) {
            Picasso.with(this).load(ICONS2[2]).into(img_title);
        }
        else {
            Picasso.with(this).load(ICONS2[3]).into(img_title);
        }
    }

    //初始化每個頁面的fragment（部落格，記錄食物，待吃，個人主頁）
    private void initFragment() {
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new ArticleFragment());//部落格
        fragmentArrayList.add(new FoodCalendarFragment());//記錄食物
        fragmentArrayList.add(new WaitingEatFragment());//待吃
        fragmentArrayList.add(new HomePageFragment());//個人主頁
    }

    //設定viewpager的adapter
    class SimpleFragmentPagerAdapter extends FragmentStatePagerAdapter {
        Context mContext;
        ArrayList<Fragment> fragmentArrayList = new ArrayList<Fragment>();
        public SimpleFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentArrayList, Context context) {
            super(fm);
            this.fragmentArrayList = fragmentArrayList;
            mContext = context;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }
    }
}

