package com.example.kursov_project;

public class Ingridient {
    private String name;
    private String unit;
    private String count_;

    public Ingridient(String name, String unit, String count_) {
        this.name = name;
        this.unit = unit;
        this.count_ = count_;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount_() {
        return count_;
    }

    public void setCount_(int count_) {
        this.count_ = String.valueOf(count_);
    }
}
