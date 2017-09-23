package tw.edu.fcu.recommendedfood.Data;

import java.io.Serializable;

/**
 * Created by kiam on 4/23/2017.
 */

public class ArticleBlogData implements Serializable{
    String title;
    String author;
    String date;
    String time;
    String htmlContent;
    int type;

    public ArticleBlogData(){
    }

    public ArticleBlogData(String title, String author, String date, String time, String htmlContent, int type) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.time = time;
        this.htmlContent = htmlContent;
        this.type = type;
    }

    public ArticleBlogData(int type, String author, String htmlContent, String time) {
        this.author = author;
        this.time = time;
        this.htmlContent = htmlContent;
        this.type = type;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
