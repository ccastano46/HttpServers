package eci.arem.server;

import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
    static boolean running = true;

    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = new ServerSocket(35000);
        while (running) {
            System.out.println("Ready to receive...");
            Socket clientSocket = serverSocket.accept();

            OutputStream out = clientSocket.getOutputStream();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            boolean isFirstLine = true;
            String URIstr = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (isFirstLine) {
                    URIstr = inputLine.split(" ")[1];

                    isFirstLine = false;
                }

                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            Map<String,byte[]> response = output(URIstr);
            out.write(response.get("headers"));
            out.write(response.get("body"));
            out.flush();

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    private static byte[] getFileBytes(String path) throws IOException {
        Path basePath = Paths.get("src/main/resources/public").toAbsolutePath().normalize();
        Path pathFile = basePath.resolve("." + path).normalize();

        if (!pathFile.startsWith(basePath) || !Files.exists(pathFile) || Files.isDirectory(pathFile)) {
            throw new FileNotFoundException();
        }
        return Files.readAllBytes(pathFile);
    }

    private static String getFileType(String path){
        if(path.endsWith(".js")) return "application/javascript";
        else if(path.endsWith(".css")) return "text/css";
        else if(path.endsWith(".png")) return "image/png";
        else if(path.endsWith(".jpg")) return "image/jpeg";
        else return "text/html";
    }

    private static byte[] buildHeaders(String path, int contentLength){
        String output = "";
        String end = "\r\n\r\n";
        if(path.startsWith("/shutdown")) {
            running = false;
            output = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: application/json\r\n"
                    + "Content-Length: " + contentLength + end;
        } else if(path.equals("/notFound")) {
            output = "HTTP/1.1 404 Not Found\r\n"
                    + "Content-Type: application/json\r\n"
                    + "Content-Length: " + contentLength + end;
        } else {
            output = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: " + getFileType(path) + "\r\n"
                    + "Content-Length: " + contentLength + end;
        }
        return output.getBytes(StandardCharsets.UTF_8);

    }


    private static Map<String, byte[]> output(String path) throws URISyntaxException, IOException {
        String cleanPath = new URI(path).getPath();
        Map<String, byte[]> response = new HashMap<>();
        byte[] headers;
        byte[] body;
        String strBody;
        try{
            if (cleanPath.startsWith("/shutdown")){
                strBody = "{\"mensaje\":\"Goodbye World\"}";
                body = strBody.getBytes(StandardCharsets.UTF_8);

            }else if(cleanPath.equals("/")){
                cleanPath = "/index.html";
                body = getFileBytes(cleanPath);
            } else {
                body = getFileBytes(cleanPath);
            }
            headers = buildHeaders(cleanPath,body.length);
        } catch (FileNotFoundException e){
            strBody = "{\"mensaje\":\"file not found: " + cleanPath + "\"}";
            body = strBody.getBytes(StandardCharsets.UTF_8);
            headers = buildHeaders("/notFound",body.length);
        }

        response.put("headers", headers);
        response.put("body", body);
        return response;

    }
}