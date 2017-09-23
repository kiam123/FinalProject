package tw.edu.fcu.recommendedfood.Data;

import java.io.Serializable;

/**
 * Created by kiam on 4/2/2017.
 */

public class ArticleData implements Serializable{
    int count;
    String articleId, title, content;
    public ArticleBlogData articleBlogData = new ArticleBlogData();

    public ArticleData(){}

    public ArticleData(int count, String articleId, String title, String content){
        this.articleId = articleId;
        this.count = count;
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
}
