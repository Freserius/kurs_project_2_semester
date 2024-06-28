package com.example.kursov_project;

public class dishEating {
    private int id;
    private int dish_id;
    private int eating_id;
    public dishEating(int id, int dish_id, int eating_id){
        this.dish_id = dish_id;
        this.eating_id = eating_id;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEating_id() {
        return eating_id;
    }

    public void setEating_id(int eating_id) {
        this.eating_id = eating_id;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }
}
