package tw.edu.fcu.recommendedfood.Adapter;

/**
 * Created by yan on 2017/4/6.
 */
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import tw.edu.fcu.recommendedfood.Data.WaitingEatData;
import tw.edu.fcu.recommendedfood.R;

public class WaitingEatAdapter extends ArrayAdapter<WaitingEatData> {

    // 畫面資源編號
    private int resource;
    // 包裝的記事資料
    private List<WaitingEatData> waitingEatDatas;

    public WaitingEatAdapter(Context context, int resource, List<WaitingEatData> waitingEatDatas) {
        super(context, resource, waitingEatDatas);
        this.resource = resource;
        this.waitingEatDatas = waitingEatDatas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView;
        // 讀取目前位置的記事物件
        final WaitingEatData waitingEatData = getItem(position);

        if (convertView == null) {
            // 建立項目畫面元件
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater = (LayoutInflater)
                    getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, itemView, true);
        }
        else {
            itemView = (LinearLayout) convertView;
        }

        // 讀取記事顏色、已選擇、標題與日期時間元件
        RelativeLayout typeColor = (RelativeLayout) itemView.findViewById(R.id.relative_type_color);
        ImageView selectedItem = (ImageView) itemView.findViewById(R.id.img_selected_item);
        TextView titleView = (TextView) itemView.findViewById(R.id.txt_title_text);
        TextView dateView = (TextView) itemView.findViewById(R.id.txt_date_text);

        // 設定記事顏色
        GradientDrawable background = (GradientDrawable)typeColor.getBackground();
        background.setColor(waitingEatData.getColor().parseColor());

        // 設定標題與日期時間
        titleView.setText(waitingEatData.getTitle());
        dateView.setText(waitingEatData.getLocaleDatetime());

        // 設定是否已選擇
        selectedItem.setVisibility(waitingEatData.isSelected() ? View.VISIBLE : View.INVISIBLE);

        return itemView;
    }

    // 設定指定編號的記事資料
    public void set(int index, WaitingEatData item) {
        if (index >= 0 && index < waitingEatDatas.size()) {
            waitingEatDatas.set(index, item);
            notifyDataSetChanged();
        }
    }

    // 讀取指定編號的記事資料
    public WaitingEatData get(int index) {
        return waitingEatDatas.get(index);
    }

}