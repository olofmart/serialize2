package ru.olofmart;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {
    private static final String FILE_PATH = "/Users/olmart/Desktop/ProjectsJava/Netology/Games/savegames/";
    private static final String ZIP_PATH = "/Users/olmart/Desktop/ProjectsJava/Netology/Games/savegames/saves.zip";

    public static void main(String[] args) {

        List<Path> fileNames = new ArrayList<>();

        unZipFile(ZIP_PATH);

        try {
            fileNames = Files.walk(Paths.get(FILE_PATH))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(openProgress(fileNames.get(0).toString()));


    }

    public static void unZipFile (String zipPath) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            String fileName;
            while ((entry = zin.getNextEntry()) != null) {
                fileName = entry.getName();
                FileOutputStream fout = new FileOutputStream(fileName);
                for (int i = zin.read(); i != -1; i = zin.read()) {
                    fout.write(i);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static GameProgress openProgress (String filePath) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            gameProgress = (GameProgress) ois.readObject();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return gameProgress;
    }
}
