package com.example.taskmaster;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Filemanager {

    public static String[] getFirstRow(UserHandler user) throws IOException {
        Path fileLocation = Path.of("user/" + user.getUsername() + "/" + user.getUsername() + "_data.txt");

        try (BufferedReader reader = Files.newBufferedReader(fileLocation)) {
            return reader.readLine().split(";");
        }
    }

    public static String[] getFirstRow(RoomHandler room) throws IOException {
        Path fileLocation = Path.of("rooms/" + room.getRoomname() + "/" + room.getRoomname() + "_info.txt");

        try (BufferedReader reader = Files.newBufferedReader(fileLocation)) {
            return reader.readLine().split(";");
        }
    }

}
