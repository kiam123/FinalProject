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

import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.FoodDBHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodPoisonBarChartFragmenet extends Fragment {
    BarChart barChart;
    ArrayList<String> xAxis = new ArrayList<String>();
    ArrayList<IBarDataSet> dataSet = new ArrayList<IBarDataSet>();
    ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
    FoodDBHelper foodDBHelper;

    public FoodPoisonBarChartFragmenet() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_poison_bar_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int tempMonth = 0;
        for (int i = 1; i <= dayOfWeek; i++) {
            Cursor res = null;
            if (day <= 0) {
                if (month - 1 < 1) {
                    cal.set(Calendar.MONTH, year - 1);
                    tempMonth = cal.get(Calendar.MONTH) + 1;
                } else {
                    cal.add(Calendar.MONTH, -1);
                    tempMonth = cal.get(Calendar.MONTH) + 1;
                    Log.v("month", tempMonth + "");
                }
                cal.set(Calendar.MONTH, 0);
                day = cal.getActualMaximum(Calendar.DATE);
                res = foodDBHelper.getAllData(day + "/" + tempMonth + "/" + year);
                Log.v("tempMonth1", day + "/" + tempMonth + "/" + year);
                day -= 1;
            } else if (tempMonth == 0){
//                Log.v("tempMonth123123", tempMonth + "");
                res = foodDBHelper.getAllData((day) + "/" + month + "/" + year);
                Log.v("tempMonth2", (day) + "/" + month + "/" + year);
                day -= 1;
            } else{
                Log.v("tempMonth", tempMonth + "");
                Log.v("tempMonth", day + "");
                res = foodDBHelper.getAllData(day + "/" + tempMonth + "/" + year);
                Log.v("tempMonth3", day + "/" + tempMonth + "/" + year);
                day -= 1;
            }
            res.moveToFirst();
            float temp = 0;

            for (int j = 0; j < res.getCount(); j++) {
                temp += Float.parseFloat(res.getString(4));
                res.moveToNext();
            }
            Log.v("dayOfWeek", (dayOfWeek-i) + "");
            valueSet1.add(new BarEntry(temp, dayOfWeek-i));
        }

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
}
