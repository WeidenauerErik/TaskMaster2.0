package com.example.taskmaster;

public class Admin {
    String username;
    String bann;
    String index;

    public Admin(String username, String bann,String index) {
        this.bann = bann;
        this.username = username;
        this.index = index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "username='" + username + '\'' +
                ", bann='" + bann + '\'' +
                ", index='" + index + '\'' +
                '}';
    }

    public String getIndex() {
        return index;
    }

    public String getBann() {
        return bann;
    }

    public String getUsername() {
        return username;
    }

    public void setBann(String bann) {
        this.bann = bann;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
