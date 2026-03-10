package com.sprint.mission.discodeit.service.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class FileUtil {
    public final Path directory;

    public FileUtil(String type) {
        this.directory = Paths.get(System.getProperty("user.dir"), "data", type);
        init(directory);
    }

    public static void init(Path directory) {
        if(!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create directory: " + directory, e);
            }
        }
    }

    public Path filePath(UUID id) {
        return directory.resolve(id.toString() + ".ser");
    }

    public <T> void save(Path path, T obj) {
        try (
                FileOutputStream fos = new FileOutputStream(path.toFile());
                ObjectOutputStream oos = new ObjectOutputStream(fos);
        ) {
            oos.writeObject(obj);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save: " + path, e);
        }
    }

    public <T> T load(Path path, Class<T> type) {
        try (
                FileInputStream fis = new FileInputStream(path.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis);
        ) {
            Object obj = ois.readObject();
            if(!type.isInstance(obj)) throw new IllegalStateException("Wrong type in file. path: " + path);
            return type.cast(obj);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load: " + path, e);
        }
    }

    public <T> List<T> loadAll(Path path, Class<T> type) {
        if(Files.exists(path)) {
            try (
                    Stream<Path> paths = Files.list(path);
            ){
                return paths
                        .map(p -> load(p, type))
                        .toList();
            } catch (IOException e) {
                throw new RuntimeException("Failed to load: " + path, e);
            }
        } else {
            return new ArrayList<>();
        }
    }

    public void delete(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete: " + path, e);
        }
    }
}
