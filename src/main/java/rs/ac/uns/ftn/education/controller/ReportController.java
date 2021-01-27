package rs.ac.uns.ftn.education.controller;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import com.itextpdf.text.DocumentException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rs.ac.uns.ftn.education.security.CurrentUser;
import rs.ac.uns.ftn.education.security.UserPrincipal;
import rs.ac.uns.ftn.education.service.report.DocumentCreatorFactory;
import rs.ac.uns.ftn.education.service.report.DocumentCreatorType;
import rs.ac.uns.ftn.education.service.report.ReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/api")
public class ReportController {

  @Autowired
  ReportService reportService;

  @RequestMapping(value = "/students/{studentId}/report", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
  @PreAuthorize("hasRole('ADMIN')" + " || @securityService.isRoleAccessingSelf('ROLE_STUDENT', #studentId, #currentUser)")
  public ResponseEntity<InputStreamResource> studentReport(@PathVariable("studentId") Long studentId, @CurrentUser UserPrincipal currentUser) throws FileNotFoundException, DocumentException {
    ByteArrayInputStream report = reportService.generateReport(DocumentCreatorFactory.getCreator(DocumentCreatorType.STUDENT), studentId);

    return preparePdfResponse(report, "student-report.pdf");
  }

  private ResponseEntity<InputStreamResource> preparePdfResponse(ByteArrayInputStream report, String fileName) {
    var headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=" + fileName);

    return ResponseEntity
      .ok()
      .headers(headers)
      .contentType(MediaType.APPLICATION_PDF)
      .body(new InputStreamResource(report));
  }
}
