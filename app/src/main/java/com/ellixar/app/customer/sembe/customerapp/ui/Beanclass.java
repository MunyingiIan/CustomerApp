package com.ellixar.app.customer.sembe.customerapp.ui;

/**
 * Created by Rp on 3/30/2016.
 */


//********GRIDVIEW************
public class Beanclass {
    private int image1;
    private String product_packet_size1;
    private String product_name1;
    private String product_buying_price1;
    private String quantity_to_sell1;
    private String sellatprice1;
    private String status1;
    private String sellingprice1;

    public Beanclass(int image, String product_packet_size, String product_name, String product_buying_price, String sell_at_price, String selling_price, String quantity_to_sell, String status) {

        this.image1 = image;
        this.product_packet_size1 = product_packet_size;
        this.product_name1 = product_name;
        this.product_buying_price1 = product_buying_price;
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

    public String getProductPacketSize1() {
        return product_packet_size1;
    }

    public void setSellingPrice1(String product_packet_size1) {
        this.product_packet_size1 = product_packet_size1;
    }

    public String getProductName1() {
        return product_name1;
    }

    public void setProductName1(String product_name_1) {
        this.product_name1 = product_name_1;
    }

    public String getProductBuyingPrice1() {
        return product_buying_price1;
    }

    public void setProduct_buying_price1(String product_buying_price1) {
        this.product_buying_price1 = product_buying_price1;
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
