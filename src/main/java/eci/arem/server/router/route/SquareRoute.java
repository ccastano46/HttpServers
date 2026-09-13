package eci.arem.server.router.route;

import eci.arem.server.HttpHeaderFactory;
import eci.arem.server.HttpRequest;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class SquareRoute implements Route{
    @Override
    public boolean matches(HttpRequest request) {
        return request.getPath().equals("/square");
    }

    @Override
    public Map<String, byte[]> handle(HttpRequest request) {
        String rawValue = Route.getFirstParam(request.getQuery(), "value");
        byte[] body;
        byte[] headers;

        try {
            double value = Double.parseDouble(rawValue);
            double square = value * value;
            String strBody = "{\"value\":" + value + ",\"square\":" + square + "}";
            body = strBody.getBytes(StandardCharsets.UTF_8);
            headers = HttpHeaderFactory.ok("application/json", body.length);
        } catch (NumberFormatException | NullPointerException e) {
            String strBody = "{\"mensaje\":\"Bad request: missing or invalid 'value' parameter\"}";
            body = strBody.getBytes(StandardCharsets.UTF_8);
            headers = HttpHeaderFactory.badRequest(body.length);
        }

        return Map.of("headers", headers, "body", body);
    }
}
