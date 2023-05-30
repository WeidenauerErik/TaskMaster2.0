package com.example.taskmaster;

import org.apache.catalina.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    UserHandler staticuser;

    @PostMapping("/")
    public String howtouse() {
        return "HowtoUse";
    }

    @GetMapping("/login")
    public String getlogin() {
        return "LoginUser";
    }

    @PostMapping("/login")
    public String postlogin() {
        return "LoginUser";
    }

    @GetMapping("/signup")
    public String getsignup() {
        return "RegisterUser";
    }

    @PostMapping("/signup")
    public String postsignup() {
        return "RegisterUser";
    }

    @PostMapping("/after-register")
    public String afterRegister(@ModelAttribute UserHandler user, Model model) throws IOException, NoSuchAlgorithmException {
        model.addAttribute("wrongsignup","");
        model.addAttribute("wronglogin", "");
        if (RegisterUser.newUser(user) == false) {
            model.addAttribute("wrongsignup", "This User is already registered!");
            return "RegisterUser";
        }
        List<Connects> ausgabe = RoomManager.getConnects(user);
        Collections.reverse(ausgabe);
        model.addAttribute("connects", ausgabe);
        staticuser = user;
        return "Room-List";
    }

    @PostMapping("/after-login")
    public String afterLogin(@ModelAttribute UserHandler user, Model model) throws IOException, NoSuchAlgorithmException {
        model.addAttribute("wrongsignup","");
        model.addAttribute("wronglogin", "");
        if (LoginUser.checkUser(user) == false) {
            model.addAttribute("wronglogin", "Login data is not valid!");
            return "LoginUser";
        }
        List<Connects> ausgabe = RoomManager.getConnects(user);
        Collections.reverse(ausgabe);
        model.addAttribute("connects", ausgabe);
        staticuser = user;
        return "Room-List";
    }

    @PostMapping("/after-connect")
    public String afterconnect(@ModelAttribute RoomHandler room,Model model) throws IOException, NoSuchAlgorithmException {
        model.addAttribute("wrongconnectdata", "");
        model.addAttribute("wrongmakedata","");
        if (!(RoomManager.checkRoom(room,staticuser))) {
            model.addAttribute("wrongconnectdata", "Login data is not valid!");
            return "Room-List";
        }
        RoomManager.addRoomtoUser(room,staticuser);

        if (Filemanager.getFirstRow(room)[0].equals(staticuser.getUsername())) {
            return "Room-Tasks-Admin";
        }
        if (Filemanager.getFirstRow(staticuser)[1].equals("teacher")) {
            return "Room-Tasks-Admin";
        }
        return "Room-Tasks-Normal";
    }

    @PostMapping("/after-create")
    public String afterCreate(@ModelAttribute RoomHandler room,Model model) throws NoSuchAlgorithmException, IOException {
        model.addAttribute("wrongconnectdata", "");
        model.addAttribute("wrongmakedata","");
        if (!(RoomManager.createRoom(room,staticuser))) {
            model.addAttribute("wrongmakedata","This room does already exists!");
            return "Room-List";
        }
        RoomManager.addRoomtoUser(room,staticuser);
        return "Room-Tasks-Admin";
    }

    @PostMapping("/after-delete")
    public String afterdeleteconnects(Model model) throws IOException {
        model.addAttribute("wrongsignup","");
        model.addAttribute("wronglogin", "");
        RoomManager.deleteGroups(staticuser);
        model.addAttribute("connects", new ArrayList<>());
        return "Room-List";
    }

    @PostMapping("/after-back-login")
    public String afterbackroomlist(Model model) {
        model.addAttribute("wrongsignup","");
        model.addAttribute("wronglogin", "");
        return "LoginUser";
    }

    @PostMapping("after-back-room-list")
    public String afterbackroomlist() {
        return "Room-List";
    }

    @PostMapping("/after-connect-privateTasks")
    public String afterconnectprivateTask(Model model) throws IOException {
        model.addAttribute("listtask", "");
        model.addAttribute("listtask", TaskManager.getTasks(staticuser));
        return "PrivateTask";
    }

    @PostMapping("/after-new")
    public String afternewprivateTask(Task task, Model model) throws IOException{
        TaskManager.addTask(task,staticuser);
        model.addAttribute("listtask", "");
        model.addAttribute("listtask", TaskManager.getTasks(staticuser));
        return "PrivateTask";
    }

    @PostMapping("after-delete-privateTasks")
    public String afterdeleteprivateTask(Task task, Model model) throws IOException {
        System.out.println(task);
        TaskManager.deleteTask(task,staticuser);
        model.addAttribute("listtask", TaskManager.getTasks(staticuser));
        return "PrivateTask";
    }

}
