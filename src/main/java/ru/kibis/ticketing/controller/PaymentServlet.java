package ru.kibis.ticketing.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import ru.kibis.ticketing.model.Hall;
import ru.kibis.ticketing.service.ValidateService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Сервлет для оплаты и бронирования места в зале
 */
public class PaymentServlet extends HttpServlet {
    /**
     * Сервис валидации
     */
    private final ValidateService validateService = ValidateService.getInstance();

    /**
     * Метод GET извлекает из HTTP запроса параметр id места в зале,
     * вызывает getPlaceById(int id) сервиса валидации, для получения объекта "место в зале",
     * в качестве JSON объекта передает в ответ номер места в ряду и номер ряда.
     *
     * @param req  HTTP запрос
     * @param resp ответ
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.valueOf(req.getParameter("id"));
        Hall place = validateService.getPlaceById(id);
        JSONObject json = new JSONObject();
        json.put("row", place.getRow());
        json.put("place", place.getPlace());
        this.send(json, resp);
    }

    /**
     * Метод POST извлекает из HTTP запроса параметр id места в зале,
     * вызывает booking(placeId, name, phone) сервиса валидации, для получения получения подтверждения о бронировании,
     * в качестве JSON объекта передает в ответ true или false как результат бронирования.
     *
     * @param req  HTTP запрос
     * @param resp ответ
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int placeId = Integer.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        boolean result = validateService.booking(placeId, name, phone);
        JSONObject json = new JSONObject();
        json.put("result", result);
        this.send(json, resp);
    }

    /**
     * Метод для отправки ответа в AJAX, использует ObjectMapper
     *
     * @param json JSON объект
     * @param resp ответ
     * @throws IOException
     */
    private void send(JSONObject json, HttpServletResponse resp) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = mapper.writeValueAsString(json);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(jsonInString);
        writer.flush();
    }
}
