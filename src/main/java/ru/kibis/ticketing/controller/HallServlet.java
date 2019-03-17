package ru.kibis.ticketing.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import ru.kibis.ticketing.model.Hall;
import ru.kibis.ticketing.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class HallServlet extends HttpServlet {
    private final ValidateService validateService = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.getRequestDispatcher("/index.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Hall> places = validateService.findPlaces();
        List<JSONObject> list = new CopyOnWriteArrayList<>();
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
            list.add(json);
        }
        String jsonInString = mapper.writeValueAsString(list);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(jsonInString);
        writer.flush();
    }
}
