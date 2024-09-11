package com.example.geoquiz;

import android.app.Application;

public class UserLogin extends Application {

    private static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
