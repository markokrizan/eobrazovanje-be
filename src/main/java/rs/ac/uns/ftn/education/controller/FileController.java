package rs.ac.uns.ftn.education.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.education.service.FileService;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api")
public class FileController {

  @Autowired
  FileService fileService;

  @GetMapping("/documents")
  public List<String> documents() throws IOException {
    return fileService.listFiles(FileService.STORAGE_LOCATION_DOCUMENT);
  }

  @GetMapping("/documents/{documentName}")
  @ResponseBody
  public ResponseEntity<byte[]> getDocument(@PathVariable String documentName)
      throws IOException {
      byte[] file = fileService.getFile(FileService.STORAGE_LOCATION_DOCUMENT + documentName);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentName + "\"")
          .body(file);
  }
}
