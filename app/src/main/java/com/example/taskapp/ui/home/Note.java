package com.example.taskapp.ui.home;

import java.io.Serializable;

public class Note implements Serializable {
    private String name;

    public Note(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
