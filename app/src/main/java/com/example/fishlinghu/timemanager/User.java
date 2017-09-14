package com.example.fishlinghu.timemanager;

/**
 * Created by fishlinghu on 9/11/17.
 */

public class User {

    private String name;
    private String photoUrl;

    public User() {
    }

    public User(String name, String photoUrl) {

        this.name = name;
        this.photoUrl = photoUrl;

    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }
}