package tw.edu.fcu.recommendedfood.Adapter;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import tw.edu.fcu.recommendedfood.Data.LoginContext;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.FoodDBHelper;

/**
 * Created by kiam on 3/2/2017.
 */

public class FoodCalendarGridAdapter extends BaseAdapter {

    private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
    private Calendar calToday = Calendar.getInstance(); // 今日
    private int iMonthViewCurrentMonth = 0; // 当前视图月
    FoodDBHelper foodDBHelper;

    // 根据改变的日期更新日历
    // 填充日历控件用
    private void UpdateStartDateForMonth() {
        calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
        iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月

        // 星期一是2 星期天是1 填充剩余天数
        int iDay = 0;
        int iFirstDayOfWeek = Calendar.MONDAY;
        int iStartDay = iFirstDayOfWeek;
        if (iStartDay == Calendar.MONDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
            if (iDay < 0)
                iDay = 6;
        }
        if (iStartDay == Calendar.SUNDAY) {
            iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
            if (iDay < 0)
                iDay = 6;
        }
        calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);

        calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

    }

    ArrayList<Date> titles;

    public ArrayList<Date> getDates() {

        UpdateStartDateForMonth();

        ArrayList<Date> alArrayList = new ArrayList<Date>();

        for (int i = 1; i <= 42; i++) {
            alArrayList.add(calStartDate.getTime());
            calStartDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        return alArrayList;
    }

    private Activity activity;
    Resources resources;

    // construct
    public FoodCalendarGridAdapter(Activity a, Calendar cal) {
        calStartDate = cal;
        activity = a;
        resources = activity.getResources();
        titles = getDates();
        foodDBHelper = new FoodDBHelper(activity);
    }

    public FoodCalendarGridAdapter(Activity a) {
        activity = a;
        resources = activity.getResources();
    }


    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("deprecation")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout iv = new LinearLayout(activity);
        iv.setGravity(Gravity.CENTER);
        iv.setOrientation(LinearLayout.VERTICAL);
        iv.setBackgroundColor(resources.getColor(R.color.white));

        Date myDate = (Date) getItem(position);
        Calendar calCalendar = Calendar.getInstance();
        calCalendar.setTime(myDate);

        final int iMonth = calCalendar.get(Calendar.MONTH);

        // 判断周六周日
        iv.setBackgroundColor(resources.getColor(R.color.white));
        // 判断周六周日结束

        TextView txtToDay = new TextView(activity);
        txtToDay.setGravity(Gravity.CENTER_HORIZONTAL);
        txtToDay.setTextSize(9);
        txtToDay.setHeight(80);

        if (equalsDate(calToday.getTime(), myDate)) {
            // 当前日期
            iv.setBackgroundColor(resources.getColor(R.color.selection));
            txtToDay.setText("TODAY!");
        } else if (foodDBHelper.getDateData(LoginContext.getLoginContext().getAccount(),
                myDate.getDate() + "/" + (myDate.getMonth() + 1) + "/" + (myDate.getYear()+ 1900))) {
            iv.setBackgroundColor(resources.getColor(R.color.forecast_point));
        }

        foodDBHelper.close();
        // 设置背景颜色结束

        // 日期开始
        TextView txtDay = new TextView(activity);// 日期
        txtDay.setGravity(Gravity.CENTER_HORIZONTAL);

        // 判断是否是当前月
        if (iMonth == iMonthViewCurrentMonth) {
            txtToDay.setTextColor(resources.getColor(R.color.ToDayText));
            txtDay.setTextColor(resources.getColor(R.color.Text));
        } else {
            txtDay.setTextColor(resources.getColor(R.color.noMonth));
            txtToDay.setTextColor(resources.getColor(R.color.noMonth));
        }

        int day = myDate.getDate(); // 日期
        txtDay.setText(String.valueOf(day));

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv.addView(txtDay, lp);

        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv.addView(txtToDay, lp1);


        return iv;
    }

    @SuppressWarnings("deprecation")
    private Boolean equalsDate(Date date1, Date date2) {
        if (date1.getYear() == date2.getYear()
                && date1.getMonth() == date2.getMonth()
                && date1.getDate() == date2.getDate()) {
            return true;
        } else {
            return false;
        }
    }
}

