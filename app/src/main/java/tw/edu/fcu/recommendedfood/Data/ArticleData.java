package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by kiam on 4/2/2017.
 */

public class ArticleData {
    String count, title, content;

    public ArticleData(String count, String title, String content){
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
