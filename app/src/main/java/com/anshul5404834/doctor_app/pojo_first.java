package com.anshul5404834.doctor_app;

import java.util.List;

public class pojo_first  {
    List<specialtiy_pojo> specialtiy_pojos;
    String message;

    public List<specialtiy_pojo> getSpecialtiy_pojos() {
        return specialtiy_pojos;
    }

    public void setSpecialtiy_pojos(List<specialtiy_pojo> specialtiy_pojos) {
        this.specialtiy_pojos = specialtiy_pojos;
    }

    public pojo_first() {
    }

    public String getMessage() {
        return message;
    }

    public pojo_first(List<specialtiy_pojo> specialtiy_pojos, String message) {
        this.specialtiy_pojos = specialtiy_pojos;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
