package com.example.evetagenda.model;

import java.io.Serializable;

public class Event implements Serializable {
    public int id;
    public String start_event;
    public String end_event;
    public String title;
    public int prod_name;
    public String perioxi;
    public String aitiologia_episkepsis;
    public String prod_phone;
    public String color;
    public int uid;


    public int getId() {
        return id;
    }

    public String getStart_event() {
        return start_event;
    }

    public String getEnd_event() {
        return end_event;
    }

    public String getTitle() {
        return title;
    }

    public int getProd_name() {
        return prod_name;
    }

    public String getPerioxi() {
        return perioxi;
    }

    public String getAitiologia_episkepsis() {
        return aitiologia_episkepsis;
    }

    public String getProd_phone() {
        return prod_phone;
    }

    public String getColor() {
        return color;
    }

    public int getUid() {
        return uid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStart_event(String start_event) {
        this.start_event = start_event;
    }

    public void setEnd_event(String end_event) {
        this.end_event = end_event;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProd_name(int prod_name) {
        this.prod_name = prod_name;
    }

    public void setPerioxi(String perioxi) {
        this.perioxi = perioxi;
    }

    public void setAitiologia_episkepsis(String aitiologia_episkepsis) {
        this.aitiologia_episkepsis = aitiologia_episkepsis;
    }

    public void setProd_phone(String prod_phone) {
        this.prod_phone = prod_phone;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
