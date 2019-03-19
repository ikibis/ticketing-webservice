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

public class PaymentServlet extends HttpServlet {
    private final ValidateService validateService = ValidateService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.valueOf(req.getParameter("id"));
        ObjectMapper mapper = new ObjectMapper();
        Hall place = validateService.getPlaceById(id);
        JSONObject json = new JSONObject();
        json.put("row", place.getRow());
        json.put("place", place.getPlace());
        String jsonInString = mapper.writeValueAsString(json);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(jsonInString);
        writer.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int placeId = Integer.valueOf(req.getParameter("id"));
        String name = req.getParameter("name");
        String phone = req.getParameter("phone");
        ObjectMapper mapper = new ObjectMapper();
        boolean result = validateService.booking(placeId, name, phone);
        System.out.println(result);
        JSONObject json = new JSONObject();
        json.put("result", result);
        String jsonInString = mapper.writeValueAsString(json);
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append(jsonInString);
        writer.flush();

    }
}
