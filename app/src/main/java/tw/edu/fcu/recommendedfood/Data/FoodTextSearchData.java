package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by kiam on 4/30/2017.
 */

public class FoodTextSearchData {
    String foodName;
    String calorie;
    String date;
    String time;

    public FoodTextSearchData(String foodName, String calorie) {
        this.foodName = foodName;
        this.calorie = calorie;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getCalorie() {
        return calorie;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
