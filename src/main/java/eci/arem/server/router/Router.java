package eci.arem.server.router;

import eci.arem.server.HttpRequest;
import eci.arem.server.router.route.Route;


import java.io.IOException;

import java.util.List;
import java.util.Map;

public class Router {
    private final List<Route> routes;

    public Router(List<Route> routes) {
        this.routes = routes;

    }

    public Map<String, byte[]> route(HttpRequest request) throws IOException {
        for (Route route : routes) {
            if (route.matches(request)) {
                return route.handle(request);
            }
        }
        throw new IllegalStateException("No route matched — ensure a fallback route");
    }
}