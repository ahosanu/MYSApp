package com.mysapp.Category;

public class CategorySelection {
    String Name;
    int id;

    public CategorySelection(String name, int id) {
        Name = name;
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
