package com.example.taskmaster;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        UserHandler user = new UserHandler("Erik", "123", "student");
        getTasks(user);
    }

    public static void addTask(Task task, UserHandler user) throws IOException {
        Path fileLocation = Path.of("user/" + user.getUsername() + "/" + user.getUsername() + "_privateTasks.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(fileLocation, StandardOpenOption.APPEND)) {
            writer.write(task.getTitle() + System.lineSeparator());
            writer.write(task.getInfo() + System.lineSeparator());
            writer.write(task.getDeadline() + System.lineSeparator());

        }
    }

    public static void deleteTask(Task task, UserHandler user) throws IOException {
        Path fileLocation = Path.of("user/" + user.getUsername() + "/" + user.getUsername() + "_privateTasks.txt");
        List<Task> ausgabe = getTasks(user);
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

    public static List<Task> getTasks(UserHandler user) throws IOException {
        List<Task> ausgabe = new ArrayList<>();
        List<String> ausgabereadFiletoList = readFileToList(user);
        int counter = 0;
        for (int i = 0; i < ausgabereadFiletoList.size(); i += 3) {

            ausgabe.add(new Task(ausgabereadFiletoList.get(i), ausgabereadFiletoList.get(i + 1), ausgabereadFiletoList.get(i + 2),String.valueOf(counter)));
            counter++;
        }
        return ausgabe;
    }

    public static List<String> readFileToList(UserHandler user) {
        List<String> lines = new ArrayList<>();
        Path fileLocation = Path.of("user/" + user.getUsername() + "/" + user.getUsername() + "_privateTasks.txt");
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
