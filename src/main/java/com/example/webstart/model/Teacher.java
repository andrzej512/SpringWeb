package com.example.webstart.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(value = { "active" })
public class Teacher {
    private Long id;
    String name;
    String surname;
    List<String> subjects;
    boolean isActive;
    public Teacher(Long id, String name, String surname, List<String> subjects) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.subjects = subjects;
        this.isActive = true;
    }
    public Teacher(){}


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        System.out.println("setter from teacher class");
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public void setIsActive(boolean isActive) {
        isActive = isActive;
    }
}

