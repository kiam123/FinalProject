package tw.edu.fcu.recommendedfood.Data;

/**
 * Created by kiam on 2017/10/3.
 */

public class FoodDetailData {
    String id;
    String shopName;
    String food;
    String price;
    String calorie;
    String plasticizer;
    String b;
    String c;
    String quantity;

    public FoodDetailData(String id, String shopName, String food, String price, String calorie,
                          String plasticizer/*, String b, String c*/, String quantity) {
        this.id = id;
        this.shopName = shopName;
        this.food = food;
        this.price = price;
        this.calorie = calorie;
        this.plasticizer = plasticizer;

        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getShopNameTostring() {
        return "店名：" + shopName;
    }

    public String getFoodTostring() {
        return "食物：" + food;
    }

    public String getPriceTostring() {
        return "價錢：" + price;
    }

    public String getCalorieTostring() {
        return "卡路里：" + calorie;
    }

    public String getQuantityTostring() {
        return "數量：" + quantity;
    }

    public String getShopName() {
        return shopName;
    }

    public String getFood() {
        return food;
    }

    public String getPrice() {
        return price;
    }

    public String getCalorie() {
        return calorie;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPlasticizer() {
        return plasticizer;
    }

    public String getB() {
        return b;
    }

    public String getC() {
        return c;
    }
}
