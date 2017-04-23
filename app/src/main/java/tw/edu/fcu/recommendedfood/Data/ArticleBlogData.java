package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by kiam on 4/23/2017.
 */

public class ArticleBlogData {
    String title;
    String content;
    String img;
    int type;

    public ArticleBlogData(String title, String content, String img, int type) {
        this.title = title;
        this.content = content;
        this.img = img;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImg() {
        return img;
    }

    public int getType() {
        return type;
    }
}
