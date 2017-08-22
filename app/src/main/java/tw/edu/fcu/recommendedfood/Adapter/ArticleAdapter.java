package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

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

    public void addItem(ArticleData itemData){
        itemDatas.add(itemData);
        this.notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView count, title, content;
    }

    @Override
    public int getCount() {
        return itemDatas.size();
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

        viewHolder.count.setText(itemDatas.get(position).getCount());
        viewHolder.title.setText(itemDatas.get(position).getTitle());
        viewHolder.content.setText(itemDatas.get(position).getContent());

        return convertView;
    }

    public void sort(){

    }
}
