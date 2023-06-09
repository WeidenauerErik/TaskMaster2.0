package com.example.taskmaster;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class UserHandler {

    private String username;
    private String password;
    private String permission;

    public UserHandler(String username, String password, String permission) throws NoSuchAlgorithmException {
        this.username = username;
        this.password = PasswordEncryptor.encrypt(password);
        this.permission = permission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static String getPermissionwithUser(UserHandler user) throws IOException {
        return Filemanager.getFirstRow(user)[1];
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "[" + username + "] [" + password + "] [" + permission + "]";
    }

}
