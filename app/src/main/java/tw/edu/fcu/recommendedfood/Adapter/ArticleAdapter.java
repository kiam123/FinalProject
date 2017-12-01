package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import tw.edu.fcu.recommendedfood.Data.ArticleData;
import tw.edu.fcu.recommendedfood.R;

/**
 * Created by kiam on 4/5/2017.
 */

public class ArticleAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    ArrayList<ArticleData> itemDatas = new ArrayList<ArticleData>();

    public ArticleAdapter(Context context){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(ArticleData itemData, String type){
        itemDatas.add(itemData);
        if("hot".equals(type)) {
            sort();
        }else{
            sortDate();
        }
        this.notifyDataSetChanged();
    }

    public void deleteItem(int pos){
        itemDatas.remove(pos);
        notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView count, title, content;
    }

    @Override
    public int getCount() {
        return itemDatas.size();
    }

    public ArticleData getItem(int position) {
        return itemDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.layout_article_adapter, null);
            viewHolder = new ViewHolder();
            viewHolder.count = (TextView) convertView.findViewById(R.id.count);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.count.setText(itemDatas.get(position).getCount()+"");
        viewHolder.title.setText(itemDatas.get(position).getTitle());
        viewHolder.content.setText(itemDatas.get(position).getContent());

        return convertView;
    }

    public void sort() {
        Collections.sort(itemDatas, new Comparator<ArticleData>(){
            @Override
            public int compare(ArticleData o1, ArticleData o2) {
                return o2.getCount() - o1.getCount();
            }
        });
    }

    public void sortDate() {
        Collections.sort(itemDatas, new Comparator<ArticleData>() {
            DateFormat f = new SimpleDateFormat("yyyy/MM/dd");

            @Override
            public int compare(ArticleData o1, ArticleData o2) {
                try {
                    return f.parse(o2.articleBlogData.getDate()).compareTo(f.parse(o1.articleBlogData.getDate()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }
}
