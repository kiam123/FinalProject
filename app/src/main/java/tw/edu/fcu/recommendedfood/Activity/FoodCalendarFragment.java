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
        ViewGroup viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_food_calendar, container, false);



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

    public void initChart(View rootView){
        barChart = (BarChart) rootView.findViewById(R.id.bar_chart);

        ArrayList<String> xAxis = new ArrayList<String>();
        ArrayList<IBarDataSet> dataSet = new ArrayList<IBarDataSet>();
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valueSet2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valueSet3 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valueSet4 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valueSet5 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valueSet6 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valueSet7 = new ArrayList<BarEntry>();

        xAxis.add("");

        valueSet1.add(new BarEntry(25, 0));
        valueSet2.add(new BarEntry(20, 0));
        valueSet3.add(new BarEntry(40, 0));
        valueSet4.add(new BarEntry(30, 0));
        valueSet5.add(new BarEntry(30, 0));
        valueSet6.add(new BarEntry(30, 0));
        valueSet7.add(new BarEntry(30, 0));


        BarDataSet d0 = new BarDataSet(valueSet1, "1");
        BarDataSet d1 = new BarDataSet(valueSet2, "2");
        BarDataSet d2 = new BarDataSet(valueSet3, "3");
        BarDataSet d3 = new BarDataSet(valueSet4, "4");
        BarDataSet d4 = new BarDataSet(valueSet4, "5");
        BarDataSet d5 = new BarDataSet(valueSet4, "6");
        BarDataSet d6 = new BarDataSet(valueSet4, "7");

        dataSet.add(d0);
        dataSet.add(d1);
        dataSet.add(d2);
        dataSet.add(d3);
        dataSet.add(d4);
        dataSet.add(d5);
        dataSet.add(d6);

        BarData barData = new BarData(xAxis, dataSet);
        d0.setColors(new int[]{Color.RED});
        d1.setColors(new int[]{Color.GREEN});
        d2.setColors(new int[]{Color.MAGENTA});
        d3.setColors(new int[]{Color.BLUE});
        d4.setColors(new int[]{Color.BLACK});
        d5.setColors(new int[]{Color.CYAN});
        d6.setColors(new int[]{Color.GRAY});
        d0.setValueTextSize(14f);
        d1.setValueTextSize(14f);
        d2.setValueTextSize(14f);
        d3.setValueTextSize(14f);
        d4.setValueTextSize(14f);
        d5.setValueTextSize(14f);
        d6.setValueTextSize(14f);


        barChart.setDescription("");
        barChart.setExtraOffsets(0, 0, 0, 20);
        barChart.setData(barData);
//        barChart.animateXY(2000, 2000);
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
