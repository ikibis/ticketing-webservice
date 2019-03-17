package ru.kibis.ticketing.model;

public class Hall {
    private int placeId;
    private int row;
    private int place;
    private boolean available;

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
