package com.ellixar.app.customer.sembe.customerapp;

/**
 * Created by Rp on 3/30/2016.
 */


//********GRIDVIEW************
public class Beanclass {
    private int image1;
    private String title1;
    private String description1;
    private String quantity1;
    private String quantity_to_sell1;
    private String sellatprice1;
    private String status1;
    private String sellingprice1;

    public Beanclass(int image, String product_package_size, String product_name, String selling_price, String sell_at_price, String quantity, String quantity_to_sell, String status) {

        this.image1 = image;
        this.title1 = product_package_size;
        this.description1 = product_name;
        this.quantity1 = quantity;
        this.quantity_to_sell1 = quantity_to_sell;
        this.sellatprice1 = sell_at_price;
        this.sellingprice1 = selling_price;
        this.status1 = status;

    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getQuantity1() {
        return quantity1;
    }

    public void setQuantity1(String quantity1) {
        this.quantity1 = quantity1;
    }

    public String getQuantityToSell1() {
        return quantity_to_sell1;
    }

    public void setQuantityToSell1(String quantity_to_sell1) {
        this.quantity_to_sell1 = quantity_to_sell1;
    }

    public String getSellatprice1() {
        return sellatprice1;
    }

    public void setSellatprice1(String sellatprice1) {
        this.sellatprice1 = sellatprice1;
    }

    public String getSellingprice1() {
        return sellingprice1;
    }

    public void setSellingprice1(String sellingprice1) {
        this.sellingprice1 = sellingprice1;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }
}
