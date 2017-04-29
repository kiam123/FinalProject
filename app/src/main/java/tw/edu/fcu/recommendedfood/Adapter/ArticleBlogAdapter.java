package tw.edu.fcu.recommendedfood.Adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tw.edu.fcu.recommendedfood.Data.ArticleBlogData;
import tw.edu.fcu.recommendedfood.Photo.PicassoImageGetter;
import tw.edu.fcu.recommendedfood.R;

/**
 * Created by kiam on 4/23/2017.
 */

public class ArticleBlogAdapter extends BaseAdapter {
    ArrayList<ArticleBlogData> articleBlogData = new ArrayList<>();
    LayoutInflater layoutInflater;
    private Context context;

    public static final int LAYOUT_DISCUSS = 0;
    public static final int LAYOUT_COMMENT = 1;

    public ArticleBlogAdapter(Context context) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(ArticleBlogData articleBlogData) {
        this.articleBlogData.add(articleBlogData);
        this.notifyDataSetChanged();
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

    //有多少個佈局
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //取得是哪一個佈局
    @Override
    public int getItemViewType(int position) {
        ArticleBlogData blogData = articleBlogData.get(position);
        int type = blogData.getType();

        return type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ArticleBlogData blogData = articleBlogData.get(position);
        int type = getItemViewType(position);
        DiscussViewHolder discussViewHolder = null;
        CommentViewHolder commentViewHolder = null;
        PicassoImageGetter picassoImageGetter = null;
        Spanned htmlSpan = null;

        if(convertView == null){
            switch (type){
                case LAYOUT_DISCUSS:
                    convertView = layoutInflater.inflate(R.layout.layout_article_blog_adapter_discuss, null);
                    discussViewHolder = new DiscussViewHolder();
                    discussViewHolder.txtTitle = (TextView) convertView.findViewById(R.id.txt_title);
                    discussViewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.txt_author);
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
                    commentViewHolder.txtAvatar = (TextView) convertView.findViewById(R.id.txt_avatar);
                    commentViewHolder.txtAuthor = (TextView) convertView.findViewById(R.id.txt_author);
                    commentViewHolder.txtTime = (TextView) convertView.findViewById(R.id.txt_time);
                    commentViewHolder.txtHtmlContent = (TextView) convertView.findViewById(R.id.txt_html_content);
                    commentViewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);

                    commentViewHolder.txtAvatar.setText(blogData.getAvatar());
                    commentViewHolder.txtAuthor.setText(blogData.getAuthor());
                    commentViewHolder.txtTime.setText(blogData.getTime());

                    picassoImageGetter = new PicassoImageGetter(commentViewHolder.txtHtmlContent,
                            commentViewHolder.imageView.getResources(), Picasso.with(context));
                    htmlSpan = Html.fromHtml(blogData.getHtmlContent(), picassoImageGetter, null);
                    commentViewHolder.txtHtmlContent.setText(htmlSpan);

                    convertView.setTag(commentViewHolder);
                    break;
            }
        }else{
            switch (type){
                case LAYOUT_DISCUSS:
                    discussViewHolder = (DiscussViewHolder) convertView.getTag();
                    discussViewHolder.txtTitle.setText(blogData.getTitle());
                    discussViewHolder.txtAuthor.setText(blogData.getAuthor());
                    discussViewHolder.txtTime.setText(blogData.getTime());

                    picassoImageGetter = new PicassoImageGetter(discussViewHolder.txtHtmlContent,
                            discussViewHolder.imageView.getResources(), Picasso.with(context));
                    htmlSpan = Html.fromHtml(blogData.getHtmlContent(), picassoImageGetter, null);
                    discussViewHolder.txtHtmlContent.setText(htmlSpan);
                    break;
                case LAYOUT_COMMENT:
                    commentViewHolder = (CommentViewHolder) convertView.getTag();
                    commentViewHolder.txtAvatar.setText(blogData.getAvatar());
                    commentViewHolder.txtAuthor.setText(blogData.getAuthor());
                    commentViewHolder.txtTime.setText(blogData.getTime());

                    picassoImageGetter = new PicassoImageGetter(commentViewHolder.txtHtmlContent,
                            commentViewHolder.imageView.getResources(), Picasso.with(context));
                    htmlSpan = Html.fromHtml(blogData.getHtmlContent(), picassoImageGetter, null);
                    commentViewHolder.txtHtmlContent.setText(htmlSpan);
                    break;
            }
        }

        return convertView;
    }

    private class DiscussViewHolder {
        ImageView imageView;
        TextView txtTitle, txtAuthor, txtTime, txtHtmlContent;
    }

    private class CommentViewHolder {
        ImageView imageView;
        TextView txtAvatar, txtAuthor, txtHtmlContent, txtTime;
    }
}
