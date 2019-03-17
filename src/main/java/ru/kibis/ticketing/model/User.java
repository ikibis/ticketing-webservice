package ru.kibis.ticketing.model;

public class User {
    private String name;
    private int phoneNumber;
    private Hall place;

    public User(String name, int phoneNumber, Hall place) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Hall getPlace() {
        return place;
    }

}
