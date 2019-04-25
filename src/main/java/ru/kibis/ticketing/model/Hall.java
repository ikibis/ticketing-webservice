package ru.kibis.ticketing.model;

/**
 * Класс зал, информация о местах в зале
 */
public class Hall {
    private int placeId;
    private int row;
    private int place;
    private boolean available;

    /**
     * Конструктор для места в зале
     *
     * @param placeId   id места в зале
     * @param row       номер ряда в зале
     * @param place     номер места в ряду
     * @param available Место доступно для бронирования или нет
     */
    public Hall(int placeId, int row, int place, boolean available) {
        this.placeId = placeId;
        this.row = row;
        this.place = place;
        this.available = available;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
