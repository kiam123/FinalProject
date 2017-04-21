package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.Data.ArticleClassificationData;
import tw.edu.fcu.recommendedfood.R;

/**
 * Created by kiam on 4/8/2017.
 */


//TODO 這裡可能需要刪掉  如果沒用到
public class ArticleClassificationAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    ArrayList<ArticleClassificationData> articleClassificationDatas = new ArrayList<>();

    public ArticleClassificationAdapter(Context context){
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(ArticleClassificationData articleClassificationData){
        articleClassificationDatas.add(articleClassificationData);
        this.notifyDataSetChanged();
    }

    private class ViewHolder{

    }

    @Override
    public int getCount() {
        return articleClassificationDatas.size();
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

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }


        return convertView;
    }
}
