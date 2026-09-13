package eci.arem.server.router.route;

import eci.arem.server.HttpHeaderFactory;
import eci.arem.server.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ShutDownRoute implements Route{

    private final Runnable onShutdown;
    private static final Logger logger = LoggerFactory.getLogger(ShutDownRoute.class);


    public ShutDownRoute(Runnable onShutdown) {
        this.onShutdown = onShutdown;
    }

    @Override
    public boolean matches(HttpRequest request) {
        return request.getPath().equals("/shutdown");
    }

    @Override
    public Map<String, byte[]> handle(HttpRequest request) throws IOException {
        onShutdown.run();
        logger.info("Shutting down server");

        String strBody = "{\"mensaje\":\"" + request.getPath() + "\"}";
        byte[] body = strBody.getBytes(StandardCharsets.UTF_8);
        byte[] headers = HttpHeaderFactory.ok("application/json",body.length);
        return Map.of("headers", headers, "body", body);
    }


}
