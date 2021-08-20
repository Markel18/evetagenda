package com.example.evetagenda.model;

public class UserInfoResponse {
    private Error error;
    private UserInfo userInfo;

    public Error getError() {
        return error;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
