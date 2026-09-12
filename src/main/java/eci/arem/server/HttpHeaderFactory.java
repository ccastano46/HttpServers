package eci.arem.server;

import java.nio.charset.StandardCharsets;

public class HttpHeaderFactory {

    private static final String crlf = "\r\n";
    private static final String end = "\r\n\r\n";



    public static byte[] ok(String fileType, int contentLength){
        String output = "HTTP/1.1 200 OK" + crlf
                + "Content-Type: " + fileType + crlf
                + "Content-Length: " + contentLength + end;
        return output.getBytes(StandardCharsets.UTF_8);

    }

    public static byte[] notFound(int contentLength){
        String output = "HTTP/1.1 404 Not Found" +crlf
                + "Content-Type: application/json" + crlf
                + "Content-Length: " + contentLength + end;
        return output.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] badMethod(int contentLength){
        String output = "HTTP/1.1 405 Method Not Allowed" + crlf
                + "Content-Type: application/json" + crlf
                + "Allow: GET" + crlf
                + "Content-Length: " + contentLength + end;
        return output.getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] badRequest(int contentLength){
        String output = "HTTP/1.1 400 Bad Request" + crlf
                + "Content-Type: application/json" + crlf
                + "Content-Length: " + contentLength + end;
        return output.getBytes(StandardCharsets.UTF_8);
    }

}
