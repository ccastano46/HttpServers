package eci.arem.server;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class URLParser {
    public static void main(String[] args) throws URISyntaxException, MalformedURLException{
        URL myURL = new URI("https://respuestasarep.escuelaing.edu.co:6789/resp2026.txt?val=9&e=8#projects").toURL();
        System.out.println("getProtocol: " + myURL.getProtocol());
        System.out.println("getAuthority: " + myURL.getAuthority());
        System.out.println("getHost: " + myURL.getHost());
        System.out.println("getPort: " + myURL.getPort());
        System.out.println("getPath: " + myURL.getPath());
        System.out.println("getQuery: " + myURL.getQuery());
        System.out.println("getFile: " + myURL.getFile());
        System.out.println("getRef: " + myURL.getRef());
    }
}
