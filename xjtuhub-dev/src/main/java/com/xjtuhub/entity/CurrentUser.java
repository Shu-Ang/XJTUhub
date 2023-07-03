package com.xjtuhub.entity;

public class CurrentUser {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CurrentUser(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
