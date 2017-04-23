package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.Data.ArticleBlogData;

/**
 * Created by kiam on 4/23/2017.
 */

public class ArticleBlogAdapter extends BaseAdapter {
    ArrayList<ArticleBlogData> articleBlogData = new ArrayList<>();
    LayoutInflater layoutInflater;

    public ArticleBlogAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(ArticleBlogData articleBlogData) {
        this.articleBlogData.add(articleBlogData);
    }

    private class ViewHolder{
        TextView title, content, img;
    }

    @Override
    public int getCount() {
        return articleBlogData.size();
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
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        ArticleBlogData BlogData = articleBlogData.get(position);
        int type = BlogData.getType();

        return type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        return convertView;
    }
}
