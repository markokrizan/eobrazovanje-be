package rs.ac.uns.ftn.education.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class FileService {

    public enum UploadType { IMAGE, DOCUMENT }

    public static final String STORAGE_LOCATION_IMAGE = "images/";
    public static final String STORAGE_LOCATION_DOCUMENT = "documents/";
    public static final String FILE_TYPE_JPG = ".jpg";
    public static final String FILE_TYPE_PDF = ".pdf";

    public static final List<String> HIDDEN_FILES = Arrays.asList(".gitkeep");

    public String uploadFile(byte[] fileToUpload, UploadType fileType) throws IOException {
        String path = generateFilePath(fileType);
    
        File file = new File(path);
        file.createNewFile();
        
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(fileToUpload);
        fout.close();

        return path;
    }

    private String generateFilePath(UploadType fileType) {
        String randomName = UUID.randomUUID().toString();
        String path = null;

        switch(fileType) {
            case IMAGE:
                path = STORAGE_LOCATION_IMAGE + randomName + FILE_TYPE_JPG;
                break;
            case DOCUMENT:
                path = STORAGE_LOCATION_DOCUMENT + randomName + FILE_TYPE_PDF;
                break;
          }

        return path;
    }

    public Boolean removeFile(String filePath) {
        File image = new File(filePath);

        return image.delete();
    }

    public List<String> listFiles(String basePath) throws IOException {
        return Files.list(Paths.get(basePath))
            .map(path -> path.getFileName().toString())
            .filter(fileName -> !HIDDEN_FILES.contains(fileName))
            .collect(Collectors.toList());
    }

    public byte[] getFile(String path) throws IOException {
        return Files.readAllBytes(Paths.get(path));
    }
}