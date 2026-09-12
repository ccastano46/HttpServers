package eci.arem.server.router.route;

import eci.arem.server.HttpHeaderFactory;
import eci.arem.server.HttpRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class BadMethodRoute implements Route{
    @Override
    public boolean matches(HttpRequest request) {
        return !request.getMethod().equals("GET");
    }

    @Override
    public Map<String, byte[]> handle(HttpRequest request) throws IOException {
        String strBody = "{\"mensaje\":\"Bad request: method not allowed\"}";
        byte[] body = strBody.getBytes(StandardCharsets.UTF_8);
        byte[] headers = HttpHeaderFactory.badMethod(body.length);
        return Map.of("headers", headers, "body", body);
    }
}
