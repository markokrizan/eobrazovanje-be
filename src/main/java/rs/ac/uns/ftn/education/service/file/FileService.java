package rs.ac.uns.ftn.education.service.file;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String uploadFile(MultipartFile file, String path) throws Exception;
    public abstract Boolean removeFile(String filePath) throws Exception;
    public List<String> listFiles(String path) throws Exception;
    public abstract byte[] getFile(String path) throws Exception;
}
