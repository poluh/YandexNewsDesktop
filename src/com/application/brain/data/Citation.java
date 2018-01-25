package com.application.brain.data;

public class Citation {
    private String text, image, info;


    public Citation(String text, String image, String info) {
        this.text = text;
        this.image = image;
        this.info = info;
    }

    public Citation() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return info + "\n" + text + "\nimage: " + image;
    }
}
