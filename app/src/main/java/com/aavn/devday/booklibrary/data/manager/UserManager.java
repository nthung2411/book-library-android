package com.aavn.devday.booklibrary.data.manager;

import com.aavn.devday.booklibrary.data.model.User;

public class UserManager {
    private static UserManager instance;
    private User userInfo;

    private UserManager() {

    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public User getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(User userInfo) {
        this.userInfo = userInfo;
    }
}
