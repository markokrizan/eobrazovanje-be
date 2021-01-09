package rs.ac.uns.ftn.education.controller;

import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.education.model.Grade;
import rs.ac.uns.ftn.education.payload.GradeRequest;
import rs.ac.uns.ftn.education.security.CurrentUser;
import rs.ac.uns.ftn.education.security.UserPrincipal;
import rs.ac.uns.ftn.education.service.GradeService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class GradeController {

  @Autowired
  private GradeService gradeService;

  @Autowired
  private ModelMapper modelMapper;
  
  @GetMapping("/grades")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<Grade> getAll(@PageableDefault(size = 10) Pageable pageable) {
    return gradeService.getAll(pageable);
  }

  @GetMapping("/students/{studentId}/grades")
  @PreAuthorize("hasRole('ADMIN')" + " || @securityService.isRoleAccessingSelf('ROLE_STUDENT', #studentId, #currentUser)")
  public Page<Grade> getStudentGrades(
    @PageableDefault(size = 10) Pageable pageable, 
    @PathVariable("studentId") Long studentId, 
    @CurrentUser UserPrincipal currentUser) 
  {
    return gradeService.getStudentGrades(studentId, pageable);
  }

  @GetMapping("/grades/{gradeId}")
  // TODO: Admin, student whose grade this is and teacher if the grade is for a course he has an enrollment on
  public Grade getOne(@PathVariable("examRegistrationId") Long gradeId) {
    return gradeService.getOne(gradeId);
  }

  @PostMapping("/grades")
  @PreAuthorize("hasRole('ADMIN')" + " || @securityService.canUpdateGrade(#gradeRequest, #currentUser)")
  public Grade save(@Valid @RequestBody GradeRequest gradeRequest, @CurrentUser UserPrincipal currentUser) {
    Grade grade = modelMapper.map(gradeRequest, Grade.class);

    return gradeService.save(grade);
  }

  @DeleteMapping("/grades/{examRegistrationId}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable("examRegistrationId") Long examRegistrationId) {
    gradeService.delete(examRegistrationId);
  }
}
