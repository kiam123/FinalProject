package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.Data.FoodDetailData;
import tw.edu.fcu.recommendedfood.R;

/**
 * Created by kiam on 2017/10/3.
 */

public class FoodDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<FoodDetailData> foodDetailDatas = new ArrayList<FoodDetailData>();
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtShopName;
        TextView txtFoodName;
        TextView txtPrice;
        TextView txtCalorie;
        TextView txtQuantity;
        public ViewHolder(View itemView) {
            super(itemView);

            txtShopName = (TextView) itemView.findViewById(R.id.txt_shop_name);
            txtFoodName = (TextView) itemView.findViewById(R.id.txt_food_name);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
            txtCalorie = (TextView) itemView.findViewById(R.id.txt_calorie);
            txtQuantity = (TextView) itemView.findViewById(R.id.txt_quantity);
        }
    }

    public void addItem(FoodDetailData foodDetailData){
        foodDetailDatas.add(foodDetailData);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.layout_food_details, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder)holder).txtShopName.setText(foodDetailDatas.get(position).getShopName());
            ((ViewHolder)holder).txtFoodName.setText(foodDetailDatas.get(position).getFood());
            ((ViewHolder)holder).txtPrice.setText(foodDetailDatas.get(position).getPrice());
            ((ViewHolder)holder).txtCalorie.setText(foodDetailDatas.get(position).getCalorie());
            ((ViewHolder)holder).txtQuantity.setText(foodDetailDatas.get(position).getQuantity());
        }
    }

    @Override
    public int getItemCount() {
        return foodDetailDatas.size();
    }
}
