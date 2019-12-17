package com.anshul5404834.doctor_app;

public class age_gender_pojo {
    String age;

    public age_gender_pojo() {
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public age_gender_pojo(String age, String gender) {
        this.age = age;
        this.gender = gender;
    }

    String gender;
}
