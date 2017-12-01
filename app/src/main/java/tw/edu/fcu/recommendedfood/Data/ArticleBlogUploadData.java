package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by kiam on 2017/11/20.
 */

public class ArticleBlogUploadData {
    String account_id;
    String article;
    String article_id;
    String click;
    String date;
    String title;
    String type;

    public ArticleBlogUploadData(String account_id, String article, String article_id, String click, String date, String title, String type) {
        this.account_id = account_id;
        this.article = article;
        this.article_id = article_id;
        this.click = click;
        this.date = date;
        this.title = title;
        this.type = type;
    }

    public String getAccount_id() {
        return account_id;
    }

    public String getArticle() {
        return article;
    }

    public String getArticle_id() {
        return article_id;
    }

    public String getClick() {
        return click;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }
}
