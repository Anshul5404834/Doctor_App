package com.anshul5404834.doctor_app;

public class isssue_structure_pojo {
    String message;
    Object Images;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public isssue_structure_pojo() {
    }

    public Object getImages() {
        return Images;
    }

    public isssue_structure_pojo(String message, Object images) {
        this.message = message;
        Images = images;
    }

    public void setImages(Object images) {
        Images = images;
    }
}
