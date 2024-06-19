package com.example.kursov_project;

public class INgridDish {
    private int id;
    private String ingridient_name;
    private int dish_id;
    private int count;

    public INgridDish(String ingridient_name, int dish_id, int count){
        this.ingridient_name = ingridient_name;
        this.dish_id =dish_id;
        this.count = count;
    }
    public String getIngridient_name() {
        return ingridient_name;
    }

    public void setIngridient_name(String ingridient_name) {
        this.ingridient_name = ingridient_name;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDish_id() {
        return dish_id;
    }

    public void setDish_id(int dish_id) {
        this.dish_id = dish_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



}
