package com.example.taskmaster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    public static boolean checkRoom(RoomHandler room, UserHandler user) throws IOException, NoSuchAlgorithmException {
        if (!Files.exists(Path.of("rooms/" + room.getRoomname()))) {
            return false;
        }
        if (!(PasswordEncryptor.encrypt(room.getPassword()).equals(Filemanager.getFirstRow(room)[1]))) {
            return false;
        }
        return true;
    }

    public static boolean createRoom(RoomHandler room, UserHandler user) throws NoSuchAlgorithmException, IOException {
        if (Files.exists(Path.of("rooms/" + room.getRoomname()))) {
            return false;
        }
        Files.createDirectories(Path.of("rooms/" + room.getRoomname()));
        Files.createFile(Path.of("rooms/" + room.getRoomname()+"/"+room.getRoomname()+"_info.txt"));
        Files.createFile(Path.of("rooms/" + room.getRoomname()+"/"+room.getRoomname()+"_user.txt"));
        Files.write(Path.of("rooms/" + room.getRoomname()+"/"+room.getRoomname() + "_info.txt"), (user.getUsername() + ";" + PasswordEncryptor.encrypt(room.password)).getBytes());
        Files.write(Path.of("rooms/" + room.getRoomname()+"/"+room.getRoomname()+"_user.txt"),(user.getUsername()).getBytes());
        Files.createFile(Path.of("rooms/"+room.getRoomname()+"/"+room.getRoomname()+"_tasks.txt"));
        return true;
    }

    public static void addRoomtoUser(RoomHandler room,UserHandler user) throws IOException {
        Path fileLocation = Path.of("user/" + user.getUsername() + "/" + user.getUsername() + "_groups.txt");
        List<String> getOwner = getOwner(user);
        List<String> getRooms = getRooms(user);
        boolean counter = true;
        for (int i = 0; i < getOwner.size(); i++) {
            if (getRooms.get(i).equals(room.getRoomname())){
                counter = false;
            }
        }
        if (counter == true) {
            try (BufferedWriter writer = Files.newBufferedWriter(fileLocation, StandardOpenOption.APPEND)) {
                writer.write(room.getRoomname() + System.lineSeparator());
                writer.write(Filemanager.getFirstRow(room)[0] + System.lineSeparator());
            }
        }
    }

    public static List<String> getRooms(UserHandler user) throws IOException {
        List<String> lastconnects = new ArrayList<>();
        Path fileLocation = Path.of("user/" + user.getUsername() + "/" + user.getUsername() + "_groups.txt");
        String line;
        try (BufferedReader reader = Files.newBufferedReader(fileLocation)) {
            while((line = reader.readLine()) != null) {
                lastconnects.add(line);
                reader.readLine();
            }
        }
        return lastconnects;
    }


    public static List<String> getOwner(UserHandler user) throws IOException {
        List<String> lastconnects = new ArrayList<>();
        Path fileLocation = Path.of("user/" + user.getUsername() + "/" + user.getUsername() + "_groups.txt");
        String line;
        try (BufferedReader reader = Files.newBufferedReader(fileLocation)) {
            reader.readLine();
            while((line = reader.readLine()) != null) {
                lastconnects.add(line);
                reader.readLine();
            }
        }
        System.out.println(lastconnects);
        return lastconnects;
    }

    public static List<Connects> getConnects(UserHandler user) throws IOException {
        List<Connects> connects = new ArrayList<>();
        List<String> getRooms = getRooms(user);
        List<String> getOwner = getOwner(user);
        for (int i = 0; i < getOwner.size(); i++) {
            connects.add(new Connects(getOwner.get(i),getRooms.get(i)));
        }
        return connects;
    }

    public static void deleteGroups(UserHandler user) throws IOException {
        Path fileLocation = Path.of("user/" + user.getUsername() + "/" + user.getUsername() + "_groups.txt");
        Files.delete(fileLocation);
        Files.createFile(fileLocation);
    }
}
