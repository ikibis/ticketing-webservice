package ru.kibis.ticketing.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import ru.kibis.ticketing.model.Hall;
import ru.kibis.ticketing.service.ValidateService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Сервлет поиска мест в зале
 */
public class HallServlet extends HttpServlet {

    /**
     * Сервис валидации
     */
    private final ValidateService validateService = ValidateService.getInstance();

    /**
     * Метод POST, вызывает findPlaces() сервиса валидации, для получения List<Hall> всех объектов "место в зале",
     * В качестве ответа передает JSON массив содежащий id места в зале, номер ряда,
     * номер места в ряду, и его статус занято или нет.
     *
     * @param req  HTTP запрос
     * @param resp ответ
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JSONArray array = new JSONArray();
        List<Hall> places = validateService.findPlaces();
        for (Hall place : places) {
            JSONObject json = new JSONObject();
            json.put("id", place.getPlaceId());
            json.put("row", place.getRow());
            json.put("place", place.getPlace());
            if (place.isAvailable()) {
                json.put("availability", "Available");
            } else {
                json.put("availability", "Busy");
            }
            array.add(json);
        }
        String jsonInString = mapper.writeValueAsString(array);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(jsonInString);
        writer.flush();
    }
}
