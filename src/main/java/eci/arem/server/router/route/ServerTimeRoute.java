package eci.arem.server.router.route;

import eci.arem.server.HttpHeaderFactory;
import eci.arem.server.HttpRequest;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;

public class ServerTimeRoute implements Route {
    @Override
    public boolean matches(HttpRequest request) {
        return request.getPath().equals("/server-time");
    }

    @Override
    public Map<String, byte[]> handle(HttpRequest request) {
        String strBody = "{\"serverTime\":\"" + Instant.now() + "\"}";
        byte[] body = strBody.getBytes(StandardCharsets.UTF_8);
        byte[] headers = HttpHeaderFactory.ok("application/json", body.length);
        return Map.of("headers", headers, "body", body);
    }
}
