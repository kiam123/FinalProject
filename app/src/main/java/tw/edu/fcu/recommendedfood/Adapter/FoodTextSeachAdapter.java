package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.Data.FoodTextSearchData;
import tw.edu.fcu.recommendedfood.R;

/**
 * Created by kiam on 4/30/2017.
 */

public class FoodTextSeachAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<FoodTextSearchData> foodTextSearchDatas = new ArrayList<>();

    public FoodTextSeachAdapter(Context context){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(FoodTextSearchData foodTextSearchData){
        this.foodTextSearchDatas.add(foodTextSearchData);
        this.notifyDataSetChanged();
    }

    public void removeItem(){
        foodTextSearchDatas.clear();
    }

    public class ViewHolder{
        TextView txtfoodName, txtCalorie;
    }

    @Override
    public int getCount() {
        return foodTextSearchDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        FoodTextSearchData searchData = foodTextSearchDatas.get(position);
        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_food_text_adapter,null);
            viewHolder = new ViewHolder();
            viewHolder.txtfoodName = (TextView) convertView.findViewById(R.id.txt_food_name);
            viewHolder.txtCalorie = (TextView) convertView.findViewById(R.id.txt_calorie);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.txtfoodName.setText(searchData.getFoodName());
        viewHolder.txtCalorie.setText(searchData.getCalorie());

        return convertView;
    }
}
