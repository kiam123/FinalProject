package tw.edu.fcu.recommendedfood.Activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;

import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodCalendarFragment extends Fragment {
    private ViewPager viewPager;
    private TextView tvMonth;
    private String month;

    BarChart barChart;


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
        initChart(view);
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

    public void initChart(View rootView) {
        barChart = (BarChart) rootView.findViewById(R.id.bar_chart);

        ArrayList<String> xAxis = new ArrayList<String>();
        ArrayList<IBarDataSet> dataSet = new ArrayList<IBarDataSet>();
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();

        xAxis.add("禮拜一");
        xAxis.add("禮拜二");
        xAxis.add("禮拜三");
        xAxis.add("禮拜四");
        xAxis.add("禮拜五");
        xAxis.add("禮拜六");
        xAxis.add("禮拜日");


        //TODO 需要做SQlite
        valueSet1.add(new BarEntry(25, 0));
        valueSet1.add(new BarEntry(20, 1));
        valueSet1.add(new BarEntry(40, 2));
        valueSet1.add(new BarEntry(0, 3));
        valueSet1.add(new BarEntry(0, 4));
        valueSet1.add(new BarEntry(0, 5));
        valueSet1.add(new BarEntry(0, 6));

        BarDataSet barDataSet = new BarDataSet(valueSet1, "卡路里");
        barDataSet.setValueTextSize(14f);
        barDataSet.setColor(Color.MAGENTA);
        dataSet.add(barDataSet);

        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);

        BarData barData = new BarData(xAxis, dataSet);

        barChart.setDescription("");
        barChart.setExtraOffsets(0, 0, 0, 20);
        barChart.setData(barData);
        barChart.invalidate();
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
