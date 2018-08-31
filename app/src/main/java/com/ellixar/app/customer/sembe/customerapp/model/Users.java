package com.ellixar.app.customer.sembe.customerapp.model;

/**
 * Created by Munyingi Ian on Friday : 3/23/2018.
 */

public class Users extends UserId{
    private String name;
    private String imageurl;

    public Users() {
    }

    public Users(String name, String imageurl) {
        this.name = name;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
