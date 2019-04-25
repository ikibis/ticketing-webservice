package ru.kibis.ticketing.model;

/**
 * Класс пользователь
 */
public class User {
    private String name;
    private int phoneNumber;
    private int placeId;

    /**
     * Конструктор для создания пользователя
     * @param name имя пользователя
     * @param phoneNumber номер телефона пользователя
     * @param placeId айди места в зале, забронированное пользователем
     */
    public User(String name, int phoneNumber, int placeId) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.placeId = placeId;
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

    public int getPlaceId() {
        return placeId;
    }
}
