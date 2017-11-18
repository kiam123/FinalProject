package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tw.edu.fcu.recommendedfood.Activity.FoodGpsDialogShopActivity;
import tw.edu.fcu.recommendedfood.Activity.FoodTextSeachActivity;
import tw.edu.fcu.recommendedfood.Data.FoodTextSearchData;
import tw.edu.fcu.recommendedfood.Data.OnItemClickLitener;
import tw.edu.fcu.recommendedfood.R;
import tw.edu.fcu.recommendedfood.Server.FoodDBHelper;

/**
 * Created by kiam on 2017/10/24.
 */

public class FoodTextShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private FoodTextSearchData foodTextSearchDatas;
    private OnItemClickLitener onItemClickLitener;
    Context mContext;
    FoodDBHelper foodDBHelper;

    public FoodTextShopAdapter(Context mContext) {
        this.mContext = mContext;
        foodDBHelper = new FoodDBHelper(mContext);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFoodName;
        ImageView imgAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            txtFoodName = (TextView) itemView.findViewById(R.id.txt_food_name);
            imgAdd = (ImageView) itemView.findViewById(R.id.img_add);
        }
    }

    public void setFoodData(FoodTextSearchData foodTextSearchDatas) {
        this.foodTextSearchDatas = foodTextSearchDatas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.layout_food_dlg_shop_detail, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).txtFoodName.setText(foodTextSearchDatas.foodShopDatas.get(position).getFoodName());
        ((ViewHolder) holder).imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = ((ViewHolder) holder).getLayoutPosition();
                String date;
                Log.v("position", foodTextSearchDatas.getShopName() + "");
                Log.v("position", foodTextSearchDatas.foodShopDatas.get(position).getFoodName() + "");
                Log.v("position", foodTextSearchDatas.foodShopDatas.get(position).getPrice() + "");
                Log.v("position", foodTextSearchDatas.foodShopDatas.get(position).getCalorie() + "");

                String shopName = foodTextSearchDatas.getShopName();
                String foodName = foodTextSearchDatas.foodShopDatas.get(position).getFoodName();
                String price = foodTextSearchDatas.foodShopDatas.get(position).getPrice();
                String calorie = foodTextSearchDatas.foodShopDatas.get(position).getCalorie();
                String plasticizer = foodTextSearchDatas.foodShopDatas.get(position).getPlasticizer();
//                String b = foodTextSearchDatas.foodShopDatas.get(position).getB();
//                String c = foodTextSearchDatas.foodShopDatas.get(position).getC();
                String count = foodTextSearchDatas.foodShopDatas.get(position).getCount();
                if (mContext instanceof FoodTextSeachActivity) {
                    Log.v("position", ((FoodTextSeachActivity) mContext).date + "");
                    date = ((FoodTextSeachActivity) mContext).date;
                } else {
                    Log.v("position", ((FoodGpsDialogShopActivity) mContext).date + "");
                    date = ((FoodGpsDialogShopActivity) mContext).date;
                }

                boolean flag = foodDBHelper.isDataExist(shopName, foodName, date);

                if (!flag) {
                    Toast.makeText(mContext, "已存在", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInsert = foodDBHelper.insertData(shopName, foodName, price, calorie, plasticizer, count, date);
                    Log.v("asdasdasd",price);
                    if (isInsert) {
                        Toast.makeText(mContext, "新增成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "新增失敗", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (foodTextSearchDatas == null) {
            return 0;
        } else {
            return foodTextSearchDatas.foodShopDatas.size();
        }
    }
}
