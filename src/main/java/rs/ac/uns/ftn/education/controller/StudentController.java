package rs.ac.uns.ftn.education.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import rs.ac.uns.ftn.education.model.Document;
import rs.ac.uns.ftn.education.model.Student;
import rs.ac.uns.ftn.education.payload.StudentRequest;
import rs.ac.uns.ftn.education.security.CurrentUser;
import rs.ac.uns.ftn.education.security.UserPrincipal;
import rs.ac.uns.ftn.education.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class StudentController {

  @Autowired
  private StudentService studentService;

  @GetMapping("/students")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<Student> getAll(@PageableDefault(size = 10) Pageable pageable) {
    return studentService.getAll(pageable);
  }

  @GetMapping("/students/{studentId}")
  @PreAuthorize("hasRole('ADMIN')"
      + " || @securityService.isRoleAccessingSelf('ROLE_STUDENT', #studentId, #currentUser)")
  public Student getOne(@PathVariable("studentId") Long studentId, @CurrentUser UserPrincipal currentUser) {
    return studentService.getOne(studentId);
  }

  @PostMapping("/students")
  @PreAuthorize("hasRole('ADMIN')"
      + " || @securityService.isRoleSavingSelf('ROLE_STUDENT', #studentRequest, #currentUser)")
  public Student save(@Valid @RequestBody StudentRequest studentRequest, @CurrentUser UserPrincipal currentUser)
      throws MessagingException {
    return studentService.save(studentRequest);
  }

  @PutMapping("/students/{studentId}/enroll/{studyProgramId}")
  @PreAuthorize("hasRole('ADMIN')")
  public Student enroll(@PathVariable("studentId") Long studentId, @PathVariable("studyProgramId") Long studyProgramId) {
    return studentService.enroll(studentId, studyProgramId);
  }

  @GetMapping("/students/{studentId}/documents")
  @PreAuthorize("hasRole('ADMIN')"
      + " || @securityService.isRoleAccessingSelf('ROLE_STUDENT', #studentId, #currentUser)")
  public List<Document> getDocuments(@PathVariable("studentId") Long studentId, @CurrentUser UserPrincipal currentUser) {
    return studentService.getDocuments(studentId);
  }

  @PostMapping("/students/{studentId}/upload-document")
  @PreAuthorize("hasRole('ADMIN')"
      + " || @securityService.isRoleSavingSelf('ROLE_STUDENT', #studentId, #currentUser)")
  public Document uploadDocument(@PathVariable("studentId") Long studentId, @RequestParam("document") MultipartFile document) throws Exception {
    return studentService.uploadDocument(studentId, document);
  }

  @GetMapping("/students/{studentId}/documents/{documentId}/download")
  @PreAuthorize("hasRole('ADMIN')"
      + " || @securityService.isRoleAccessingSelf('ROLE_STUDENT', #studentId, #currentUser)")
  public ResponseEntity<byte[]> downloadDocument(@PathVariable("studentId") Long studentId, @PathVariable("documentId") Long documentId) throws Exception {
    return studentService.downloadDocument(documentId);
  }

  @DeleteMapping("/students/{studentId}/delete-document/{documentName}")
  @PreAuthorize("hasRole('ADMIN')"
      + " || @securityService.isRoleSavingSelf('ROLE_STUDENT', #studentId, #currentUser)")
  public void deleteDocument(@PathVariable("studentId") Long studentId, @PathVariable("documentName") String documentName) throws Exception {
    studentService.deleteDocument(studentId, documentName);
  }

  @DeleteMapping("/students/{studentId}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable("studentId") Long studentId) {
    studentService.delete(studentId);
  }
}
