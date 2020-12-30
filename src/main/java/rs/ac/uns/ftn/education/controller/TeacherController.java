package rs.ac.uns.ftn.education.controller;

import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.education.model.Teacher;
import rs.ac.uns.ftn.education.payload.TeacherRequest;
import rs.ac.uns.ftn.education.security.CurrentUser;
import rs.ac.uns.ftn.education.security.UserPrincipal;
import rs.ac.uns.ftn.education.service.TeacherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class TeacherController {

  @Autowired
  private TeacherService teacherService;

  @GetMapping("/teachers")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<Teacher> getAll(@PageableDefault(size = 10) Pageable pageable) {
      return teacherService.getAll(pageable);
  }

  @GetMapping("/teachers/{teacherId}")
  @PreAuthorize("hasRole('ADMIN')" + " || @securityService.isRoleAccessingSelf('ROLE_TEACHER', #teacherId, #currentUser)")
  public Teacher getOne(@PathVariable("teacherId") Long teacherId, @CurrentUser UserPrincipal currentUser) {    
      return teacherService.getOne(teacherId);
  }

  @PostMapping("/teachers")
  @PreAuthorize("hasRole('ADMIN')" + " || @securityService.isRoleSavingSelf('ROLE_TEACHER', #teacherRequest, #currentUser)")
  public Teacher save(@Valid @RequestBody TeacherRequest teacherRequest, @CurrentUser UserPrincipal currentUser) {
      return teacherService.save(teacherRequest);
  }

  @DeleteMapping("/teachers/{teacherId}")
  @PreAuthorize("hasRole('ADMIN')")
  public void delete(@PathVariable("teacherId") Long teacherId) {
    teacherService.delete(teacherId);
  }
}
