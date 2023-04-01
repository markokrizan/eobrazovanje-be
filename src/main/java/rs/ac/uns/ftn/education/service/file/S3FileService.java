package rs.ac.uns.ftn.education.service.file;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class S3FileService implements FileService {

    @Autowired
    private AmazonS3 S3Client;
    
    public String uploadFile(MultipartFile fileToUpload, String path) throws Exception {
        String bucketName = getBucketNameFromPath(path);
        String bucketKey = getBucketKeyFromPath(path);

        File tempFile = File.createTempFile("temp", null);
        FileOutputStream out = new FileOutputStream(tempFile);
        out.write(fileToUpload.getBytes());
        out.close();
    
        S3Client.putObject(bucketName, bucketKey, tempFile);

        tempFile.delete();

        return path;
    }

    public Boolean removeFile(String filePath) {
        String bucketName = getBucketNameFromPath(filePath);
        String bucketKey = getBucketKeyFromPath(filePath);

        S3Client.deleteObject(bucketName, bucketKey);

        return true;
    }

    public List<String> listFiles(String path) throws Exception {
        List<String> files = new ArrayList<String>();

        for (S3ObjectSummary object : S3Client.listObjects(path).getObjectSummaries()) {
            files.add(object.getKey());
        }

        return files;        
    }

    public byte[] getFile(String path) throws Exception {
        String bucketName = getBucketNameFromPath(path);
        String bucketKey = getBucketKeyFromPath(path);
        S3Object object = S3Client.getObject(bucketName, bucketKey);
        S3ObjectInputStream objectContent = object.getObjectContent();
        return IOUtils.toByteArray(objectContent);
    }

    private String getBucketNameFromPath(String path) {
        String[] segments = path.split("/");

        if (segments[0].equals("")) {
            return segments[1];
        }

        return segments[0];
    }

    private static String getBucketKeyFromPath(String path) {
        String[] segments = path.split("/");

        String[] keySegments = {};

        if (segments[0].equals("")) {
            keySegments = ArrayUtils.remove(segments, 1);
        } else {
            keySegments = ArrayUtils.remove(segments, 0);
        }

        return String.join("/", keySegments);
    }
}
