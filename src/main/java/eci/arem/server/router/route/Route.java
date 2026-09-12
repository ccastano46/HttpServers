package eci.arem.server.router.route;

import eci.arem.server.HttpRequest;

import java.io.IOException;
import java.util.Map;

public interface Route {

    boolean matches(HttpRequest request);
    Map<String, byte[]> handle(HttpRequest request) throws IOException;
}
