package com.example.taskmaster;

public class RoomHandler {
    String roomname;
    String password;

    public RoomHandler(String roomname, String password) {
        this.password = password;
        this.roomname = roomname;
    }

    public String getPassword() {
        return password;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }
}
