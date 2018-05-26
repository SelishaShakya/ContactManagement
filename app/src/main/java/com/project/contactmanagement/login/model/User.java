package com.project.contactmanagement.login.model;

/**
 * Created by Own on 4/10/2018.
 */

public class User {

    public String username;
    public String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString(){
        return "User [firstName=" + username + ", password=" + password + "]";
    }
}
