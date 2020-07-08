package rs.ac.uns.ftn.education.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class FileUploadService {

    public enum UploadType { IMAGE, DOCUMENT }

    private final String STORAGE_LOCATION_IMAGE = "images/";
    private final String STORAGE_LOCATION_DOCUMENT = "documents/";
    private final String FILE_TYPE_JPG = ".jpg";
    private final String FILE_TYPE_PDF = ".pdf";

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
}