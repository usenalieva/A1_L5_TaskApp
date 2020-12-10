package com.example.taskapp.ui.home;

import java.io.Serializable;

public class Contact implements Serializable {
    private String name;

    public Contact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
