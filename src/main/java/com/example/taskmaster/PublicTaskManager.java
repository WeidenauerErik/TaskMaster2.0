package com.example.taskmaster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class PublicTaskManager {

    public static void addTask(Task task, RoomHandler room) throws IOException {
        Path fileLocation = Path.of("rooms/" + room.getRoomname() + "/" + room.getRoomname() + "_tasks.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(fileLocation, StandardOpenOption.APPEND)) {
            writer.write(task.getTitle() + System.lineSeparator());
            writer.write(task.getInfo() + System.lineSeparator());
            writer.write(task.getDeadline() + System.lineSeparator());

        }
    }

    public static void deleteTask(Task task, RoomHandler room) throws IOException {
        Path fileLocation = Path.of("rooms/" + room.getRoomname() + "/" + room.getRoomname() + "_tasks.txt");
        List<Task> ausgabe = getTasks(room);
        try (BufferedWriter out = Files.newBufferedWriter(fileLocation)) {
            for (Task tmp : ausgabe) {
                if (!(task.getIndex().equals(tmp.getIndex()))) {
                    out.write(tmp.getTitle() + System.lineSeparator());
                    out.write(tmp.getInfo() + System.lineSeparator());
                    out.write(tmp.getDeadline() + System.lineSeparator());

                }
            }
        }

    }

    public static List<Task> getTasks(RoomHandler room) throws IOException {
        List<Task> ausgabe = new ArrayList<>();
        List<String> ausgabereadFiletoList = readFileToList(room);
        int counter = 0;
        for (int i = 0; i < ausgabereadFiletoList.size(); i += 3) {

            ausgabe.add(new Task(ausgabereadFiletoList.get(i), ausgabereadFiletoList.get(i + 1), ausgabereadFiletoList.get(i + 2),String.valueOf(counter)));
            counter++;
        }
        return ausgabe;
    }

    public static List<String> readFileToList(RoomHandler room) {
        List<String> lines = new ArrayList<>();
        Path fileLocation = Path.of("rooms/" + room.getRoomname() + "/" + room.getRoomname() + "_tasks.txt");
        try (BufferedReader reader = Files.newBufferedReader(fileLocation)) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
