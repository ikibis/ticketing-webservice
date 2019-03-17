package ru.kibis.ticketing.service;

import ru.kibis.ticketing.model.Hall;
import ru.kibis.ticketing.model.User;
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

    public boolean booking(User user) {
        boolean result = false;
        if (user.getName() != null && user.getPhoneNumber() != 0) {
            result = memory.booking(user);
        }
        return result;
    }

    public List<Hall> findPlaces() {
        return memory.findPlaces();
    }
}
