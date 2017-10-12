package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by kiam on 2017/10/3.
 */

public class FoodDetailData {
    String shopName;
    String food;
    String price;
    String calorie;
    String quantity;

    public FoodDetailData(String shopName, String food, String price, String calorie, String quantity) {
        this.shopName = shopName;
        this.food = food;
        this.price = price;
        this.calorie = calorie;
        this.quantity = quantity;
    }

    public String getShopName() {
        return "店名："+shopName;
    }

    public String getFood() {
        return "食物："+food;
    }

    public String getPrice() {
        return "價錢："+price;
    }

    public String getCalorie() {
        return "卡路里："+calorie;
    }

    public String getQuantity() {
        return "數量："+quantity;
    }
}
