package eci.arem.server;

import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.slf4j.Logger;

public class HttpRequest {
    private final String method;
    private final String path;
    private final String query;
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    private HttpRequest(String method, String path, String query) {
        this.method = method;
        this.path = path;
        this.query = query;
    }

    public static HttpRequest parse(BufferedReader in) throws IOException, URISyntaxException {
        boolean isFirstLine = true;
        String method = "";
        String path = "";
        String query = "";
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            if (isFirstLine) {
                String[] parts = inputLine.split(" ");
                method = parts.length > 0 ? parts[0] : "";

                if (!method.equals("GET")) {
                    path = "/methodNotAllowed";
                } else if (parts.length > 1) {
                    URI uri = new URI(parts[1]);
                    path = uri.getPath();
                    query = uri.getQuery();
                } else {
                    path = "/badRequest";
                }
                isFirstLine = false;
            }

            logger.info("Received: {}", inputLine);
            if (!in.ready()) {
                break;
            }
        }

        if (method.isEmpty()) {
            path = "/badRequest";
        }

        return new HttpRequest(method, path, query);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }
}