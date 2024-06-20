package com.example.kursov_project;

public class Officaant {
    private int id;
    private String adress;
    private String phone_number;
    private String name;
    public Officaant(int id, String adress, String phone_number, String name){
        this.id = id;
        this.adress = adress;
        this.phone_number = phone_number;
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
