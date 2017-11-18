package tw.edu.fcu.recommendedfood.Activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import tw.edu.fcu.recommendedfood.Adapter.FoodGraphSpinnerAdapter;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodCalendarFragment extends Fragment {
    private ViewPager viewPager;
    private TextView tvMonth;
    private String month;
    Spinner spinner;
    FoodGraphSpinnerAdapter foodGraphSpinnerAdapter;
    private ArrayList<Fragment> fragmentArrayList;
    private Fragment mCurrentFrgment;
    private int currentIndex = 0;


    public FoodCalendarFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_food_calendar, container, false);


        return viewGroup;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        initSpinner(view);
    }

    public void initView(View rootView) {
        tvMonth = (TextView) rootView.findViewById(R.id.tv_month);
        month = Calendar.getInstance().get(Calendar.YEAR)
                + "-"
                + Utils.LeftPad_Tow_Zero(Calendar.getInstance().get(
                Calendar.MONTH) + 1);
        tvMonth.setText(month);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        viewPager.setAdapter(new ScreenSlidePagerAdapter(getChildFragmentManager()));
        viewPager.setCurrentItem(500);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Calendar calendar = Utils.getSelectCalendar(position);
                month = calendar.get(Calendar.YEAR)
                        + "-"
                        + Utils.LeftPad_Tow_Zero(calendar.get(Calendar.MONTH) + 1);
                tvMonth.setText(month);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void initSpinner(View view){
        spinner = (Spinner) view.findViewById(R.id.spinner);
        List<String> list = new ArrayList<>();
        list.add("每週卡路里長條圖");
        list.add("每週卡路里直線圖");
        list.add("每週毒物長條圖");
        list.add("每週毒物直線圖");
        foodGraphSpinnerAdapter = new FoodGraphSpinnerAdapter(getActivity(), list);
        spinner.setAdapter(foodGraphSpinnerAdapter);
        initFragment();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                foodGraphSpinnerAdapter.setPosition(position);
                changeTab(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initFragment() {
        fragmentArrayList = new ArrayList<Fragment>(4);
        fragmentArrayList.add(new FoodCalorieBarChartFragmenet());
        fragmentArrayList.add(new FoodCalorieLineChartFragmenet());
        fragmentArrayList.add(new FoodPoisonBarChartFragmenet());
        fragmentArrayList.add(new FoodPoisonLineChartFragmenet());

        changeTab(0);
    }

    private void changeTab(int index) {
        currentIndex = index;
        /*---------------------------------------------↓需要用v4 lib------------------------------------------------------*/

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        //判断当前的Fragment是否为空，不为空则隐藏
        if (null != mCurrentFrgment) {
            ft.hide(mCurrentFrgment);
        }
        //先根据Tag从FragmentTransaction事物获取之前添加的Fragment
        Fragment fragment = getChildFragmentManager().findFragmentByTag(fragmentArrayList.get(currentIndex).getClass().getName());

        if (null == fragment) {
            //如fragment为空，则之前未添加此Fragment。便从集合中取出
            fragment = fragmentArrayList.get(index);
        }
        mCurrentFrgment = fragment;

        //判断此Fragment是否已经添加到FragmentTransaction事物中
        if (!fragment.isAdded()) {
            ft.add(R.id.fragment, fragment, fragment.getClass().getName());
        } else {
            ft.show(fragment);
        }
        ft.commitAllowingStateLoss();
        /*---------------------------------------------↑需要用v4 lib------------------------------------------------------*/
    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FoodCalendarViewFragment.create(position);
        }

        @Override
        public int getCount() {
            return 1000;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
