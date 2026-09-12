package eci.arem.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileResolver {

    private final Path basePath;

    public FileResolver(String basePath) {
        this.basePath = Paths.get(basePath).toAbsolutePath().normalize();
    }

    public byte[] getFileBytes(String path) throws IOException {
        Path pathFile = basePath.resolve("." + path).normalize();

        if (!pathFile.startsWith(basePath) || !Files.exists(pathFile) || Files.isDirectory(pathFile)) {
            throw new FileNotFoundException();
        }
        return Files.readAllBytes(pathFile);
    }

    public static String getFileType(String path){
        if(path.endsWith(".js")) return "application/javascript";
        else if(path.endsWith(".css")) return "text/css";
        else if(path.endsWith(".png")) return "image/png";
        else if(path.endsWith(".jpg")) return "image/jpeg";
        else if(path.endsWith(".json")) return "application/json";
        else if(path.endsWith(".html")) return "text/html";
        else return null;
    }


}
