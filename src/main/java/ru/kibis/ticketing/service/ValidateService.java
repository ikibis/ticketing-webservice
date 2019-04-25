package ru.kibis.ticketing.service;

import ru.kibis.ticketing.model.Hall;
import ru.kibis.ticketing.persistence.DbStore;

import java.util.List;

/**
 * Класс сервиса валидации
 */
public class ValidateService {

    /**
     * Экземпляр хранилища
     */
    private final DbStore memory = DbStore.getInstance();

    private static class Holder {
        private static final ValidateService INSTANCE = new ValidateService();
    }

    /**
     * Метод используется для доступа к слою валидации из сервлетов
     *
     * @return объект ValidateService
     */
    public static ValidateService getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * Метод бронирования места в зале.
     * Проверяет является ли выбранное место доступным для бронирования,
     * если место доступно, то осуществляется вызов booking(placeId, name, phone).
     * Если место не доступно, то возвращает false.
     * В ответ возвращает true или false
     *
     * @param placeId айди места в зале
     * @param name имя пользователя
     * @param phone номер телефона пользователя
     * @return
     */
    public boolean booking(int placeId, String name, String phone) {
        boolean result;
        if (!memory.getPlaceById(placeId).isAvailable()) {
            result = false;
        } else {
            result = memory.booking(placeId, name, phone);
        }
        return result;
    }

    /**
     * Метод поиска всех мест в зале
     *
     * @return List<Hall> список мест
     */
    public List<Hall> findPlaces() {
        return memory.findPlaces();
    }

    /**
     * Метод для поиска Hall (места в зале) по id
     *
     * @param id айди места в зале
     * @return Объект Hall
     */
    public Hall getPlaceById(int id) {
        return memory.getPlaceById(id);
    }
}
