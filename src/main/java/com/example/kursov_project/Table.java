package com.example.kursov_project;

public class Table {
    private int id;
    private int max_count_people;
    public Table(int id, int max_count_people){
        this.id = id;
        this.max_count_people = max_count_people;
    }

    public int getMax_count_people() {
        return max_count_people;
    }

    public void setMax_count_people(int mac_count_people) {
        this.max_count_people = mac_count_people;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
