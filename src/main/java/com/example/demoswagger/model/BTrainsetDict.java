package com.example.demoswagger.model;

public class BTrainsetDict {

    private int age;

    private String name;

    private String gender;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public BTrainsetDict(int age, String name, String gender) {
        this.age = age;
        this.name = name;
        this.gender = gender;
    }
}
