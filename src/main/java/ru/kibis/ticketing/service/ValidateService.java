package ru.kibis.ticketing.service;

import ru.kibis.ticketing.model.Hall;
import ru.kibis.ticketing.persistence.DbStore;

import java.util.List;

public class ValidateService {
    private final DbStore memory = DbStore.getInstance();

    private static class Holder {
        private static final ValidateService INSTANCE = new ValidateService();
    }

    public static ValidateService getInstance() {
        return Holder.INSTANCE;
    }

    public boolean booking(int placeId, String name, String phone) {
        return memory.booking(placeId, name, phone);
    }

    public List<Hall> findPlaces() {
        return memory.findPlaces();
    }

    public Hall getPlaceById(int id) {
        return memory.getPlaceById(id);
    }
}
