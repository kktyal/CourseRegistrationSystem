package com.example.realfinal;



public class IdUser {
    String userID;
    String userPassword;
    String userName;
    String userMajor;
    public IdUser(String userID, String userPassword, String userName, String userMajor) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userMajor = userMajor;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserMajor() {
        return userMajor;
    }
    public void setUserMajor(String userMajor) {
        this.userMajor = userMajor;
    }
}
