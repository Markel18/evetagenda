package com.example.evetagenda.model;

public class UserInfo {
    public int id;
    public String fnameUsers;
    public String lnameUsers;
    public String emailUsers;
    public long phoneUsers;
    public int aFMUsers;
    public String pwdUsers;
    public int isEmailConfirmed;
    public String token;

    public int getId() {
        return id;
    }

    public String getFnameUsers() {
        return fnameUsers;
    }

    public String getLnameUsers() {
        return lnameUsers;
    }

    public String getEmailUsers() {
        return emailUsers;
    }

    public long getPhoneUsers() {
        return phoneUsers;
    }

    public int getaFMUsers() {
        return aFMUsers;
    }

    public String getPwdUsers() {
        return pwdUsers;
    }

    public int getIsEmailConfirmed() {
        return isEmailConfirmed;
    }

    public String getToken() {
        return token;
    }
}
