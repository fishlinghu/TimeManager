package com.example.fishlinghu.timemanager;

/**
 * Created by fishlinghu on 9/13/17.
 */

public class Tomato {

    private String category;
    private Integer minutes;

    public Tomato() {
    }

    public Tomato(String category, Integer minutes) {

        this.category = category;
        this.minutes = minutes;

    }

    public String getCategory() {
        return category;
    }

    public Integer getMinutes() {
        return minutes;
    }
}
