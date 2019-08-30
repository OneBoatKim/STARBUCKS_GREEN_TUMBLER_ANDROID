package com.example.menulist_test;

public class Alarm_data {
    String a_type;
    String a_content;
    String a_time;
    String a_orderid;

    public Alarm_data(String a_type, String a_content, String a_time, String a_orderid) {
        this.a_type = a_type;
        this.a_content = a_content;
        this.a_time = a_time;
        this.a_orderid = a_orderid;
    }

    public String getA_type() {
        return a_type;
    }

    public void setA_type(String a_type) {
        this.a_type = a_type;
    }

    public String getA_content() {
        return a_content;
    }

    public void setA_content(String a_content) {
        this.a_content = a_content;
    }

    public String getA_time() {
        return a_time;
    }

    public void setA_time(String a_time) {
        this.a_time = a_time;
    }

    public String getA_orderid() {
        return a_orderid;
    }

    public void setA_orderid(String a_orderid) {
        this.a_orderid = a_orderid;
    }
}
