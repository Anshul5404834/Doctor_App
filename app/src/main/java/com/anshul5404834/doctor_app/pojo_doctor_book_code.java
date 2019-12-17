package com.anshul5404834.doctor_app;

public class pojo_doctor_book_code {
    String amount;
    String code;
    String docName;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getPatMob() {
        return patMob;
    }

    public void setPatMob(String patMob) {
        this.patMob = patMob;
    }

    public Boolean getPay() {
        return pay;
    }

    public void setPay(Boolean pay) {
        this.pay = pay;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public pojo_doctor_book_code() {
    }

    String docid;
    String patMob;

    public pojo_doctor_book_code(String amount, String code, String docName, String docid, String patMob, Boolean pay, String time) {
        this.amount = amount;
        this.code = code;
        this.docName = docName;
        this.docid = docid;
        this.patMob = patMob;
        this.pay = pay;
        this.time = time;
    }

    Boolean pay;
    String time;
}
