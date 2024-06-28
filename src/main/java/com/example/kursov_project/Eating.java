package com.example.kursov_project;

import javafx.scene.chart.PieChart;

import java.sql.Date;
import java.sql.Time;

public class Eating {
    private int id;
    private int table_id;
    private int inn;
    private int officiant_id;
    private String date_;
    private String time_begin;
    private String time_end;


    public Eating(int id, int table_id, int inn, int officiant_id, String date_, String time_begin, String time_end) {
        this.id = id;
        this.table_id = table_id;
        this.inn = inn;
        this.officiant_id = officiant_id;
        this.date_ = date_;
        this.time_begin = time_begin;
        this.time_end = time_end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getTime_begin() {
        return time_begin;
    }

    public void setTime_begin(String time_begin) {
        this.time_begin = time_begin;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getOfficiant_id() {
        return officiant_id;
    }

    public void setOfficiant_id(int officiant_id) {
        this.officiant_id = officiant_id;
    }

    public int getInn() {
        return inn;
    }

    public void setInn(int inn) {
        this.inn = inn;
    }



    public String getDate_() {
        return date_;
    }

    public void setDate_(String date_) {
        this.date_ = date_;
    }
}
