package rs.ac.uns.ftn.education.controller;

import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.education.model.ExamRegistration;
import rs.ac.uns.ftn.education.payload.ExamRegistrationRequest;
import rs.ac.uns.ftn.education.security.CurrentUser;
import rs.ac.uns.ftn.education.security.UserPrincipal;
import rs.ac.uns.ftn.education.service.ExamRegistrationService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ExamRegistrationController {

  @Autowired
  private ExamRegistrationService examRegistrationService;

  @Autowired
  private ModelMapper modelMapper;
  
  @GetMapping("/exam-registrations")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<ExamRegistration> getAll(@PageableDefault(size = 10) Pageable pageable) {
    return examRegistrationService.getAll(pageable);
  }

  @GetMapping("/students/{studentId}/exam-registrations")
  @PreAuthorize("hasRole('ADMIN')" + " || @securityService.isRoleAccessingSelf('ROLE_STUDENT', #studentId, #currentUser)")
  public Page<ExamRegistration> getStudentExamRegistrations(
    @PageableDefault(size = 10) Pageable pageable, 
    @PathVariable("studentId") Long studentId, 
    @CurrentUser UserPrincipal currentUser) 
  {
    return examRegistrationService.getStudentExamRegistrations(studentId, pageable);
  }

  @GetMapping("/teachers/{teacherId}/exam-registrations")
  @PreAuthorize("hasRole('ADMIN')" + " || @securityService.isRoleAccessingSelf('ROLE_TEACHER', #teacherId, #currentUser)")
  public Page<ExamRegistration> getTeacherExamRegistrations(
    @PageableDefault(size = 10) Pageable pageable, 
    @PathVariable("teacherId") Long teacherId, 
    @CurrentUser UserPrincipal currentUser) 
  {
    return examRegistrationService.getTeacherExamRegistrations(teacherId, pageable);
  }

  @GetMapping("/exam-registrations/{examRegistrationId}")
  @PreAuthorize("hasRole('ADMIN')")
  public ExamRegistration getOne(@PathVariable("examRegistrationId") Long examRegistrationId) {
    return examRegistrationService.getOne(examRegistrationId);
  }

  @PostMapping("/exam-registrations")
  @PreAuthorize("hasRole('ADMIN')" + " || @securityService.canUpdateExamRegistration(#examRegistrationRequest, #currentUser)")
  public ExamRegistration save(@Valid @RequestBody ExamRegistrationRequest examRegistrationRequest, @CurrentUser UserPrincipal currentUser) {
    ExamRegistration examRegistration = modelMapper.map(examRegistrationRequest, ExamRegistration.class);

    return examRegistrationService.save(examRegistration);
  }

  @DeleteMapping("/exam-registrations/{examRegistrationId}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable("examRegistrationId") Long examRegistrationId) {
    examRegistrationService.delete(examRegistrationId);
  }
}
