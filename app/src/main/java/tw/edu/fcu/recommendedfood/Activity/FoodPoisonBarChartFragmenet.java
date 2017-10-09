package tw.edu.fcu.recommendedfood.Activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import tw.edu.fcu.recommendedfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodPoisonBarChartFragmenet extends Fragment {
    BarChart barChart;

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

        initChart(view);
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
        valueSet1.add(new BarEntry(45, 3));
        valueSet1.add(new BarEntry(12, 4));
        valueSet1.add(new BarEntry(34, 5));
        valueSet1.add(new BarEntry(42, 6));

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
