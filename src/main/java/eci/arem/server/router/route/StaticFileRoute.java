package eci.arem.server.router.route;

import eci.arem.server.FileResolver;
import eci.arem.server.HttpHeaderFactory;
import eci.arem.server.HttpRequest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class StaticFileRoute implements Route{

    private FileResolver fileResolver;
    public StaticFileRoute(FileResolver fileResolver) {
        this.fileResolver = fileResolver;
    }
    @Override
    public boolean matches(HttpRequest request) {

        return request.getPath().equals("/") || FileResolver.getFileType(request.getPath()) != null;
    }

    @Override
    public Map<String, byte[]> handle(HttpRequest request) throws IOException {
        String path = request.getPath().equals("/") ? "/async-client.html" : request.getPath();
        byte[] body;
        byte[] headers;
        try{
            body = fileResolver.getFileBytes(path);
            headers = HttpHeaderFactory.ok(FileResolver.getFileType(path),body.length);
        } catch (FileNotFoundException e){
            String strBody = "{\"mensaje\":\"file not found: " + Route.escapeJson(path) + "\"}";
            body = strBody.getBytes(StandardCharsets.UTF_8);
            headers = HttpHeaderFactory.notFound(body.length);
        }

        return Map.of("headers", headers, "body", body);
    }
}
