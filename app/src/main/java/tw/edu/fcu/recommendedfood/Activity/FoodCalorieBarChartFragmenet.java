package tw.edu.fcu.recommendedfood.Activity;


import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import tw.edu.fcu.recommendedfood.Data.LoginContext;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.FoodDBHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodCalorieBarChartFragmenet extends Fragment {
    BarChart barChart;
    ArrayList<String> xAxis = new ArrayList<String>();
    ArrayList<IBarDataSet> dataSet = new ArrayList<IBarDataSet>();
    ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
    FoodDBHelper foodDBHelper;
    View viewGroup;

    public FoodCalorieBarChartFragmenet() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_calorie_bar_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewGroup = view;
        initDatebase();
        initChart(view);
    }

    public void initDatebase() {
        foodDBHelper = new FoodDBHelper(getActivity());
    }

    public void initChart(View rootView) {
        barChart = (BarChart) rootView.findViewById(R.id.bar_chart);


        xAxis.add("禮拜日");
        xAxis.add("禮拜一");
        xAxis.add("禮拜二");
        xAxis.add("禮拜三");
        xAxis.add("禮拜四");
        xAxis.add("禮拜五");
        xAxis.add("禮拜六");

        setBarChart();
    }

    public void setBarChart(){
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

//        if(dayOfWeek == 7)
//            dayOfWeek -= 1;
        Log.v("kkkk", dayOfWeek + "");
        int tempMonth = 0;
        int abc123 = 0;
        Cursor res = null;
        for (int i = 1; i <= dayOfWeek; i++) {
            if (day <= 0) {
                if (month - 1 < 1) {
                    cal.set(Calendar.YEAR, cal.get( Calendar.YEAR )-1);
                    cal.set( Calendar.MONTH, cal.get( Calendar.MONTH )-1);
                    tempMonth = cal.get(Calendar.MONTH) + 1;
                } else {
                    cal.set(Calendar.MONTH, cal.get( Calendar.MONTH )-1);
                    tempMonth = cal.get(Calendar.MONTH)+1;
                    Log.v("month", tempMonth + " "+year);
                }
                day = cal.getActualMaximum(Calendar.DATE);

                res = foodDBHelper.getAllData(LoginContext.getLoginContext().getAccount(),day + "/" + tempMonth + "/" + year);
                Log.v("tempMonth1", day + "/" + tempMonth + "/" + year+ "  1");
                day -= 1;
                abc123 = 1;
            } else if (tempMonth == 0) {
                res = foodDBHelper.getAllData(LoginContext.getLoginContext().getAccount(),(day) + "/" + month + "/" + year);
                Log.v("tempMonth2", (day) + "/" + month + "/" + year+ "  2");
                day -= 1;
                abc123 = 2;
            } else {
                res = foodDBHelper.getAllData(LoginContext.getLoginContext().getAccount(),day + "/" + tempMonth + "/" + year);
                Log.v("tempMonth3", day + "/" + tempMonth + "/" + year+ "  3");
                day -= 1;
                abc123 = 3;
            }
            res.moveToFirst();
            float temp = 0;

            for (int j = 0; j < res.getCount(); j++) {
                temp = temp + Float.parseFloat(res.getString(5)) * Float.parseFloat(res.getString(7));
                res.moveToNext();
            }
            Log.v("dayOfWeek", (dayOfWeek - i) + "  "  +temp+" "+abc123);
            valueSet1.add(new BarEntry(temp, dayOfWeek - i));
            res.close();
        }

        foodDBHelper.close();

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

    public void update (){
        xAxis = new ArrayList<String>();
        dataSet = new ArrayList<IBarDataSet>();
        valueSet1 = new ArrayList<BarEntry>();

        xAxis.add("禮拜日");
        xAxis.add("禮拜一");
        xAxis.add("禮拜二");
        xAxis.add("禮拜三");
        xAxis.add("禮拜四");
        xAxis.add("禮拜五");
        xAxis.add("禮拜六");

        setBarChart();
    }
}
