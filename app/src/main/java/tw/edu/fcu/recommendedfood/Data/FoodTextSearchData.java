package tw.edu.fcu.recommendedfood.Data;

import java.util.ArrayList;

/**
 * Created by kiam on 4/30/2017.
 */

public class FoodTextSearchData {
    String foodId;
    String shopName;
    String address;
    String latlng;
    String phone;
    public ArrayList<FoodShopData> foodShopDatas;

    public FoodTextSearchData(String foodId, String shopName, String address, String phone) {
        this.foodId = foodId;
        this.shopName = shopName;
        this.address = address;
        this.phone = phone;
    }

    public FoodTextSearchData() {

    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFoodShopData(ArrayList<FoodShopData> foodShopDatas) {
        this.foodShopDatas = foodShopDatas;
    }
}
