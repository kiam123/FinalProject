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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.FoodDBHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodPoisonLineChartFragmenet extends Fragment {
    private LineChart mChart;
    FoodDBHelper foodDBHelper;

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

        initDatebase();
        initChart(view);
    }

    public void initDatebase() {
        foodDBHelper = new FoodDBHelper(getActivity());
    }

    public void initChart(View view){
        mChart = (LineChart) view.findViewById(R.id.line_chart);
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);
        setChart();
    }

    public void setChart() {
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMaxValue(600f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.enableGridDashedLine(10f, 10f, 0);
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(true);

        ArrayList<Entry> yValues = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

//        if(dayOfWeek == 7){
//            dayOfWeek -= 1;
//        }
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
                res = foodDBHelper.getAllPoisonData(day + "/" + tempMonth + "/" + year);
                Log.v("tempMonth1", day + "/" + tempMonth + "/" + year);
                day -= 1;
            } else if (tempMonth == 0){
//                Log.v("tempMonth123123", tempMonth + "");
                res = foodDBHelper.getAllPoisonData((day) + "/" + month + "/" + year);
                Log.v("tempMonth2", (day) + "/" + month + "/" + year);
                day -= 1;
            } else{
                Log.v("tempMonth", tempMonth + "");
                Log.v("tempMonth", day + "");
                res = foodDBHelper.getAllPoisonData(day + "/" + tempMonth + "/" + year);
                Log.v("tempMonth3", day + "/" + tempMonth + "/" + year);
                day -= 1;
            }
            res.moveToFirst();
            float temp = 0;
            Log.v("aaaddd", res.getCount() + "");
            Log.v("aaaddd", (day - i) + "/" + month + "/" + year);

            for (int j = 0; j < res.getCount(); j++) {
                temp = temp + Float.parseFloat(res.getString(0)) * Float.parseFloat(res.getString(1));
                Log.v("aaaddd", temp + "");
                res.moveToNext();
            }
            yValues.add(new Entry(temp, dayOfWeek - i));
        }

        LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");
        set1.setFillAlpha(1010);
        set1.setColor(Color.RED);
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.MAGENTA);

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1);

        String[] values = new String[]{"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

        LineData data = new LineData(values, dataSets);
        mChart.setData(data);
        mChart.setData(data);
        mChart.setDescription("");
    }
}
