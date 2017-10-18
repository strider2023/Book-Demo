package com.touchmenotapps.bookdemo.dao;

/**
 * Created by i7 on 31-05-2017.
 */

public class QuestionDAO {

    private int image;
    private boolean isCorrect = false;

    public QuestionDAO(int image) {
        this.image = image;
    }

    public QuestionDAO(int image, boolean isCorrect) {
        this.image = image;
        this.isCorrect = isCorrect;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
