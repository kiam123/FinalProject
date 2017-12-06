package tw.edu.fcu.recommendedfood.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import tw.edu.fcu.recommendedfood.Adapter.FoodCalendarGridAdapter;
import tw.edu.fcu.recommendedfood.Data.LoginContext;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.FoodDBHelper;
import tw.edu.fcu.recommendedfood.Utils.Utils;

public class FoodCalendarViewFragment extends Fragment {
    public static final String ARG_PAGE = "classofication";
    public static final String KEY_MONTH = "MONTH";

    private int mPageNumber;

    private Calendar mCalendar;

    private FoodCalendarGridAdapter foodCalendarGridAdapter;

    String month;

    FoodDBHelper foodDBHelper;

    public static Fragment create(int pageNumber) {
        FoodCalendarViewFragment fragment = new FoodCalendarViewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public FoodCalendarViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        mCalendar = Utils.getSelectCalendar(mPageNumber);
        foodCalendarGridAdapter = new FoodCalendarGridAdapter(getActivity(),
                mCalendar);
        foodDBHelper = new FoodDBHelper(getActivity());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_food_calendar_view, container, false);
        GridView titleGridView = (GridView) rootView.findViewById(R.id.gridview);
        TitleGridAdapter titleAdapter = new TitleGridAdapter(getActivity());
        initGridView(titleGridView, titleAdapter);

        GridView calendarView = (GridView) rootView.findViewById(R.id.calendarView);
        initGridView(calendarView, foodCalendarGridAdapter);
        calendarView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Date date = (Date) foodCalendarGridAdapter.getItem(position);
                SimpleDateFormat df = new SimpleDateFormat("yyyy");
                df.format(date);

                for (int i = 0; i < parent.getCount(); i++) {
                    if(!(foodDBHelper.getDateData(LoginContext.getLoginContext().getAccount(),
                                    (date.getDate())+"/"+(date.getMonth()+1)+"/"+df.format(date)))) {
                        parent.getChildAt(i).setBackgroundColor(
                                Color.TRANSPARENT);
                    }
                }
//                view.setBackgroundColor(getActivity().getResources().getColor(
//                        R.color.selection));

                Intent intent = new Intent();
                intent.putExtra("DATE",date.getDate()+"/"+(date.getMonth()+1)+"/"+df.format(date));
                intent.setClass(getActivity(),FoodRecorderActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void initGridView(GridView gridView, BaseAdapter adapter) {
        gridView = setGirdView(gridView);
        gridView.setAdapter(adapter);// 设置菜单Adapter
    }

    @SuppressWarnings("deprecation")
    private GridView setGirdView(GridView gridView) {
        gridView.setNumColumns(7);// 设置每行列数
        gridView.setGravity(Gravity.CENTER_VERTICAL);// 位置居中
        gridView.setVerticalSpacing(1);// 垂直间隔
        gridView.setHorizontalSpacing(1);// 水平间隔
        gridView.setBackgroundColor(getResources().getColor(
                R.color.calendar_background));

        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int i = display.getWidth() / 7;
        int j = display.getWidth() - (i * 7);
        int x = j / 2;
        gridView.setPadding(x, 0, 0, 0);// 居中

        return gridView;
    }

    public class TitleGridAdapter extends BaseAdapter {

        int[] titles = new int[] { R.string.Sun, R.string.Mon, R.string.Tue,
                R.string.Wed, R.string.Thu, R.string.Fri, R.string.Sat };

        private Context activity;

        // construct
        public TitleGridAdapter(Context a) {
            activity = a;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout linearLayout = new LinearLayout(activity);
            TextView txtDay = new TextView(activity);
            txtDay.setFocusable(false);
            txtDay.setBackgroundColor(Color.TRANSPARENT);
            txtDay.setHeight(100);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            txtDay.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            txtDay.setTextColor(Color.GRAY);

            txtDay.setText((Integer) getItem(position));

            linearLayout.addView(txtDay, layoutParams);
            return linearLayout;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        foodCalendarGridAdapter.notifyDataSetChanged();
    }
}
