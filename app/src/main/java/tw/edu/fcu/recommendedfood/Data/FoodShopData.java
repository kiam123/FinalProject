package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by kiam on 2017/10/25.
 */

public class FoodShopData {
    String foodName;
    String price;
    String calorie;
    String count;
    String date;

    public FoodShopData(){}

    public FoodShopData(String foodName, String price, String calorie, String count) {
        this.foodName = foodName;
        this.price = price;
        this.calorie = calorie;
        this.count = count;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
