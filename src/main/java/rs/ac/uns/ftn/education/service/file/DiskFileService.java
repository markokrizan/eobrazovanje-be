package rs.ac.uns.ftn.education.service.file;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DiskFileService implements FileService {
    public String uploadFile(MultipartFile fileToUpload, String path) throws Exception {
        throw new UnsupportedOperationException("Upload file method not implemented for DiskFileService");
    }

    public Boolean removeFile(String filePath) {
        File image = new File(filePath);

        return image.delete();
    }

    public List<String> listFiles(String path) throws Exception {
        return Files.list(Paths.get(path))
            .map(p -> p.getFileName().toString())
            .collect(Collectors.toList());
    }

    public byte[] getFile(String path) throws Exception {
        return Files.readAllBytes(Paths.get(path));
    }
}
