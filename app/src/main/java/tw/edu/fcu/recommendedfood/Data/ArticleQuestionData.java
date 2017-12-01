package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by kiam on 2017/11/19.
 */

public class ArticleQuestionData {
    String account_id;
    String article_id;
    String comments;
    String date;
    String time;

    public ArticleQuestionData(String account_id, String article_id, String comments, String date, String time) {
        this.account_id = account_id;
        this.article_id = article_id;
        this.comments = comments;
        this.date = date;
        this.time = time;
    }

    public String getAccount_id() {
        return account_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public String getComments() {
        return comments;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
