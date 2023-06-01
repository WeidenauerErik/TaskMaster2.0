package com.example.taskmaster;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class PrivateTaskManager {

    public static List<Task> sortTasks(List<Task> tasks) {
        if (tasks.size() > 1) {
            tasks.sort((o1, o2) -> {
                String[] date1, date2;
                try {
                    date1 = o1.getDeadline().split("-");
                } catch (NullPointerException e) {
                    return 1;
                }
                try {
                    date2 = o2.getDeadline().split("-");
                } catch (NullPointerException e) {
                    return -1;
                }

                if (date1.length < 2) {
                    return 1;
                } else if (date2.length < 2) {
                    return -1;
                }

                if (Integer.parseInt(date1[2]) < Integer.parseInt(date2[2])) {
                    return -1;
                } else if (Integer.parseInt(date1[1]) < Integer.parseInt(date2[1])) {
                    return -1;
                } else if (Integer.parseInt(date1[0]) < Integer.parseInt(date2[0])) {
                    return -1;
                } else {
                    return 1;
                }
            });
        }
        return tasks;
    }

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
