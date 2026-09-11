package eci.arem.server;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class URLReader {
    public static void main(String[] args) throws Exception {
        URL siteURL = new URI("http://www.google.com/").toURL();


        //Se configura una instancia URLConnection configurada para comunicarse con el destino remoto.
        URLConnection connection = siteURL.openConnection();

        //Se recibe un mapa de los encabezados de la respuesta.
        Map<String, List<String>> headers = connection.getHeaderFields();
        //Se recorre el mapa de encabezados y convierten cada valor en una lista.
        Set<Map.Entry<String, List<String>>> entries = headers.entrySet();

        for (Map.Entry<String, List<String>> entry : entries) {
            String headerName = entry.getKey();
            // A null name represents the HTTP status line.
            if (headerName != null) System.out.print(headerName + ":");
            for (String value : entry.getValue()) System.out.print(value);
            System.out.println();
        }


        try (BufferedReader reader = new BufferedReader(
                //Abre el flujo de entrada para recibir los bytes del cuerpo de la respuesta del servidor.
                new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}