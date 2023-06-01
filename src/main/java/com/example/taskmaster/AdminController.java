package com.example.taskmaster;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        System.out.println(makeUser());
    }
    public static String[] getPassword() throws IOException {
        Path fileLocation = Path.of("user/admin/admin.txt");

        try (BufferedReader reader = Files.newBufferedReader(fileLocation)) {
            return reader.readLine().split(";");
        }
    }

    public static String getbaninfo(String username) throws IOException {
        if (getFirstRow(username).length == 2) {
            return "ban";
        }
        return "unban";
    }

    public static String[] getFirstRow(String username) throws IOException {
        Path fileLocation = Path.of("user/" + username + "/" + username + "_data.txt");

        try (BufferedReader reader = Files.newBufferedReader(fileLocation)) {
            return reader.readLine().split(";");
        }
    }

    @PostMapping("after-banuser")
    public String afterbanning(Admin user,Model model) throws NoSuchAlgorithmException, IOException {
        afterbanning(user.username);
        model.addAttribute("adminuserlist",makeUser());
        return "AdminDashboard";
    }

    public static void afterbanning(String username) throws IOException {
        Path fileLocation = Path.of("user/" + username + "/" + username + "_data.txt");
        String line;
        try (BufferedReader reader = Files.newBufferedReader(fileLocation)) {
            line =  reader.readLine();
        }
        String[] array = line.split(";");
        if (array.length == 3) {
            Files.write(fileLocation, (array[0]+";"+array[1]).getBytes());
        }else {
            Files.write(fileLocation, (array[0]+";"+array[1]+";banned").getBytes());
        }
    }

    public static List<Admin> makeUser() throws NoSuchAlgorithmException, IOException {
        List<Admin> ausgabe= new ArrayList<>();
        List<String> ausgabegetOrdner = getOrdnerImUserVerzeichnis();
        int counter = 0;
        for (String tmp: ausgabegetOrdner) {
            ausgabe.add(new Admin(tmp,getbaninfo(tmp),String.valueOf(counter)));
            counter++;
        }
        return ausgabe;
    }

    public static List<String> getOrdnerImUserVerzeichnis() {
        List<String> ordnerListe = new ArrayList<>();

        File verzeichnis = new File(Path.of("user").toUri());

        File[] ordnerArray = verzeichnis.listFiles(File::isDirectory);

        if (ordnerArray != null) {
            for (File ordner : ordnerArray) {
                if (!("admin".equals(ordner.getName()))) {
                    ordnerListe.add(ordner.getName());
                }
            }
        }

        return ordnerListe;
    }

}
