package eci.arem.server.router.route;

import eci.arem.server.HttpHeaderFactory;
import eci.arem.server.HttpRequest;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class GreetingRoute implements Route {
    @Override
    public boolean matches(HttpRequest request) {
        return request.getPath().equals("/greeting");
    }

    @Override
    public Map<String, byte[]> handle(HttpRequest request) {
        String name = Route.getFirstParam(request.getQuery(), "name");
        byte[] body;
        byte[] headers;

        if (name == null || name.isBlank()) {
            String strBody = "{\"mensaje\":\"Bad request: missing 'name' parameter\"}";
            body = strBody.getBytes(StandardCharsets.UTF_8);
            headers = HttpHeaderFactory.badRequest(body.length);
        } else {
            String strBody = "{\"mensaje\":\"Hello, " + Route.escapeJson(name) + "!\"}";
            body = strBody.getBytes(StandardCharsets.UTF_8);
            headers = HttpHeaderFactory.ok("application/json", body.length);
        }

        return Map.of("headers", headers, "body", body);
    }
}