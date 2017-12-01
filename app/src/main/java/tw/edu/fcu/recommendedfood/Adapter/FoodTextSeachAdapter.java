package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.Data.ArticleData;
import tw.edu.fcu.recommendedfood.Data.FoodTextSearchData;
import tw.edu.fcu.recommendedfood.Data.OnItemClickLitener;
import tw.edu.fcu.recommendedfood.R;

/**
 * Created by kiam on 4/30/2017.
 */

public class FoodTextSeachAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    public ArrayList<FoodTextSearchData> foodTextSearchDatas = new ArrayList<>();
    private OnItemClickLitener onItemClickLitener;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lnRoot;
        TextView txtShopName;
        TextView txtAddress;
        TextView txtPhone;

        public ViewHolder(View itemView) {
            super(itemView);
            lnRoot = (LinearLayout) itemView.findViewById(R.id.ln_root);
            txtShopName = (TextView) itemView.findViewById(R.id.txt_shop_name);
            txtAddress = (TextView) itemView.findViewById(R.id.txt_address);
            txtPhone = (TextView) itemView.findViewById(R.id.txt_phone);
        }
    }

    public void addItem(FoodTextSearchData foodTextSearchData) {
        this.foodTextSearchDatas.add(foodTextSearchData);
        this.notifyDataSetChanged();
    }

    public FoodTextSearchData getItem(int position){
        return foodTextSearchDatas.get(position);
    }

    public void removeItem() {
        foodTextSearchDatas.clear();
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.layout_food_shop, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((ViewHolder) holder).txtShopName.setText(foodTextSearchDatas.get(position).getShopName() + "");
        ((ViewHolder) holder).txtAddress.setText(foodTextSearchDatas.get(position).getAddress());
        ((ViewHolder) holder).txtPhone.setText(foodTextSearchDatas.get(position).getPhone());

        ((ViewHolder)holder).lnRoot.setBackgroundResource(R.drawable.touch_bg);
        ((ViewHolder) holder).lnRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = ((ViewHolder) holder).getLayoutPosition();
                onItemClickLitener.onItemClick(((ViewHolder) holder).lnRoot, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return foodTextSearchDatas.size();
    }
}
