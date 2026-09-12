package eci.arem.server.router.route;

import eci.arem.server.HttpHeaderFactory;
import eci.arem.server.HttpRequest;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class BadRequestRoute implements Route {
    @Override
    public boolean matches(HttpRequest request) {
        return true;
    }

    @Override
    public Map<String, byte[]> handle(HttpRequest request) {
        String strBody = "{\"mensaje\":\"Bad request: " + request.getPath() + "\"}";
        byte[] body = strBody.getBytes(StandardCharsets.UTF_8);
        byte[] headers = HttpHeaderFactory.badRequest(body.length);
        return Map.of("headers", headers, "body", body);
    }
}