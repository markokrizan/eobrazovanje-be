package rs.ac.uns.ftn.education.controller;

import org.springframework.web.bind.annotation.*;

import rs.ac.uns.ftn.education.exception.ResourceNotFoundException;
import rs.ac.uns.ftn.education.model.Role;
import rs.ac.uns.ftn.education.model.Teacher;
import rs.ac.uns.ftn.education.payload.TeacherRequest;
import rs.ac.uns.ftn.education.repository.RoleRepository;
import rs.ac.uns.ftn.education.repository.TeacherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;
import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class TeacherController {

  @Autowired
  private TeacherRepository teacherRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  RoleRepository roleRepository;
  
  @GetMapping("/teachers")
  @PreAuthorize("hasRole('ADMIN')")
  public Page<Teacher> getAll(@PageableDefault(size = 10) Pageable pageable) {
      return teacherRepository.findAll(pageable);
  }

  @GetMapping("/teachers/{teacherId}")
  @PreAuthorize("hasRole('ADMIN')")
  public Teacher getOne(@PathVariable("teacherId") Long studentId) {    
      return teacherRepository.findById(studentId)
        .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));
  }

  @PostMapping("/teachers")
  @PreAuthorize("hasRole('ADMIN')")
  public Teacher save(@Valid @RequestBody TeacherRequest teacherRequest) {
      Teacher teacher = modelMapper.map(teacherRequest, Teacher.class);
      teacher.setPassword(passwordEncoder.encode(teacherRequest.getPersonalIdNumber()));

      Role teacherRole = roleRepository.findByName(Role.ROLE_TEACHER)
        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", Role.ROLE_TEACHER));
        teacher.setRoles(Collections.singleton(teacherRole));

      return teacherRepository.save(teacher);
  }

  @DeleteMapping("/teachers/{teacherId}")
  @PreAuthorize("hasRole('USER')")
  public void delete(@PathVariable("teacherId") Long teacherId) {
    teacherRepository.deleteById(teacherId);
  }
}
