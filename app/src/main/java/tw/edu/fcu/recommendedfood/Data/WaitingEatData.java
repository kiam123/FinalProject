package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by yan on 2017/4/6.
 */

import java.util.Date;
import java.util.Locale;

public class WaitingEatData implements java.io.Serializable {

    // 編號、日期時間、顏色、標題、內容、經度、緯度、修改、已選擇
    private long id;
    private long datetime;
    private WaitingEatColors color;
    private String title;
    private String content;
    private double latitude;
    private double longitude;
    private long lastModify;
    private boolean selected;

    public WaitingEatData() {
        title = "";
        content = "";
        color = WaitingEatColors.LIGHTGREY;
    }

    public WaitingEatData(long id, long datetime, WaitingEatColors color, String title,
                          String content,double latitude, double longitude, long lastModify) {
        this.id = id;
        this.datetime = datetime;
        this.color = color;
        this.title = title;
        this.content = content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.lastModify = lastModify;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDatetime() {
        return datetime;
    }

    // 裝置區域的日期時間
    public String getLocaleDatetime() {
        return String.format(Locale.getDefault(), "%tF  %<tR", new Date(datetime));
    }

    // 裝置區域的日期
    public String getLocaleDate() {
        return String.format(Locale.getDefault(), "%tF", new Date(datetime));
    }

    // 裝置區域的時間
    public String getLocaleTime() {
        return String.format(Locale.getDefault(), "%tR", new Date(datetime));
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public WaitingEatColors getColor() {
        return color;
    }

    public void setColor(WaitingEatColors color) {
        this.color = color;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getLastModify() {
        return lastModify;
    }

    public void setLastModify(long lastModify) {
        this.lastModify = lastModify;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}