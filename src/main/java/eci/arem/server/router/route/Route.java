package eci.arem.server.router.route;

import eci.arem.server.HttpRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public interface Route {

    boolean matches(HttpRequest request);
    Map<String, byte[]> handle(HttpRequest request) throws IOException;

    static String getFirstParam(String query, String key) {
        if (query == null) return null;
        for (String pair : query.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2 && parts[0].equals(key)) {
                return java.net.URLDecoder.decode(parts[1], StandardCharsets.UTF_8);
            }
        }
        return null;
    }

    static String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }

}
