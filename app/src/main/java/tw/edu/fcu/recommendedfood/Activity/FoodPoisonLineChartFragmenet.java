package tw.edu.fcu.recommendedfood.Activity;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodPoisonLineChartFragmenet extends Fragment {
    private LineChart mChart;

    public FoodPoisonLineChartFragmenet() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_food_poison_line_chart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initChart(view);
    }

    public void initChart(View view){
        mChart = (LineChart) view.findViewById(R.id.line_chart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        setChart();
    }

    public void setChart(){
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(25f);
        leftAxis.enableGridDashedLine(10f,10f,0);
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(true);

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(60f,0));
        yValues.add(new Entry(50f,1));
        yValues.add(new Entry(70f,2));
        yValues.add(new Entry(30f,3));
        yValues.add(new Entry(50f,4));
        yValues.add(new Entry(60f,5));
        yValues.add(new Entry(65f,6));


        LineDataSet set1 = new LineDataSet(yValues,"Data Set 1");
        set1.setFillAlpha(110);
        set1.setColor(Color.RED);
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.GREEN);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1);

        String[] values = new String [] {"Jan","Feb","Mar","Apr","May","Jun","Jul"};

        LineData data = new LineData(values,dataSets);
        mChart.setData(data);
        mChart.setData(data);
    }
}
