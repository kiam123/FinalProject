package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.Data.FoodNoteData;
import tw.edu.fcu.recommendedfood.R;

/**
 * Created by kiam on 2017/10/3.
 */

public class FoodNoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int LAYOUT_1 = 0;
    private Context context;
    private ArrayList<FoodNoteData> foodNoteDatas = new ArrayList<FoodNoteData>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void addItem(FoodNoteData foodNoteData){
        foodNoteDatas.add(foodNoteData);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.layout_food_note, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return foodNoteDatas.size();
    }
}
