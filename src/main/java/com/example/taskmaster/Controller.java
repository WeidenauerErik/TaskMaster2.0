package com.example.taskmaster;

import org.apache.catalina.User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@org.springframework.stereotype.Controller
public class Controller {
    UserHandler staticuser = new UserHandler();

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
        model.addAttribute("connects", RoomManager.getConnects(user));
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
        model.addAttribute("connects", RoomManager.getConnects(user));
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
        return "Room";
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
        return "Room";
    }

    @PostMapping("/after-delete")
    public String afterdeleteconnects(Model model) throws IOException {
        model.addAttribute("wrongsignup","");
        model.addAttribute("wronglogin", "");
        RoomManager.deleteGroups(staticuser);
        model.addAttribute("connects", new ArrayList<>());
        return "Room-List";
    }
}