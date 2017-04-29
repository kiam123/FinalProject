package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by kiam on 4/23/2017.
 */

public class ArticleBlogData {
    String avatar;
    String title;
    String author;
    String time;
    String htmlContent;
    int type;

    public ArticleBlogData(String title, String author, String time, String htmlContent, int type) {
        this.title = title;
        this.author = author;
        this.time = time;
        this.htmlContent = htmlContent;
        this.type = type;
    }

    public ArticleBlogData(int type, String avatar, String author, String htmlContent, String time) {
        this.avatar = avatar;
        this.author = author;
        this.time = time;
        this.htmlContent = htmlContent;
        this.type = type;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getTime() {
        return time;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public int getType() {
        return type;
    }
}
