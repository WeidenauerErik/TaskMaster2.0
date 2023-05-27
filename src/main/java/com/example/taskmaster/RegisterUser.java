package com.example.taskmaster;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;

public class RegisterUser {

    public static boolean newUser(UserHandler user) throws IOException, NoSuchAlgorithmException {
        Path path = Path.of("user/"+user.getUsername());
        if (Files.exists(path)) {
            return false;
        } else {
            Files.createDirectories(path);
            if (Files.exists(Path.of(path + "/" + user.getUsername() + "_data.txt"))) {
                return false;
            }else {
                Files.createFile(Path.of(path + "/" + user.getUsername() + "_data.txt"));
                Files.write(Path.of(path + "/" + user.getUsername() + "_data.txt"), (PasswordEncryptor.encrypt(user.getPassword())+";"+user.getPermission()).getBytes());
            }
            if (Files.exists(Path.of(path + "/" + user.getUsername() + "_groups.txt"))) {
                return false;
            }else {
                Files.createFile(Path.of(path + "/" + user.getUsername() + "_groups.txt"));
            }
            if (Files.exists(Path.of(path + "/" + user.getUsername() + "_privateTasks.txt"))) {
                return false;
            }else {
                Files.createFile(Path.of(path + "/" + user.getUsername() + "_privateTasks.txt"));
            }
        }
        return true;
    }

}
