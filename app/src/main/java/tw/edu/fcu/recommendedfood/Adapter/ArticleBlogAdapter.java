package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tw.edu.fcu.recommendedfood.Data.ArticleBlogData;
import tw.edu.fcu.recommendedfood.Photo.PicassoImageGetter;
import tw.edu.fcu.recommendedfood.R;

/**
 * Created by kiam on 4/23/2017.
 */

public class ArticleBlogAdapter extends BaseAdapter {
    ArrayList<ArticleBlogData> articleBlogDatas = new ArrayList<>();
    ArrayList<ArticleBlogData> articleCommentDatas = new ArrayList<>();
    LayoutInflater layoutInflater;
    private Context context;

    public static final int LAYOUT_DISCUSS = 0;
    public static final int LAYOUT_COMMENT = 1;

    public ArticleBlogAdapter(Context context) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(ArticleBlogData articleBlogData) {
        articleBlogDatas.add(articleBlogData);
        notifyDataSetChanged();

//        Log.v("length", articleBlogDatas.size() + "");
    }

    public void addComment(ArticleBlogData articleCommentData) {
        articleCommentDatas.add(articleCommentData);
        sortDate();
    }

    public void updateData() {
        Log.v("length2", articleCommentDatas.size() + "");

        for (int i = 0; i < articleCommentDatas.size(); i++) {
            articleBlogDatas.add(articleCommentDatas.get(i));
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return articleBlogDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //有多少個佈局
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //取得是哪一個佈局
    @Override
    public int getItemViewType(int position) {
        ArticleBlogData blogData = articleBlogDatas.get(position);
        int type = blogData.getType();

        return type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ArticleBlogData blogData = articleBlogDatas.get(position);
        int type = getItemViewType(position);
        DiscussViewHolder discussViewHolder = null;
        CommentViewHolder commentViewHolder = null;
        PicassoImageGetter picassoImageGetter = null;
        Spanned htmlSpan = null;

        if (convertView == null) {
            switch (type) {
                case LAYOUT_DISCUSS:
                    convertView = layoutInflater.inflate(R.layout.layout_article_blog_adapter_discuss, null);
                    discussViewHolder = new DiscussViewHolder();
                    discussViewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txt_title);
                    discussViewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.txt_author);
                    discussViewHolder.txtDate = (TextView) convertView.findViewById(R.id.txt_date);
                    discussViewHolder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
                    discussViewHolder.txtHtmlContent = (TextView) convertView.findViewById(R.id.txt_html_content);
                    discussViewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

                    discussViewHolder.txtTitle.setText(blogData.getTitle());
                    discussViewHolder.txtAuthor.setText(blogData.getAuthor());
                    discussViewHolder.txtTime.setText(blogData.getTime());

                    picassoImageGetter = new PicassoImageGetter(discussViewHolder.txtHtmlContent,
                            discussViewHolder.imageView.getResources(), Picasso.with(context));
                    htmlSpan = Html.fromHtml(blogData.getHtmlContent(), picassoImageGetter, null);
                    discussViewHolder.txtHtmlContent.setText(htmlSpan);

                    convertView.setTag(discussViewHolder);
                    break;
                case LAYOUT_COMMENT:
                    convertView = layoutInflater.inflate(R.layout.layout_article_blog_adapter_comment, null);
                    commentViewHolder = new CommentViewHolder();
                    commentViewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.txt_author);
                    commentViewHolder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
                    commentViewHolder.txtHtmlContent = (TextView) convertView.findViewById(R.id.txt_html_content);

                    commentViewHolder.txtAuthor.setText(blogData.getAuthor());
                    commentViewHolder.txtTime.setText(blogData.getTime());

                    Log.v("Html", position + "");
//                    htmlSpan = Html.fromHtml(blogData.getHtmlContent());
//                    htmlSpan = Html.fromHtml(blogData.getHtmlContent(), picassoImageGetter, null);
//                    commentViewHolder.txtHtmlContent.setText(htmlSpan);
                    commentViewHolder.txtHtmlContent.setText(blogData.getHtmlContent());
                    convertView.setTag(commentViewHolder);
                    break;
            }
        } else {
            switch (type) {
                case LAYOUT_DISCUSS:
                    discussViewHolder = (DiscussViewHolder) convertView.getTag();
                    discussViewHolder.txtTitle.setText(blogData.getTitle());
                    discussViewHolder.txtAuthor.setText(blogData.getAuthor());
                    discussViewHolder.txtDate.setText(blogData.getDate());
                    discussViewHolder.txtTime.setText(blogData.getTime());

                    picassoImageGetter = new PicassoImageGetter(discussViewHolder.txtHtmlContent,
                            discussViewHolder.imageView.getResources(), Picasso.with(context));
                    htmlSpan = Html.fromHtml(blogData.getHtmlContent(), picassoImageGetter, null);
                    discussViewHolder.txtHtmlContent.setText(htmlSpan);
                    break;
                case LAYOUT_COMMENT:
                    commentViewHolder = (CommentViewHolder) convertView.getTag();
                    //TODO 還沒做完
//                    commentViewHolder.txtAvatar.setText(blogData.getAuthor());
                    commentViewHolder.txtAuthor.setText(blogData.getAuthor());
                    commentViewHolder.txtTime.setText(blogData.getTime());

//                    picassoImageGetter = new PicassoImageGetter(commentViewHolder.txtHtmlContent,
//                            commentViewHolder.imageView.getResources(), Picasso.with(context));
//                    htmlSpan = Html.fromHtml(blogData.getHtmlContent(), picassoImageGetter, null);
//                    htmlSpan = Html.fromHtml(blogData.getHtmlContent());
                    commentViewHolder.txtHtmlContent.setText(blogData.getHtmlContent());
                    break;
            }
        }

        return convertView;
    }

    private class DiscussViewHolder {
        ImageView imageView;
        TextView txtTitle, txtAuthor, txtDate, txtTime, txtHtmlContent;
    }

    private class CommentViewHolder {
        ImageView imageView;
        TextView txtAvatar, txtAuthor, txtHtmlContent, txtTime;
    }

    public void sortDate() {
        Collections.sort(articleCommentDatas, new Comparator<ArticleBlogData>() {
            DateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm");

            @Override
            public int compare(ArticleBlogData o1, ArticleBlogData o2) {
                try {
                    return f.parse(o2.getTime()).compareTo(f.parse(o1.getTime()));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });
    }
}
