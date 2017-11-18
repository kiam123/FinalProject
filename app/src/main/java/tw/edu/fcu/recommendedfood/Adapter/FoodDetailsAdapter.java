package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.Data.FoodDetailData;
import tw.edu.fcu.recommendedfood.Data.OnItemClickLitener;
import tw.edu.fcu.recommendedfood.R;

/**
 * Created by kiam on 2017/10/3.
 */

public class FoodDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<FoodDetailData> foodDetailDatas = new ArrayList<FoodDetailData>();
    private Context context;
    private OnItemClickLitener onItemClickLitener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lnRoot;
        TextView txtShopName;
        TextView txtFoodName;
        TextView txtPrice;
        TextView txtCalorie;
        TextView plasticizer;
        TextView b;
        TextView c;
        TextView txtQuantity;
        public ViewHolder(View itemView) {
            super(itemView);

            lnRoot = (LinearLayout) itemView.findViewById(R.id.ln_root);
            txtShopName = (TextView) itemView.findViewById(R.id.txt_shop_name);
            txtFoodName = (TextView) itemView.findViewById(R.id.txt_food_name);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_price);
            txtCalorie = (TextView) itemView.findViewById(R.id.txt_calorie);
            plasticizer = (TextView) itemView.findViewById(R.id.txt_plasticizer);
//            b = (TextView) itemView.findViewById(R.id.b);
//            c = (TextView) itemView.findViewById(R.id.c);
            txtQuantity = (TextView) itemView.findViewById(R.id.txt_quantity);
        }
    }

    public void addItem(FoodDetailData foodDetailData){
        foodDetailDatas.add(foodDetailData);
    }

    public FoodDetailData getItem(int pos){
        FoodDetailData foodDetailData = foodDetailDatas.get(pos);
        return foodDetailData;
    }

    public void deleteItem(int pos){
        foodDetailDatas.remove(pos);
        notifyDataSetChanged();
    }

    public void updateItem(int pos, FoodDetailData foodDetailData){
        foodDetailDatas.set(pos,foodDetailData);
        notifyDataSetChanged();
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder)holder).txtShopName.setText(foodDetailDatas.get(position).getShopName());
            ((ViewHolder)holder).txtFoodName.setText(foodDetailDatas.get(position).getFood());
            ((ViewHolder)holder).txtPrice.setText(foodDetailDatas.get(position).getPrice());
            ((ViewHolder)holder).txtCalorie.setText(foodDetailDatas.get(position).getCalorie());
            ((ViewHolder)holder).plasticizer.setText(foodDetailDatas.get(position).getPlasticizer());
//            ((ViewHolder)holder).b.setText(foodDetailDatas.get(position).getB());
//            ((ViewHolder)holder).c.setText(foodDetailDatas.get(position).getC());
            ((ViewHolder)holder).txtQuantity.setText(foodDetailDatas.get(position).getQuantity());

            ((ViewHolder)holder).lnRoot.setBackgroundResource(R.drawable.touch_bg);
            ((ViewHolder)holder).lnRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = ((ViewHolder) holder).getLayoutPosition();
                    onItemClickLitener.onItemClick(((ViewHolder) holder).lnRoot, position);
                }
            });
            ((ViewHolder)holder).lnRoot.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = ((ViewHolder) holder).getLayoutPosition();
                    onItemClickLitener.onItemLongClick(((ViewHolder) holder).lnRoot, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return foodDetailDatas.size();
    }


}
