package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by kiam on 4/2/2017.
 */

public class ArticleData {
    int count;
    String title, content;

    public ArticleData(int count, String title, String content){
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
}
