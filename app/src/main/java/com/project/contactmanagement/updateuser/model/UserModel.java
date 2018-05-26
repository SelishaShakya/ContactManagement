package com.project.contactmanagement.updateuser.model;

/**
 * Created by Own on 4/11/2018.
 */

public class UserModel {

    public String userId;
    public String fullname;
    public String address;
    public String email;
    public String contact;

    public UserModel(String fullname, String address, String email, String contact) {
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.contact = contact;
    }

    public UserModel(String userId, String fullname, String address, String email, String contact) {
        this.userId = userId;
        this.fullname = fullname;
        this.address = address;
        this.email = email;
        this.contact = contact;
    }

    public String getUserId() {
        return userId;
    }

    public String getFullname() {
        return fullname;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }
}
