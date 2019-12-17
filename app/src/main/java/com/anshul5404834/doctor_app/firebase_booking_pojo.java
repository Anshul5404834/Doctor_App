package com.anshul5404834.doctor_app;

public class firebase_booking_pojo {
String code;
String time;
String userid;
String docid;

    public firebase_booking_pojo() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public firebase_booking_pojo(String code, String time, String userid, String docid, Boolean paid) {
        this.code = code;
        this.time = time;
        this.userid = userid;
        this.docid = docid;
        this.paid = paid;
    }

    Boolean paid;

}
