package eci.arem.server;

import eci.arem.server.router.Router;
import eci.arem.server.router.route.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.io.*;
import java.util.List;
import java.util.Map;

public class HttpServer {
    private static boolean running = true;
    private static final Logger logger = LoggerFactory.getLogger(HttpRequest.class);

    public static void main(String[] args) throws IOException, URISyntaxException {
        FileResolver fileResolver = new FileResolver("src/main/resources/public");

        List<Route> routes = List.of(
                new BadMethodRoute(),
                new ShutDownRoute(() -> HttpServer.running = false),
                new StaticFileRoute(fileResolver),
                new BadRequestRoute()
        );
        Router router = new Router(routes);
        ServerSocket serverSocket = new ServerSocket(35000);

        while (running) {
            logger.info("Ready to receive...");
            Socket clientSocket = serverSocket.accept();
            OutputStream out = clientSocket.getOutputStream();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            HttpRequest request = HttpRequest.parse(in);
            Map<String, byte[]> response = router.route(request);

            out.write(response.get("headers"));
            out.write(response.get("body"));

            out.flush();
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }


}