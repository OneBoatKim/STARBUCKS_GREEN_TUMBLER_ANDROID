package com.example.menulist_test;

public class Menu_data {
    String price;
    String menuId;
    int resId;

    public Menu_data(String price, int resId, String menuId) {
        this.price = price;
        this.resId = resId;
        this.menuId = menuId;
    }

    public String getPrice() {
        return price;
    }

    public int getResId() {
        return resId;
    }

    public String getMenuId() {
        return menuId;
    }
}


