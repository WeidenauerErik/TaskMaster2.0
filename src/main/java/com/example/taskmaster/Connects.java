package com.example.taskmaster;

public class Connects {
    String roomname;
    String owner;

    public Connects(String owner, String roomname) {
        this.roomname = roomname;
        this.owner = owner;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public String getRoomname() {
        return roomname;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
