package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tw.edu.fcu.recommendedfood.Data.ArticleData;
import tw.edu.fcu.recommendedfood.Data.OnItemClickLitener;
import tw.edu.fcu.recommendedfood.R;

/**
 * Created by kiam on 2017/10/17.
 */

public class ArticleSeachAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ArticleData> articleDatas = new ArrayList<>();
    private OnItemClickLitener onItemClickLitener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout lnRoot;
        TextView txtCount;
        TextView txtTitle;
        TextView txtContent;

        public ViewHolder(View itemView) {
            super(itemView);
            lnRoot = (LinearLayout) itemView.findViewById(R.id.ln_root);
            txtCount = (TextView) itemView.findViewById(R.id.count);
            txtTitle = (TextView) itemView.findViewById(R.id.title);
            txtContent = (TextView) itemView.findViewById(R.id.content);
        }
    }

    public void addItem(ArticleData articleData) {
        articleDatas.add(articleData);
        sort();
        notifyDataSetChanged();
    }

    public ArticleData getItem(int position) {
        return articleDatas.get(position);
    }

    public void removeItem() {
        articleDatas.clear();
    }

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.onItemClickLitener = onItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;

        view = LayoutInflater.from(context).inflate(R.layout.layout_article_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).txtCount.setText(articleDatas.get(position).getCount() + "");
            ((ViewHolder) holder).txtTitle.setText(articleDatas.get(position).getTitle());
            ((ViewHolder) holder).txtContent.setText(articleDatas.get(position).getContent());
            ((ViewHolder) holder).lnRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = ((ViewHolder) holder).getLayoutPosition();
                    onItemClickLitener.onItemClick(((ViewHolder) holder).lnRoot, position);
                }
            });

            ((ViewHolder) holder).lnRoot.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = ((ViewHolder) holder).getLayoutPosition();
                    onItemClickLitener.onItemLongClick(((ViewHolder) holder).lnRoot, position);
                    return false;
                }
            });
//            Log.v("abc","123");
//            Toast.makeText(context,"123",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return articleDatas.size();
    }

    public void sort() {
        Collections.sort(articleDatas, new Comparator<ArticleData>() {
            @Override
            public int compare(ArticleData o1, ArticleData o2) {
                return o2.getCount() - o1.getCount();
            }
        });
    }
}
