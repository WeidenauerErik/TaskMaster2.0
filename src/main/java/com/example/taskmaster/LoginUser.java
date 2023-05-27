package com.example.taskmaster;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class LoginUser {

    public static boolean checkUser(UserHandler user) throws IOException, NoSuchAlgorithmException {
        if (Files.exists(Path.of("user/" + user.getUsername() + "/" + user.getUsername() + "_data.txt"))) {
            if (!(Filemanager.getFirstRow(user)[0].equals(PasswordEncryptor.encrypt(user.getPassword())))) {
                return false;
            }
            return true;
        }else {
            return false;
        }
    }
}
