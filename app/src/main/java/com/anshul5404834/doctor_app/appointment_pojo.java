package com.anshul5404834.doctor_app;

public class appointment_pojo {
    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    String docid;
    Boolean paid;
    String userid;

    public appointment_pojo() {
    }

    public appointment_pojo(String docid, String userid, String time, String code, Boolean paid) {
        this.docid = docid;
        this.userid = userid;
        this.time = time;
        this.code = code;
        this.paid=paid;
    }

    String time;
    String code;
}
