package com.application.paramountfinefoods;

public class User {

    public String name,email,loc_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoc_id() {
        return loc_id;
    }

    public void setLoc_id(String loc_id) {
        this.loc_id = loc_id;
    }

    public String id;

    public User() {

    }

    public User(String name,String loc_id,String email) {
        this.name = name;
        this.loc_id = loc_id;
        this.email = email;
    }

}
