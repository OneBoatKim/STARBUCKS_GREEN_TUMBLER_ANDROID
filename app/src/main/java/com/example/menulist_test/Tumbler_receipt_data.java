package com.example.menulist_test;

public class Tumbler_receipt_data {

    String private_menu_yn;
    String shot;
    String syrup;
    String whipped_cream;
    String drizzle;
    String size;
    String option_sum;
    String menu_cnt;
    String menu_price;
    String menu_name;

    public Tumbler_receipt_data(String private_menu_yn, String shot, String syrup, String whipped_cream, String drizzle, String size, String option_sum, String menu_cnt, String menu_price, String menu_name) {
        this.private_menu_yn = private_menu_yn;
        this.shot = shot;
        this.syrup = syrup;
        this.whipped_cream = whipped_cream;
        this.drizzle = drizzle;
        this.size = size;
        this.option_sum = option_sum;
        this.menu_cnt = menu_cnt;
        this.menu_price = menu_price;
        this.menu_name = menu_name;
    }

    public String getPrivate_menu_yn() {
        return private_menu_yn;
    }

    public void setPrivate_menu_yn(String private_menu_yn) {
        this.private_menu_yn = private_menu_yn;
    }

    public String getShot() {
        return shot;
    }

    public void setShot(String shot) {
        this.shot = shot;
    }

    public String getSyrup() {
        return syrup;
    }

    public void setSyrup(String syrup) {
        this.syrup = syrup;
    }

    public String getWhipped_cream() {
        return whipped_cream;
    }

    public void setWhipped_cream(String whipped_cream) {
        this.whipped_cream = whipped_cream;
    }

    public String getDrizzle() {
        return drizzle;
    }

    public void setDrizzle(String drizzle) {
        this.drizzle = drizzle;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOption_sum() {
        return option_sum;
    }

    public void setOption_sum(String option_sum) {
        this.option_sum = option_sum;
    }

    public String getMenu_cnt() {
        return menu_cnt;
    }

    public void setMenu_cnt(String menu_cnt) {
        this.menu_cnt = menu_cnt;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

}
